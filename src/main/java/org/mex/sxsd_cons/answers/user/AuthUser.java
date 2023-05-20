package org.mex.sxsd_cons.answers.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.mex.sxsd_cons.answers.net.BaseNetLink;
import org.mex.sxsd_cons.answers.net.WebRequest;


/**
 * 已登录的用户
 * 包含更多的用户信息
 * 只在运行时存在, 不保存
 */
public class AuthUser implements AuthUserInterface{

    public String PHONE;
    public String COOKIE;

    /**
     * 用户信息,包含
     * wechatUserId
     * phone
     * userName
     * headPic
     * roleId
     * accountId
     * schoolId
     * classFullName
     * schoolName
     * tclassId
     * tclassCode
     * subjectId
     * subjectList
     * appId
     * gradeId
     * createUser
     * teacherName
     * wstate
     * activityId
     * activityName
     * inschool
     */
    public JsonObject INFO;


    /**
     * 通过基础用户BaseUser更新用户信息
     * @param user 基础用户BaseUser
     * @return 是否更新成功
     */
    @Override
    public boolean Up_User_Info(BaseUser user) {
        this.PHONE = user.Phone;
        return Up_User_Info_ByCookie(user.Cookie);
    }

    /**
     * 通过Cookie更新用户信息
     * 注意这种更新方式无法获取用户的手机号
     * @param cookie Cookie
     * @return 是否更新成功
     */
    public boolean Up_User_Info_ByCookie(String cookie){
        this.COOKIE = cookie;
        String res = WebRequest.send(BaseNetLink.LOGIN_GETUSERINFO_LINK, COOKIE, BaseNetLink.BaseHeaders_GET, null);
        if(res == null) return false;
        Gson gson = new Gson();
        JsonObject resData = gson.fromJson(res, JsonObject.class);
        if(resData.get("errCode").getAsString().equals("0")) {
            INFO = resData.get("data").getAsJsonObject();
            return true;
        }
        return false;
    }

    /**
     * 清除用户信息
     */
    public void Clear() {
        this.COOKIE = null;
        this.PHONE = null;
        this.INFO = null;
    }
}
