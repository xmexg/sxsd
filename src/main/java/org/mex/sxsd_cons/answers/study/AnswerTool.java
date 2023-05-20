package org.mex.sxsd_cons.answers.study;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.mex.sxsd_cons.Init;
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
     * 自动完成所有活动
     * @param authUser 已登录的用户
     * @param costTime 答题总耗时
     * @return 是否成功
     */
    public static boolean Auto_Activity_Finish(AuthUser authUser, int costTime) {
        int accountId = authUser.INFO.get("accountId").getAsInt();
        String cookie = authUser.COOKIE;
        JsonArray res = WebResponseData.GetDataAsJsonArray(new Studyer().GET_STUDY_MAIN_LIST(authUser.INFO.get("gradeId").getAsInt(), Init.AUTHUSER.COOKIE));
        if (res == null) {
            PrintFormat.println("获取主页书目失败", PrintFormat.ERROR);
            return false;
        }
        try {
            res.forEach((v) -> {
                JsonObject obj = v.getAsJsonObject();
                int originId = obj.get("pretestAppId").getAsInt();
                int bookId = obj.get("bookId").getAsInt();
                JsonArray res2 = WebResponseData.GetDataAsJsonArray(new Studyer().GET_STUDY_ACTIVITY_LIST(originId, 0, authUser.INFO.get("accountId").getAsInt(), bookId, Init.AUTHUSER.COOKIE));

                PrintFormat.println(" \n- " + obj.get("bookName").toString(), PrintFormat.LIGHT_OUT);
                res2.forEach((v2) -> {
                    JsonObject obj2 = v2.getAsJsonObject();
                    PrintFormat.print(" | - " + obj2.get("paperName").getAsString(), PrintFormat.OUT);
                    if (obj2.get("isDone").getAsInt() == 1) {
                        PrintFormat.println(" - 已完成", PrintFormat.OUT);
                        return;
                    }
                    JsonObject obj3 = WebResponseData.GetDataAsJsonObject(new Studyer().GET_STUDY_ACTIVITY_ANSWER(obj2.get("paperId").getAsInt(), 0, accountId, bookId, cookie));
                    if (obj3 == null) {
                        PrintFormat.println(" - 获取闯关题目及答案失败,可能是达到了每日做题上限", PrintFormat.ERROR);
                        return;
                    }
                    String answerBody = Activity_answerBuilder(obj3, costTime, authUser.INFO.get("accountId").getAsInt(), bookId);
                    if (answerBody == null) {
                        PrintFormat.println(" - 生成答案失败", PrintFormat.ERROR);
                        return;
                    }
                    JsonObject answer_res = WebResponseData.GetDataAsJsonObject(new Studyer().POST_STUDY_ACTIVITY_FINISH(cookie, answerBody));
//                    JsonObject answer_res = null;
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
     *                 <= 0 时随机生成
     * @param accountId 用户id, 使用AuthUser.INFO.get("accountId").getAsInt()获取
     * @return 答案的请求体
     */
    public static String Activity_answerBuilder(JsonObject answer, int costTime, int accountId, int bookId) {
        costTime = costTime <= 0 ? (int) (Math.random() * 17000) + 3000 : costTime;
        HashMap<String, Object> item = new HashMap<>();// 单个选项
        ArrayList<HashMap<String, ?>> questions = new ArrayList<>();// 选项集合
        HashMap<String, Object> topMap = new HashMap<>();// 完整请求体
        JsonArray res_questions = answer.getAsJsonArray("questions");
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
                    questions.add(item);
                }
            });
        });
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

}
