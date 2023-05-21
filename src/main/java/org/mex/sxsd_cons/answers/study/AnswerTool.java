package org.mex.sxsd_cons.answers.study;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.mex.sxsd_cons.PrintFormat;
import org.mex.sxsd_cons.answers.net.WebResponseData;
import org.mex.sxsd_cons.answers.user.AuthUser;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * 提交答案
 */
public class AnswerTool {

    /**
     * 自动完成书目关卡
     * @param authUser 已登录的用户
     * @param costTime 答题总耗时
     * @param redo 是否重做
     * @return 是否成功
     */
    public static boolean Auto_Activity_Finish(AuthUser authUser, int costTime, boolean redo) {
        int accountId = authUser.INFO.get("accountId").getAsInt();
        int gradeId = authUser.INFO.get("gradeId").getAsInt();
        String cookie = authUser.COOKIE;
        JsonArray res = WebResponseData.GetDataAsJsonArray(new Studyer().GET_STUDY_MAIN_LIST(gradeId, cookie));
        if (res == null) {
            PrintFormat.println("获取主页书目失败", PrintFormat.ERROR);
            return false;
        }
        try {
            res.forEach((v) -> {
                JsonObject obj = v.getAsJsonObject();
                int originId = obj.get("pretestAppId").getAsInt();
                int bookId = obj.get("bookId").getAsInt();
                JsonArray res2 = WebResponseData.GetDataAsJsonArray(new Studyer().GET_STUDY_ACTIVITY_LIST(originId, 0, accountId, bookId, cookie));

                PrintFormat.println(" \n- " + obj.get("bookName").toString(), PrintFormat.LIGHT_OUT);
                res2.forEach((v2) -> {
                    JsonObject obj2 = v2.getAsJsonObject();
                    PrintFormat.print(" | - " + obj2.get("paperName").getAsString(), PrintFormat.OUT);
                    if (obj2.get("isDone").getAsInt() == 1) {
                        PrintFormat.print(" - 已完成", PrintFormat.OUT);
                        if (redo)
                            PrintFormat.print(" - 正在重做", PrintFormat.OUT);
                        else {
                            PrintFormat.println("", PrintFormat.OUT);
                            return;
                        }
                    }
                    JsonObject obj3 = WebResponseData.GetDataAsJsonObject(new Studyer().GET_STUDY_ACTIVITY_ANSWER(obj2.get("paperId").getAsInt(), 0, accountId, bookId, cookie));
                    if (obj3 == null) {
                        PrintFormat.println(" - 获取闯关题目及答案失败,可能是达到了每日做题上限", PrintFormat.ERROR);
                        return;
                    }
                    int upCostTime = costTime <= 0 ? (int) (Math.random() * 17000) + 3000 : costTime;
                    String answerBody = Activity_answerBuilder(obj3, upCostTime, accountId, bookId);
                    if (answerBody == null) {
                        PrintFormat.println(" - 生成答案失败", PrintFormat.ERROR);
                        return;
                    }
                    JsonObject answer_res = WebResponseData.GetDataAsJsonObject(new Studyer().POST_STUDY_ACTIVITY_FINISH(cookie, answerBody));
                    if (answer_res == null) {
                        PrintFormat.println(" - 提交答案失败", PrintFormat.ERROR);
                        return;
                    }
                    PrintFormat.println(" - 提交成功 得分:"+answer_res.get("points").getAsString(), PrintFormat.OUT);
                });
            });
        } catch (Exception e) {
            e.printStackTrace();
            PrintFormat.println(" - 迭代列表失败", PrintFormat.ERROR);
            return false;
        }
        return true;
    }

    /**
     * 生成答案
     * @param answer 题目的data部分
     * @param costTime 答题总耗时
     * @param accountId 用户id, 使用AuthUser.INFO.get("accountId").getAsInt()获取
     * @param bookId bookId
     * @return 答案的请求体
     */
    public static String Activity_answerBuilder(JsonObject answer, int costTime, int accountId, int bookId) {
        ArrayList<HashMap<String, ?>> questions;// 选项集合
        HashMap<String, Object> topMap = new HashMap<>();// 完整请求体
        JsonArray res_questions = answer.getAsJsonArray("questions");
        questions = Question_Builder(res_questions, costTime, false);// 书目闯关不需要在选项中添加耗时
        topMap.put("bookId", ""+bookId);//这是String类型
        topMap.put("costTime", costTime);
        topMap.put("questions", questions);
        topMap.put("paperId", answer.get("paperId").getAsInt());
        topMap.put("batchId", answer.get("batchId").getAsString());
        topMap.put("accountId", accountId);
        topMap.put("okCount", questions.size());
        topMap.put("okRate", (int)((questions.size()/res_questions.size()) * 100));
        return new Gson().toJson(topMap);
    }

    /**
     * 生成选项列表答案
     * @param res_questions 题目的questions部分
     * @param costTime 答题总耗时
     *                 书目闯关不需要在选项中添加耗时
     *                 关卡闯关需要在选项中添加耗时
     * @param addCostTime 是否在每个选项中添加耗时
     * @return 选项集合
     */
    private static ArrayList<HashMap<String,?>> Question_Builder(JsonArray res_questions, int costTime, boolean addCostTime) {
        HashMap<String, Object> item = new HashMap<>();// 单个选项
        ArrayList<HashMap<String, ?>> questions = new ArrayList<>();// 选项集合
        res_questions.forEach((items) -> {
            JsonObject res_questions_obj = items.getAsJsonObject();
            item.put("questionId", res_questions_obj.get("questionId").getAsInt());
            JsonArray res_questions_obj_items = res_questions_obj.getAsJsonArray("items");
            res_questions_obj_items.forEach((itemObj) -> {
                JsonObject itemObj_obj = itemObj.getAsJsonObject();
                if (itemObj_obj.get("isRight").getAsBoolean()) {
                    int rightItemId = itemObj_obj.get("id").getAsInt();
                    item.put("userItemIds", rightItemId);
                    item.put("okitemIds", rightItemId);
                    if (addCostTime) item.put("costTime", costTime);
                    questions.add(item);
                }
            });
        });
        return questions;
    }

    /**
     * 自动完成知识关卡
     * @param authuser
     * @param costTime
     * @param retry
     * @param redo
     * @return
     */
    public static boolean Auto_KNOWLEDGE_Finish(AuthUser authuser, int costTime, String originIds, int retry, boolean redo) {
        int gradeId = authuser.INFO.get("gradeId").getAsInt();
        int accountId = authuser.INFO.get("accountId").getAsInt();
        String cookie = authuser.COOKIE;
        int finalRetry = retry == 0 ? 1 : retry;
        String up_originIds = null;
        if (originIds == null || originIds.equals("")) {
            JsonArray originIdsList = WebResponseData.GetDataAsJsonArray(new Studyer().GET_STUDY_MAIN_LIST(gradeId, cookie));
            if (originIdsList != null) {
                for(int i=0; i < originIdsList.size(); i++){
                    JsonObject obj = originIdsList.get(i).getAsJsonObject();
                    up_originIds += obj.get("pretestAppId").getAsString();
                    if (i != originIdsList.size() - 1)
                        up_originIds += "-";
                }
            } else {
                up_originIds = originIds;
            }
        } else {
            up_originIds = originIds;
        }
        JsonArray res = WebResponseData.GetDataAsJsonArray(new Studyer().GET_KNOWLEDGE_SCORE(up_originIds, gradeId,  accountId, 0, cookie));
        if (res == null) {
            PrintFormat.println("获取知识关卡列表失败", PrintFormat.ERROR);
            return false;
        }
        res.forEach((v) -> {
            JsonObject obj1 = v.getAsJsonObject();
            int logicPaperId = obj1.get("logicPaperId").getAsInt();
            PrintFormat.print(" - 当前关卡: " + logicPaperId, PrintFormat.LIGHT_OUT);
            if(obj1.get("isDone").getAsInt() == 1) {
                PrintFormat.print(" - 已完成", PrintFormat.OUT);
                if (redo)
                    PrintFormat.print(" - 正在重做", PrintFormat.OUT);
                else {
                    PrintFormat.println("", PrintFormat.OUT);
                    return;
                }
            }
            JsonObject res2 = WebResponseData.GetDataAsJsonObject(new Studyer().GET_KNOWLEDGE_ANSWER(accountId, gradeId, null, 0, logicPaperId, finalRetry, cookie));
            if (res2 == null) {
                PrintFormat.println("获取知识关卡试题及答案失败,可能达到了做题上限", PrintFormat.ERROR);
                return;
            }
            String answerBody = Knowledge_answerBuilder(res2, costTime, accountId, gradeId, finalRetry);
            if (answerBody == null) {
                PrintFormat.println(" - 生成答案失败", PrintFormat.ERROR);
                return;
            }
            JsonObject answer_res = WebResponseData.GetDataAsJsonObject(new Studyer().POST_KNOWLEDGE_FINISH(cookie, answerBody));
            if (answer_res == null) {
                PrintFormat.println(" - 提交答案失败", PrintFormat.ERROR);
                return;
            }
            PrintFormat.println(" - 提交成功 得分:"+answer_res.get("points").getAsString(), PrintFormat.OUT);
        });
        return true;
    }

    /**
     * 生成知识关卡的答案
     * @param answer 题目的data部分
     * @param costTime 答题总耗时
     * @param accountId 用户id
     * @param gradeId 年级id
     * @param retry 之前获取答案时使用的retry的值
     * @return 答案的请求体
     */
    public static String Knowledge_answerBuilder(JsonObject answer, int costTime, int accountId, int gradeId, int retry) {
        int AllCostTime = 0;
        ArrayList<HashMap<String, ?>> questions;// 选项集合
        HashMap<String, Object> topMap = new HashMap<>();// 完整请求体
        JsonArray res_questions = answer.getAsJsonArray("questions");
        int upCostTime = costTime <= 0 ? (int) (Math.random() * 7000) + 3000 : costTime;// 随机耗时, 3~10秒
        AllCostTime += upCostTime;
        questions = Question_Builder(res_questions, upCostTime, true);
        topMap.put("reTry", ""+retry);//这是String类型
        topMap.put("costTime", AllCostTime);
        topMap.put("questions", questions);
        topMap.put("logicPaperId", answer.get("logicPaperId").getAsInt());
        topMap.put("batchId", answer.get("batchId").getAsString());
        topMap.put("accountId", accountId);
        topMap.put("gradeId", ""+gradeId);//这是String类型
        topMap.put("okCount", questions.size());
        topMap.put("okRate", (int)((questions.size()/res_questions.size()) * 100));
        return new Gson().toJson(topMap);
    }
}
