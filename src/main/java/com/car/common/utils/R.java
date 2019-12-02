package com.car.common.utils;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ZGC
 * @date 2019-6-21
 **/
@Data
public class R<T> implements Serializable {

    private static final long serialVersionUID = -2825436079063723409L;

    private static final String OK = "1";
    private static final String FAILED = "0";
    /**
     * 未登录
     */
    private static final String UN_AUTHENTICATION = "501";

    private String code;
    private String message;
    private T data;

    private static <T> R<T> buildResponse(String code, String message, T data){
        R<T> r = new R<>();
        r.code = code;
        r.message = message;
        r.data = data;
        return r;
    }

    public static <T> R<T> ok(){
        return buildResponse(R.OK,"成功。",null);
    }
    public static <T> R<T> ok(T data){
        return buildResponse(R.OK,"成功。",data);

    }
    public static <T> R<T> ok(String message, T data){
        return buildResponse(R.OK,message,data);
    }


    public static <T> R<T> failed(String message){
        return buildResponse(R.FAILED,message,null);
    }

    public static <T> R<T> failed(String message, T data){
        return buildResponse(R.FAILED,message,data);
    }

    public static <T> R<T> unAuthentication(String message){
        return buildResponse(R.UN_AUTHENTICATION,message,null);
    }

}

