package org.mex.sxsd_cons;

import org.mex.sxsd_cons.answers.user.AuthUser;
import org.mex.sxsd_cons.answers.user.BaseUserList;

import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 * 初始化
 */
public class Init {

    public static final String VERSION = "0.0.1";// 版本号
    public static Config CONFIG = new Config();// 配置文件
    public static BaseUserList BASEUSERLIST = new BaseUserList();// 基础用户列表
    public static AuthUser AUTHUSER = new AuthUser();// 当前用户身份

    public Init() {
        PrintFormat.println("当前版本: "+VERSION, PrintFormat.LIGHT_BLUE);
        PrintFormat.println("\n    | 本程序免费,如果你付费购买获得,请退款+差评\n    | 如果你认为本程序对你有所帮助,可以请我喝杯奶茶吗?\n    | 注意这是无偿的,是否赞助均不影响使用本程序\n    | 爱发电(记得备注学号) : https://afdian.net/a/d2t5ZA\n", PrintFormat.LIGHT_PURPLE);
        PrintFormat pf = new PrintFormat(PrintFormat.PURPLE);
        pf.println(" 已加载本地用户数据 : "+UpBaseUserList());
    }

    public static boolean UpBaseUserList(){
        try {
            File file = new File(CONFIG.File_BaseUserList);
            if (!file.exists()) {
                return false;
            }
            FileInputStream fileIn = new FileInputStream(CONFIG.File_BaseUserList);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            BASEUSERLIST = (BaseUserList) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            BASEUSERLIST = new BaseUserList();
            PrintFormat.println("加载本地用户数据失败,可能 "+CONFIG.File_BaseUserList+" 已损坏", PrintFormat.ERROR);
            return false;
        }
        return true;
    }
}
