package com.car.common.exception;

/**
 * @author zgc
 * @since 2019/9/27
 */
public class UnAuthenticationException extends BusinessException{
    public UnAuthenticationException(String message){
        super(message);
    }
}
