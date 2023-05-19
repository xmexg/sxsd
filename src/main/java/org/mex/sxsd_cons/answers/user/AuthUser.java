package org.mex.sxsd_cons.answers.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.mex.sxsd_cons.answers.net.BaseNetLink;
import org.mex.sxsd_cons.answers.net.WebRequest;

import java.util.Map;

/**
 * 已登录的用户
 *
 * 包含更多的用户信息
 *
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


    @Override
    public Boolean Up_User_Info(String cookie) {
        this.COOKIE = cookie;
        String res = WebRequest.send(BaseNetLink.LOGIN_GETUSERINFO_LINK, cookie, BaseNetLink.BaseHeaders_GET, null);
        if(res == null) return false;
        Gson gson = new Gson();
        Map<String, Object> resData = gson.fromJson(res, Map.class);
        //读取resData中errCode的值
        if(resData.get("errCode").equals(0)){
            //读取resData中data的值
            String data = resData.get("data").toString();
            INFO = gson.fromJson(data, JsonObject.class);
            return true;
        }
        return false;
    }

    @Override
    public String GET_USER_SCORE(int accountId, String cookie) {
        return WebRequest.send(BaseNetLink.USER_SCORE + "?accountId=" + accountId, cookie, BaseNetLink.BaseHeaders_GET, null);
    }

    /**
     * 清除用户信息
     */
    public void Clear() {
        this.PHONE = null;
        this.COOKIE = null;
        this.INFO = null;
    }
}
