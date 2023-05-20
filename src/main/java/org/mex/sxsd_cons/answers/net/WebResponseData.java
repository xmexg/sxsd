package org.mex.sxsd_cons.answers.net;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * 网络响应数据
 */
public class WebResponseData {

    /**
     * 获取数据
     * 出现错误时返回null,否则返回JsonObject
     * @param res 原始响应数据
     * @return data的JsonObject
     */
    public static String GetDataAsString(String res){
        if (res == null)
            return null;
        Gson gson = new Gson();
        JsonObject resData = gson.fromJson(res, JsonObject.class);
        if(resData.get("errCode").getAsString().equals("0")) {
            return resData.get("data").toString();
        }
        return null;
    }

    public static JsonObject GetDataAsJsonObject(String res){
        if (res == null) return null;
        Gson gson = new Gson();
        String JOres = GetDataAsString(res);
        if (JOres == null) return null;
        if (JOres.startsWith("[") && JOres.endsWith("]")) {
            JOres = JOres.substring(1, JOres.length() - 1);
        }
        return gson.fromJson(JOres, JsonObject.class);
    }

    public static JsonArray GetDataAsJsonArray(String res){
        if (res == null) return null;
        Gson gson = new Gson();
        String JOres = GetDataAsString(res);
        if (JOres == null) return null;
        JsonArray resData = null;
        if (JOres.startsWith("[") && JOres.endsWith("]")) {
            resData = gson.fromJson(JOres, JsonArray.class);
        }
        return resData;
    }
}
