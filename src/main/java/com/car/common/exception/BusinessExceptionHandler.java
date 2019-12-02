package com.car.common.exception;

import com.car.common.utils.R;
import com.car.service.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author ZGC
 * @date 2019-6-25
 * @description 全局异常处理
 **/
@Slf4j
@ControllerAdvice
public class BusinessExceptionHandler {

    private static final String TOPIC_ID = "exception";

    private static final String SYSTEM_NAME = "car-parts-boot";

    @Autowired
    ProducerService producerService;

    @ResponseBody
    @ExceptionHandler(UnAuthenticationException.class)
    public R unAuthenticationExceptionHandler(UnAuthenticationException e){
        return R.unAuthentication(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public R businessExceptionHandler(BusinessException e){
        producerService.sendSyncMessage(TOPIC_ID,SYSTEM_NAME+"抛出异常，异常类型："+e.getClass().getName()+"，异常信息："+e.getMessage());
        log.warn("{}",e.getMessage());
        return R.failed(e.getMessage());
    }

    @ResponseBody
    @ExceptionHandler
    public R otherExceptionHandler(RuntimeException e) {
        producerService.sendSyncMessage(TOPIC_ID,SYSTEM_NAME+"抛出异常，异常类型："+e.getClass().getName()+"，异常信息："+e.getMessage());
        log.warn("{}",e);
        return R.failed(e.getMessage());
    }
}
