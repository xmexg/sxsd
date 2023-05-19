package org.mex.sxsd_cons.answers.study;

import org.mex.sxsd_cons.answers.net.BaseNetLink;
import org.mex.sxsd_cons.answers.net.WebRequest;

public class Studer implements StuderInterface{


    @Override
    public String GET_STUDY_MAIN_LIST(int gradeId, String cookie) {
        return WebRequest.send(BaseNetLink.STUDY_MAIN_LIST + "?gradeId=" + gradeId, cookie, BaseNetLink.BaseHeaders_GET, null);
    }

    @Override
    public String GET_STUDY_ACTIVITY_LIST(int originId, int originType, int accountId, int bookId, String cookie) {
        originType = originType == 0 ? 11 : originType;
        return WebRequest.send(BaseNetLink.STUDY_ACTIVITY_LIST + "?originId=" + originId + "&originType=" + originType + "&accountId=" + accountId + "&bookId=" + bookId, cookie, BaseNetLink.BaseHeaders_GET, null);
    }

    @Override
    public String GET_STUDY_ACTIVITY_ANSWER(int originId, int originType, int accountId, int bookIdcookie, String cookie) {
        originId = originId == 0 ? 35 : originId;
        originType = originType == 0 ? 11 : originType;
        return WebRequest.send(BaseNetLink.STUDY_ACTIVITY_ANSWER + "?originId=" + originId + "&originType=" + originType + "&accountId=" + accountId + "&bookId=" + bookIdcookie, cookie, BaseNetLink.BaseHeaders_GET, null);
    }

    @Override
    public String POST_STUDY_ACTIVITY_FINISH(String cookie, String Body) {
        return WebRequest.send(BaseNetLink.STUDY_ACTIVITY_FINISH, cookie, BaseNetLink.BaseHeaders_POST, Body);
    }

    @Override
    public String GET_KONWLEDGE_SCORE(int originIds, int gradeId, int accountId, int ver, String cookie) {
        originIds = originIds == 0 ? 35 : originIds;
        return WebRequest.send(BaseNetLink.KONWLEDGE_SCORE + "?originIds=" + originIds + "&gradeId=" + gradeId + "&accountId=" + accountId + "&ver=" + ver, cookie, BaseNetLink.BaseHeaders_GET, null);
    }

    @Override
    public String GET_KONWLEDGE_ANSWER(int accountId, int gradeId, int originIds, int ver, int logicPaperId, int reTry, String cookie) {
        originIds = originIds == 0 ? 35 : originIds;
        reTry = reTry == 0 ? 1 : reTry;
        return WebRequest.send(BaseNetLink.KONWLEDGE_ANSWER + "?accountId=" + accountId + "&gradeId=" + gradeId + "&originIds=" + originIds + "&ver=" + ver + "&logicPaperId=" + logicPaperId + "&reTry=" + reTry, cookie, BaseNetLink.BaseHeaders_GET, null);
    }

    @Override
    public String POST_KONWLEDGE_FINISH(String cookie, String Body) {
        return WebRequest.send(BaseNetLink.KONWLEDGE_FINISH, cookie, BaseNetLink.BaseHeaders_POST, Body);
    }
}
