package com.tzy.demo.seed.core;

import com.alibaba.fastjson.JSON;

/**
 * @author tangzanyong
 * @description 统一API响应结果封装
 * @date 2020/5/20
 **/
public class Result<T> {
    private int code;
    private String message;
    private T data;

    public Result setCode(ResultCode resultCode) {
        this.code = resultCode.code();
        return this;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public Result setMessage(String message) {
        this.message = message;
        return this;
    }

    public T getData() {
        return data;
    }

    public Result setData(T data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
