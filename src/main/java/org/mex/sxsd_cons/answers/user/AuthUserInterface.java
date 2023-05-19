package org.mex.sxsd_cons.answers.user;

/**
 * 已注册用户接口
 */
public interface AuthUserInterface {

    /**
     * LOGIN_GETUSERINFO_LINK 获取用户信息链接
     * USER_SCORE 获取用户成绩链接
     */
    //cookie为Cookie
    Boolean Up_User_Info(String cookie);
    // accountId为用户id cookie为Cookie
    String GET_USER_SCORE(int accountId, String cookie);
    // 清除用户信息
    void Clear();
}
