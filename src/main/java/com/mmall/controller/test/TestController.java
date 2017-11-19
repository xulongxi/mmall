package com.mmall.controller.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 项目名：   mmall
 * 包名：     com.mmall.controller.test
 * 文件名：   TestController
 * 创建时间： 2017/11/19 12:42
 * @author   Longxi XU
 * 描述：     学习SpringMVC数据绑定
 */
@Controller
public class TestController {
    @RequestMapping(value = "baseType.do")
    @ResponseBody
    public String baseType(int age){
        return "age:"+age;
    }

    @RequestMapping(value = "wrapperType.do")
    @ResponseBody
    public String wrapperType(Integer age){
        return "age:"+age;
    }

    //http://localhost:8088/array.do?name=Tom&name=Lucy
    @RequestMapping(value = "array.do")
    @ResponseBody
    public String array(String[] name){
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : name) {
            stringBuilder.append(s);
        }
        return stringBuilder.toString();
    }


    //UserTest下面有ContactInfo类
    //http://localhost:8088/object.do?name=Tom&name=Lucy&contactInfo.email=123@123.com
    @RequestMapping(value = "object.do")
    @ResponseBody
    public String object(UserTest user){
        return user.toString();
    }

    //UserTest类和UserTestSame有同名的属性，需要区分
    //http://localhost:8088/sameObject.do?userTest.name=Tom&id=5
    @RequestMapping(value = "sameObject.do")
    @ResponseBody
    public String object(UserTest userTest,UserTestSame userTestSame){
        return userTest.toString()+" "+userTestSame.toString();
    }
    @InitBinder("userTest")
    public void initUserTest(WebDataBinder binder){
        binder.setFieldDefaultPrefix("userTest.");
    }
    @InitBinder("userTestSame")
    public void initUserTestSame(WebDataBinder binder){
        binder.setFieldDefaultPrefix("userTestSame.");
    }


    //List绑定
    @RequestMapping(value = "list.do")
    @ResponseBody
    public String list(UserTest user){
        return user.toString();
    }

}
