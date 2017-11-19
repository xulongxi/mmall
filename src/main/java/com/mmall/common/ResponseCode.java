package com.mmall.common;

/**
 * 项目名：   mmall
 * 包名：     com.mmall.common
 * 文件名：   ResponseCode
 * @author   Longxi XU
 * 创建时间： 2017/11/16 22:02
 * 描述：     TODO
 */
public enum ResponseCode {
    SUCCESS(0, "SUCCESS"),
    ERROR(1, "ERROR"),
    NEED_LOGIN(10, "NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2, "ILLEGAL_ARGUMENT");

    private final int code;
    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
