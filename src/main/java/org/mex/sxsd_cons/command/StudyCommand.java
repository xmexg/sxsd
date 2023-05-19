package org.mex.sxsd_cons.command;

public class StudyCommand {
    public StudyCommand() {

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
        return false;
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
        return false;
    }
}

class Study_ACTIVITY_START_CommandHandler implements CommandHandler {
    public String trigger() {
        return "study_activity_start";
    }

    public String description() {
        return "获取闯关题目及答案";
    }

    public boolean handleCommand(String[] command) {
        return false;
    }
}

class Study_ACTIVITY_ANSWER_CommandHandler implements CommandHandler {
    public String trigger() {
        return "study_activity_answer";
    }

    public String description() {
        return "提交闯关答案";
    }

    public boolean handleCommand(String[] command) {
        return false;
    }
}

class Study_KONWLWDGE_SCORE_CommandHandler implements CommandHandler {
    public String trigger() {
        return "study_kown_score";
    }

    public String description() {
        return "获取知识关卡得分";
    }

    public boolean handleCommand(String[] command) {
        return false;
    }
}

class Study_KONWLWDGE_ANSWER_CommandHandler implements CommandHandler {
    public String trigger() {
        return "study_kown_answer";
    }

    public String description() {
        return "获取知识关卡试题及答案";
    }

    public boolean handleCommand(String[] command) {
        return false;
    }
}

class Study_KONWLWDGE_FINISH_CommandHandler implements CommandHandler {
    public String trigger() {
        return "study_kown_finish";
    }

    public String description() {
        return "完成知识关卡";
    }

    public boolean handleCommand(String[] command) {
        return false;
    }
}