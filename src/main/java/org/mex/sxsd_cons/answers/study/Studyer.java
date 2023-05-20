package org.mex.sxsd_cons.answers.study;

import org.mex.sxsd_cons.answers.net.BaseNetLink;
import org.mex.sxsd_cons.answers.net.WebRequest;

public class Studyer implements StudyerInterface {

    /**
     * 获取主页书目列表
     * @param gradeId 年级ID
     * @param cookie Cookie
     * @return 返回书目列表的响应
     */
    @Override
    public String GET_STUDY_MAIN_LIST(int gradeId, String cookie) {
        return WebRequest.send(BaseNetLink.STUDY_MAIN_LIST + "?activityId=1&gradeId=" + gradeId, cookie, BaseNetLink.BaseHeaders_GET, null);
    }

    /**
     * 获取书目闯关列表
     * @param originId originId即为pretestAppId
     * @param originType 书目类型,默认为11,填0默认
     * @param accountId 账户ID,和微信id不是一回事
     * @param bookId 书目ID
     * @param cookie Cookie
     * @return 返回闯关列表的响应
     */
    @Override
    public String GET_STUDY_ACTIVITY_LIST(int originId, int originType, int accountId, int bookId, String cookie) {
        originType = originType <= 0 ? 11 : originType;
        return WebRequest.send(BaseNetLink.STUDY_ACTIVITY_LIST + "?originId=" + originId + "&originType=" + originType + "&accountId=" + accountId + "&bookId=" + bookId, cookie, BaseNetLink.BaseHeaders_GET, null);
    }

    /**
     * 获取闯关题目(及答案)
     * @param paperId 试题ID
     * @param originType 书目类型,默认为11,填0默认
     * @param accountId 账户ID
     * @param bookId 书目ID
     * @param cookie Cookie
     * @return 返回闯关题目(及答案)的响应
     */
    @Override
    public String GET_STUDY_ACTIVITY_ANSWER(int paperId, int originType, int accountId, int bookId, String cookie) {
        originType = originType <= 0 ? 11 : originType;
        return WebRequest.send(BaseNetLink.STUDY_ACTIVITY_ANSWER + "?paperId=" + paperId + "&originType=" + originType + "&accountId=" + accountId + "&bookId=" + bookId, cookie, BaseNetLink.BaseHeaders_GET, null);
    }

    /**
     * 完成闯关
     * @param cookie Cookie
     * @param Body 请求体
     * @return 返回完成闯关的响应
     */
    @Override
    public String POST_STUDY_ACTIVITY_FINISH(String cookie, String Body) {
        return WebRequest.send(BaseNetLink.STUDY_ACTIVITY_FINISH, cookie, BaseNetLink.BaseHeaders_POST, Body);
    }

    /**
     * 获取知识点列表
     * @param originIds
     * @param gradeId
     * @param accountId
     * @param ver
     * @param cookie
     * @return
     */
    @Override
    public String GET_KNOWLEDGE_SCORE(int originIds, int gradeId, int accountId, int ver, String cookie) {
        originIds = originIds == 0 ? 35 : originIds;
        return WebRequest.send(BaseNetLink.KONWLEDGE_SCORE + "?originIds=" + originIds + "&gradeId=" + gradeId + "&accountId=" + accountId + "&ver=" + ver, cookie, BaseNetLink.BaseHeaders_GET, null);
    }

    @Override
    public String GET_KNOWLEDGE_ANSWER(int accountId, int gradeId, int originIds, int ver, int logicPaperId, int reTry, String cookie) {
        originIds = originIds == 0 ? 35 : originIds;
        reTry = reTry == 0 ? 1 : reTry;
        return WebRequest.send(BaseNetLink.KONWLEDGE_ANSWER + "?accountId=" + accountId + "&gradeId=" + gradeId + "&originIds=" + originIds + "&ver=" + ver + "&logicPaperId=" + logicPaperId + "&reTry=" + reTry, cookie, BaseNetLink.BaseHeaders_GET, null);
    }

    @Override
    public String POST_KNOWLEDGE_FINISH(String cookie, String Body) {
        return WebRequest.send(BaseNetLink.KONWLEDGE_FINISH, cookie, BaseNetLink.BaseHeaders_POST, Body);
    }

    @Override
    public String GET_USER_SCORE(int accountId, String cookie) {
        return WebRequest.send(BaseNetLink.USER_SCORE + "?accountId=" + accountId, cookie, BaseNetLink.BaseHeaders_GET, null);
    }
}
