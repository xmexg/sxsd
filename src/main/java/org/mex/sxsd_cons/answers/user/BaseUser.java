package org.mex.sxsd_cons.answers.user;

import java.io.Serializable;

/**
 * 基础用户
 * 保存用户的手机号和Cookie
 */
public class BaseUser implements Serializable {

    public String Phone;
    public String Cookie;

    public BaseUser(String Phone, String Cookie) {
        this.Phone = Phone;
        this.Cookie = Cookie;
    }
}
