package org.mex.sxsd_cons.answers.user;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.mex.sxsd_cons.PrintFormat;
import org.mex.sxsd_cons.answers.net.BaseNetLink;
import org.mex.sxsd_cons.answers.net.WebRequest;

import java.util.Map;

public class UserLogin {

    /**
     * 有登录功能
     * POST_LOGIN_GETVALIDATE_LINK 获取验证码链接
     * POST_LOGIN_LOGIN_LINK 登录链接
     */

    /**
     * 发送验证码
     * @param phone 手机号
     * @return 是否发送成功
     */
    public static boolean Send_Validate(String phone){
        String body = "{\"phone\":\""+phone+"\",\"phoneAreaCode\":86,\"state\":3}";
        String res =  WebRequest.send(BaseNetLink.LOGIN_GETVALIDATE_LINK, "userInfo=officialAccountsId%3D50", BaseNetLink.BaseHeaders_POST, body);
        if(res.startsWith("{\"errCode\":0"))
            return true;
        else {
            PrintFormat.println(res, PrintFormat.ERROR);
            return false;
        }
    }

    /**
     * 登录
     * @param phone 手机号
     * @param validate 验证码
     * @return cookie
     */
    public static String Login(String phone, String validate){
        String cookie = null;
        String body = "{\"phone\":\""+phone+"\",\"validate\":\""+validate+"\"}";
        String res = WebRequest.send(BaseNetLink.LOGIN_LOGIN_LINK, "userInfo=officialAccountsId%3D50", BaseNetLink.BaseHeaders_POST, body);
        JsonObject resData = new Gson().fromJson(res, JsonObject.class);
        if(resData.get("errCode").getAsString().equals("0")) {
            JsonObject dataMap = resData.get("data").getAsJsonObject();
            String wechatUserId = dataMap.get("wechatUserId").getAsString();
            String officialAccountsId = dataMap.get("officialAccountsId").getAsString();
            cookie = "officialAccountsId%3D" + officialAccountsId + "%26wechatUserId%3D" + wechatUserId;
        }
        return cookie;
    }
}
