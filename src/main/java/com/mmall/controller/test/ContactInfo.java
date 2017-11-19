package com.mmall.controller.test;

/**
 * 项目名：   mmall
 * 包名：     com.mmall.controller.test
 * 文件名：   ContactInfo
 * 创建时间： 2017/11/19 13:35
 *
 * @author Longxi XU
 * 描述：     TODO
 */
public class ContactInfo {
    private String email;
    private String address;

    @Override
    public String toString() {
        return "ContactInfo{" +
                "email='" + email + '\'' +
                ", address='" + address + '\'' +
                '}';
    }

    public ContactInfo(String email, String address) {
        this.email = email;
        this.address = address;
    }

    public ContactInfo() {
        super();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
