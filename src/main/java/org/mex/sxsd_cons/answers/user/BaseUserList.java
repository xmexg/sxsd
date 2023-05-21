package org.mex.sxsd_cons.answers.user;

import org.mex.sxsd_cons.Init;
import org.mex.sxsd_cons.PrintFormat;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 基础用户列表
 * 保存所有用户的手机号和Cookie
 */
public class BaseUserList implements Serializable {
    private HashMap<String, BaseUser> baseUserList;

    public BaseUserList() {
        baseUserList = new HashMap<>();
    }

    public BaseUser getBaseUser(String s) {
        return this.baseUserList.get(s);
    }

    public boolean setBaseUserList(HashMap<String, BaseUser> baseUserList) {
        this.baseUserList = baseUserList;
        return save();
    }

    public List<BaseUser> getBaseUserList() {
        if(this.baseUserList == null) return null;
        return new ArrayList<>(this.baseUserList.values());
    }

    public List<String> getBaseUserPhoneList() {
        if(this.baseUserList == null) return null;
        return new ArrayList<>(this.baseUserList.keySet());
    }

    public boolean addBaseUser(BaseUser baseUser) {
        this.baseUserList.put(baseUser.Phone, baseUser);
        return save();
    }

    public boolean removeBaseUser(String phone) {
        this.baseUserList.remove(phone);
        return save();
    }

    /**
     * 实时保存用户数据
     */
    public boolean save() {
        try {
            String path = Init.CONFIG.File_BaseUserList;
            FileOutputStream fileOut = new FileOutputStream(path);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
            PrintFormat.println("保存用户数据失败", PrintFormat.ERROR);
            return false;
        }
        return true;
    }

}
