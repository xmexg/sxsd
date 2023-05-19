package org.mex.sxsd_cons.answers.net;

import java.util.HashMap;

public class BaseNetLink  {

    /**
     * 基础网络链接
     *
     * ROOT_DOMAIN 根域名
     * ROOT_URL 根链接
     * LOGIN_GETVALIDATE_LINK 获取验证码链接
     * LOGIN_LOGIN_LINK 登录链接
     * LOGIN_GETUSERINFO_LINK 获取用户信息链接
     * USER_SCORE 获取用户成绩链接
     * STUDY_MAIN_LIST 获取主页书目列表
     * STUDY_ACTIVITY_LIST 获取书目闯关列表
     * STUDY_ACTIVITY_ANSWER 获取闯关题目(及答案)
     * STUDY_ACTIVITY_FINISH 完成闯关
     * KONWLEDGE_SCORE 获取知识关卡成绩
     * KONWLEDGE_ANSWER 获取知识关卡题目(及答案)
     * KONWLEDGE_FINISH 完成知识关卡
     */
    public static String ROOT_DOMAIN = "sxact.5rs.me",
        ROOT_URL = "https://"+ROOT_DOMAIN,
        LOGIN_GETVALIDATE_LINK = ROOT_URL + "/shandongbasic/v1.0/shortMessage/sendPhoneMessage",
        LOGIN_LOGIN_LINK = ROOT_URL + "/shandongbasic/v1.0/wechatUser/author",
        LOGIN_GETUSERINFO_LINK = ROOT_URL + "/shandongbasic/v1.0/read/getUserAccount",
        USER_SCORE = ROOT_URL + "/shandongbasic/v1.0/read/getStudentScore",
        STUDY_MAIN_LIST = ROOT_URL + "/shandongbasic/v1.0/read/getAppColumnByName4Wechat",
        STUDY_ACTIVITY_LIST = ROOT_URL + "/shandongbasic/v1.0/pretestread/getPapers",
        STUDY_ACTIVITY_ANSWER = ROOT_URL + "/shandongbasic/v1.0/pretestread/getPaperQuestions",
        STUDY_ACTIVITY_FINISH = ROOT_URL + "/shandongbasic/v1.0/pretestread/getPaperResults",
        KONWLEDGE_SCORE = ROOT_URL + "/shandongbasic/v1.0/pretestread/getQuizPapers",
        KONWLEDGE_ANSWER = ROOT_URL + "/shandongbasic/v1.0/pretestread/getQuizPaperQuestions",
        KONWLEDGE_FINISH = ROOT_URL + "/shandongbasic/v1.0/pretestread/getQuizPaperResults";

    /**
     * BaseHeaders 基础请求头
     */
    public static HashMap<String, String> BaseHeaders_POST = new HashMap<String, String>() {{
        put("Host", ROOT_DOMAIN);
        put("Connection", "close");
        put("Access-Control-Allow-Origin", "*");
        put("Accept", "application/json, text/plain, */*");
        put("Content-Type", "application/json;charset=UTF-8");
        put("Origin", ROOT_URL);
        put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.5672.93 Safari/537.36");
        put("Sec-Ch-Ua", "\"Chromium\";v=\"113\", \"Not-A.Brand\";v=\"24\"");
        put("Sec-Ch-Ua-Mobile", "?0");
        put("Sec-Ch-Ua-Platform", "\"Windows\"");
        put("Sec-Fetch-Site", "same-origin");
        put("Sec-Fetch-Mode", "cors");
        put("Sec-Fetch-Dest", "empty");
        put("Referer", ROOT_URL);
        put("Accept-Encoding", "identity");
        put("Accept-Language", "zh-CN,zh;q=0.9");
    }};
    public static HashMap<String, String> BaseHeaders_GET = new HashMap<String, String>() {{
        put("Host", ROOT_DOMAIN);
        put("Connection", "close");
        put("Access-Control-Allow-Origin", "*");
        put("Accept", "application/json, text/plain, */*");
        put("Origin", ROOT_URL);
        put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/113.0.5672.93 Safari/537.36");
        put("Sec-Ch-Ua", "\"Chromium\";v=\"113\", \"Not-A.Brand\";v=\"24\"");
        put("Sec-Ch-Ua-Mobile", "?0");
        put("Sec-Ch-Ua-Platform", "\"Windows\"");
        put("Sec-Fetch-Site", "same-origin");
        put("Sec-Fetch-Mode", "cors");
        put("Sec-Fetch-Dest", "empty");
        put("Referer", ROOT_URL);
        put("Accept-Encoding", "identity");
        put("Accept-Language", "zh-CN,zh;q=0.9");
    }};
}
