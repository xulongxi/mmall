package com.mmall.common;

/**
 * 项目名：   mmall
 * 包名：     com.mmall.common
 * 文件名：   Const
 * @author   Longxi XU
 * 创建时间： 2017/11/17 2:33
 * 描述：     常量类
 */
public class Const {
    public static final String CURRENT_USER = "currentUser";

    public interface Role{
        //普通用户
        int ROLE_CUSTOMER = 0;
        //管理员
        int ROLE_ADMIN = 1;
    }

    public static final String USERNAME = "username";
    public static final String EMAIL = "email";
}
