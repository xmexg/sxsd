package org.mex.sxsd_cons.answers.user;

/**
 * 已注册用户接口
 */
public interface AuthUserInterface {

    /**
     * LOGIN_GETUSERINFO_LINK 获取用户信息链接
     */
    boolean Up_User_Info(BaseUser user);
    //cookie为Cookie
    boolean Up_User_Info_ByCookie(String cookie);
    // 清除用户信息
    void Clear();
}
