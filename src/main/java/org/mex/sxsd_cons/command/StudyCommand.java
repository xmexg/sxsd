package org.mex.sxsd_cons.command;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.mex.sxsd_cons.Console;
import org.mex.sxsd_cons.Init;
import org.mex.sxsd_cons.PrintFormat;
import org.mex.sxsd_cons.answers.net.WebResponseData;
import org.mex.sxsd_cons.answers.study.AnswerTool;
import org.mex.sxsd_cons.answers.study.Studyer;

public class StudyCommand {
    public StudyCommand() {
        Console.COMMAND.register(new Study_MAIN_LIST_CommandHandler());
        Console.COMMAND.register(new Study_ACTIVITY_LIST_CommandHandler());
        Console.COMMAND.register(new Study_ACTIVITY_ANSWER_CommandHandler());
        Console.COMMAND.register(new Study_ACTIVITY_FINISH_CommandHandler());
        Console.COMMAND.register(new Study_KNOWLWDGE_SCORE_CommandHandler());
        Console.COMMAND.register(new Study_KNOWLWDGE_ANSWER_CommandHandler());
        Console.COMMAND.register(new Study_KNOWLWDGE_FINISH_CommandHandler());

    }
}

class Study_MAIN_LIST_CommandHandler implements CommandHandler {
    public String trigger() {
        return "study_main_list";
    }

    public String description() {
        return "列出主页书目";
    }

    public boolean handleCommand(String[] command) {
        if (Init.AUTHUSER == null) {
            PrintFormat.println("请先切换用户", PrintFormat.ERROR);
            return false;
        }
        JsonArray res = WebResponseData.GetDataAsJsonArray(new Studyer().GET_STUDY_MAIN_LIST(Init.AUTHUSER.INFO.get("gradeId").getAsInt(), Init.AUTHUSER.COOKIE));

        if (res == null) {
            PrintFormat.println("获取主页书目失败", PrintFormat.ERROR);
            return false;
        }
        res.forEach((v) -> {
            JsonObject obj = v.getAsJsonObject();
            PrintFormat.println(" - "+obj.toString(), PrintFormat.OUT);
        });
        return true;
    }
}

class Study_ACTIVITY_LIST_CommandHandler implements CommandHandler {
    public String trigger() {
        return "study_activity_list";
    }

    public String description() {
        return "列出书目闯关列表";
    }

    public boolean handleCommand(String[] command) {
        if (Init.AUTHUSER == null) {
            PrintFormat.println("请先切换用户", PrintFormat.ERROR);
            return false;
        }
        JsonArray res = WebResponseData.GetDataAsJsonArray(new Studyer().GET_STUDY_MAIN_LIST(Init.AUTHUSER.INFO.get("gradeId").getAsInt(), Init.AUTHUSER.COOKIE));
        if (res == null) {
            PrintFormat.println("获取主页书目失败", PrintFormat.ERROR);
            return false;
        }
        try {
            res.forEach((v) -> {
                JsonObject obj = v.getAsJsonObject();
                JsonArray res2 = WebResponseData.GetDataAsJsonArray(new Studyer().GET_STUDY_ACTIVITY_LIST(obj.get("pretestAppId").getAsInt(), 0, Init.AUTHUSER.INFO.get("wechatUserId").getAsInt(), obj.get("bookId").getAsInt(), Init.AUTHUSER.COOKIE));
                if (res2 == null) {
                    PrintFormat.println("获取书目闯关列表失败", PrintFormat.ERROR);
                } else {
                    PrintFormat.println(" \n- " + obj.get("bookName").toString(), PrintFormat.LIGHT_OUT);
                    res2.forEach((v2) -> {
                        JsonObject obj2 = v2.getAsJsonObject();
                        PrintFormat.println(" | - " + obj2.toString(), PrintFormat.OUT);
                    });
                }
            });
        } catch (Exception e) {
            PrintFormat.println("迭代书目闯关列表失败", PrintFormat.ERROR);
            return false;
        }
        return true;
    }
}

class Study_ACTIVITY_ANSWER_CommandHandler implements CommandHandler {
    public String trigger() {
        return "study_activity_answer";
    }

    public String description() {
        return "获取闯关题目及答案";
    }

    public boolean handleCommand(String[] command) {
        if (Init.AUTHUSER == null) {
            PrintFormat.println("请先切换用户", PrintFormat.ERROR);
            return false;
        }
        int accountId = Init.AUTHUSER.INFO.get("accountId").getAsInt();
        String cookie = Init.AUTHUSER.COOKIE;
        JsonArray res = WebResponseData.GetDataAsJsonArray(new Studyer().GET_STUDY_MAIN_LIST(Init.AUTHUSER.INFO.get("gradeId").getAsInt(), Init.AUTHUSER.COOKIE));
        if (res == null) {
            PrintFormat.println("获取主页书目失败", PrintFormat.ERROR);
            return false;
        }
        try {
            res.forEach((v) -> {
                JsonObject obj = v.getAsJsonObject();
                int originId = obj.get("pretestAppId").getAsInt();
                int bookId = obj.get("bookId").getAsInt();
                JsonArray res2 = WebResponseData.GetDataAsJsonArray(new Studyer().GET_STUDY_ACTIVITY_LIST(originId, 0, Init.AUTHUSER.INFO.get("wechatUserId").getAsInt(), bookId, Init.AUTHUSER.COOKIE));

                    PrintFormat.println(" \n- " + obj.get("bookName").toString(), PrintFormat.LIGHT_OUT);
                    res2.forEach((v2) -> {
                        JsonObject obj2 = v2.getAsJsonObject();
                        PrintFormat.println(" | - " + obj2.get("paperName").getAsString(), PrintFormat.OUT);

                        JsonObject obj3 = WebResponseData.GetDataAsJsonObject(new Studyer().GET_STUDY_ACTIVITY_ANSWER(obj2.get("paperId").getAsInt(), 0, accountId, bookId, cookie));
                        if(obj3 == null){
                            PrintFormat.println(" | - 获取闯关题目及答案失败,可能是达到了每日做题上限", PrintFormat.ERROR);
                            return;
                        }
                        JsonArray res3_questions = obj3.getAsJsonArray("questions");
                        res3_questions.forEach((v3) -> {
                            JsonObject v3_obj = v3.getAsJsonObject();
                            JsonArray res3_item = v3_obj.getAsJsonArray("items");
                            PrintFormat.println(" | | - ? " + v3_obj.get("title").getAsString(), PrintFormat.OUT);
                            res3_item.forEach((v3_item) -> {
                                JsonObject v4_obj = v3_item.getAsJsonObject();
                                boolean isRight = v4_obj.get("isRight").getAsBoolean();
                                String out;
                                if (isRight) {
                                    out = "[ " + v4_obj.get("itemContent").getAsString() + " ]";
                                } else {
                                    out = v4_obj.get("itemContent").getAsString();
                                }
                                PrintFormat.println(" | | - : " + out, PrintFormat.OUT);
                            });
                        });
                    });
            });
        } catch (Exception e) {
            e.printStackTrace();
            PrintFormat.println("迭代列表失败", PrintFormat.ERROR);
            return false;
        }
        return true;
    }
}

class Study_ACTIVITY_FINISH_CommandHandler implements CommandHandler {
    public String trigger() {
        return "study_activity_finish";
    }

    public String description() {
        return "完成闯关";
    }

    public boolean handleCommand(String[] command) {
        if (Init.AUTHUSER == null) {
            PrintFormat.println("请先切换用户", PrintFormat.ERROR);
            return false;
        }
        PrintFormat.print(" ? 请输入做题用时(毫秒,过小时会答题失败): ", PrintFormat.LIGHT_OUT);
        int time = Integer.parseInt(Console.SCANNER.next());
        return AnswerTool.Auto_Activity_Finish(Init.AUTHUSER, time);
    }
}

class Study_KNOWLWDGE_SCORE_CommandHandler implements CommandHandler {
    public String trigger() {
        return "study_know_score";
    }

    public String description() {
        return "获取知识关卡得分";
    }

    public boolean handleCommand(String[] command) {
        return false;
    }
}

class Study_KNOWLWDGE_ANSWER_CommandHandler implements CommandHandler {
    public String trigger() {
        return "study_know_answer";
    }

    public String description() {
        return "获取知识关卡试题及答案";
    }

    public boolean handleCommand(String[] command) {
        return false;
    }
}

class Study_KNOWLWDGE_FINISH_CommandHandler implements CommandHandler {
    public String trigger() {
        return "study_know_finish";
    }

    public String description() {
        return "完成知识关卡";
    }

    public boolean handleCommand(String[] command) {
        return false;
    }
}