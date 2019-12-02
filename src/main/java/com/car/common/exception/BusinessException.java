package com.car.common.exception;

/**
  * @description: 自定义异常
  * @author liyifan
  * @date 2019/5/24 15:37
  */
public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = -2825436079063723410L;

    private String code; // 异常对应的返回码
    private String message; // 异常对应的描述信息

    public BusinessException() {
        super();
    }

    public BusinessException(String message) {
        super(message);
        this.message = message;
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }

    public BusinessException(Throwable e) {
        super(e);
        this.message = e.getMessage();
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
