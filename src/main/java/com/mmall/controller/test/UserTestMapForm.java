package com.mmall.controller.test;

import java.util.List;
import java.util.Map;

/**
 * 项目名：   mmall
 * 包名：     com.mmall.controller.test
 * 文件名：   UserTsetListForm
 * 创建时间： 2017/11/19 14:27
 *
 * @author Longxi XU
 * 描述：     数据绑定List
 */
public class UserTestMapForm {
    private Map<String,UserTest> userTests;

    @Override
    public String toString() {
        return "UserTestMapForm{" +
                "userTests=" + userTests +
                '}';
    }

    public Map<String,UserTest> getUserTests() {
        return userTests;
    }

    public void setUserTests(Map<String,UserTest> userTests) {
        this.userTests = userTests;
    }
}
