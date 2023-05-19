package org.mex.sxsd_cons.answers.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * 网络请求
 */
public class WebRequest {

    public static String send(String webUrl, String cookie, Map<String, String> Headers, String Body) {
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(webUrl);
            URLConnection conn = url.openConnection();
            conn.setConnectTimeout(10000);// 设置连接超时时间
            if(cookie != null) conn.setRequestProperty("Cookie", cookie);// 设置Cookie
            if(Headers != null) {
                for (Map.Entry<String, String> entry : Headers.entrySet()) {// 设置请求头
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            conn.setDoInput(true);// 设置允许从服务器获取数据
            // conn.connect();// 连接服务器,不需要手动调用,当调用getInputStream, getOutputStream时会自动连接
            if(Body != null) {
                conn.setDoOutput(true);// 设置允许向服务器提交数据
                out = new PrintWriter(conn.getOutputStream());// 获取输出流
                out.print(Body);// 向输出流写入数据
                out.flush();// 刷新输出流,提交数据
            }
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }
}
