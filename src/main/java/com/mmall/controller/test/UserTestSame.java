package com.mmall.controller.test;

/**
 * 项目名：   mmall
 * 包名：     com.mmall.controller.test
 * 文件名：   UserTestSame
 * 创建时间： 2017/11/19 13:59
 *
 * @author Longxi XU
 * 描述：     属性名和UserTest一样，要求同时接受UserTest和UserTestSame两个对象
 */
public class UserTestSame {

    private Integer id;

    private String name;

    @Override
    public String toString() {
        return "UserTestSame{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contactInfo=" + contactInfo +
                '}';
    }

    private ContactInfo contactInfo;

    public void setContactInfo(ContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public UserTestSame(Integer id, String name, ContactInfo contactInfo) {
        this.id = id;
        this.name = name;
        this.contactInfo = contactInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserTestSame() {
        super();
    }
}
