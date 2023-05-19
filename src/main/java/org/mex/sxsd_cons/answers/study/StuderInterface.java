package org.mex.sxsd_cons.answers.study;

/**
 * 书香山东学习总流程的所有接口
 */
public interface StuderInterface {

    /**
     * GET_STUDY_MAIN_LIST 获取主页书目列表
     * GET_STUDY_ACTIVITY_LIST 获取书目闯关列表
     * GET_STUDY_ACTIVITY_ANSWER 获取闯关题目(及答案)
     * POST_STUDY_ACTIVITY_FINISH 完成闯关
     * GET_KONWLEDGE_SCORE 获取知识关卡成绩
     * GET_KONWLEDGE_ANSWER 获取知识关卡题目(及答案)
     * POST_KONWLEDGE_FINISH 完成知识关卡
     */
    //传入年级和Cookie
    String GET_STUDY_MAIN_LIST(int gradeId, String cookie);

    //originId即为pretestAppId, originType可以任填,默认为11, accountId为用户id, bookId为书目id, cookie为Cookie
    String GET_STUDY_ACTIVITY_LIST(int originId,int originType,int accountId,int bookId, String cookie);

    //originId任填,推荐35 originType任填,推荐11 accountId bookIdcookie
    String GET_STUDY_ACTIVITY_ANSWER(int originId, int originType, int accountId, int bookIdcookie, String cookie);

    //cookie body
    String POST_STUDY_ACTIVITY_FINISH(String cookie, String Body);

    //originIds任填,35 gradeId=14年级 accountId帐号 ver任填,1 cookie
    String GET_KONWLEDGE_SCORE(int originIds, int gradeId, int accountId, int ver, String cookie);

    //accountId学号 gradeId年级 originIds任填,35 ver任填,1 logicPaperId第几套试题 reTry任填,1 cookie
    String GET_KONWLEDGE_ANSWER(int accountId, int gradeId, int originIds, int ver, int logicPaperId, int reTry, String cookie);

    //cookie body
    String POST_KONWLEDGE_FINISH(String cookie, String Body);

}
