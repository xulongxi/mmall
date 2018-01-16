package com.mmall.test;

import org.junit.Test;

import java.math.BigDecimal;

/**
 * 项目名：   mmall
 * 包名：     com.mmall.test
 * 文件名：   BigDecimalTest
 * 创建时间： 2017/11/26 12:04
 *
 * @author Longxi XU
 * 描述：     TODO
 */

public class BigDecimalTest {

    @Test
    public void test1(){
        System.out.println(0.05+0.01);
    }

    @Test
    public void test2(){
        BigDecimal bigDecimal = new BigDecimal("0.01");
        BigDecimal bigDecimal2 = new BigDecimal("0.05");
        System.out.println(bigDecimal.add(bigDecimal2));
    }
}
