package com.jiang.mall.exception;

public class MallException extends  RuntimeException {
    private  final  Integer code;
    private  final  String message;
    public  MallException(Integer code,String message){
        this.code = code;
        this.message = message;
    }
    public MallException(MallExceptionEunm exceptionEunm){
        this(exceptionEunm.getCode(), exceptionEunm.getMes());
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
