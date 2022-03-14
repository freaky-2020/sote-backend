package com.upc.eden.commen.api;

/**
 * @Author: CS Dong
 * @Date: 2022/03/11/20:26
 * @Description: 封装API的错误码
 */
public interface IErrorCode {
    long getCode();

    String getMessage();
}
