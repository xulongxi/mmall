package com.mmall.controller.test;

import java.beans.PropertyEditorSupport;

/**
 * 项目名：   mmall
 * 包名：     com.mmall.controller.test
 * 文件名：   MyPropertyEditor
 * 创建时间： 2017/11/19 15:29
 * @author Longxi XU
 * 描述：     转化器，把和前端约定的格式转成某种格式
 */
public class MyPropertyEditor extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        UserTest userTest = new UserTest();
        String[] textArray = text.split(",");
        userTest.setName(textArray[0]);
        userTest.setId(Integer.valueOf(textArray[1]));
        setValue(userTest);
    }

    public static void main(String[] args) {
        MyPropertyEditor myPropertyEditor = new MyPropertyEditor();
        myPropertyEditor.setAsText("tom,11");
        System.out.println(myPropertyEditor.getValue());
        //输出UserTest{id=11, name='tom', contactInfo=null}
    }
}