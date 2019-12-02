package com.car.common.aspect;

import com.alibaba.fastjson.JSON;
import com.car.common.annotation.SysLog;
import com.car.common.utils.JwtUtil;
import com.car.common.utils.WebUtil;
import com.car.pojo.SysLogObj;
import com.car.service.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * @author ZGC
 * @date 2019-6-26
 **/
@Aspect
@Slf4j
@Component
public class SysLogAspect {

    private static final String TOPIC_ID = "SysLog";

    private static final String SYSTEM_NAME = "car-parts-boot";

    @Autowired
    ProducerService producerService;

    @Around("@annotation(com.car.common.annotation.SysLog)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = null;
        String exceptionMessage = null;
        try {
            result = point.proceed();
        } catch (Exception e) {
            log.error("{}" + e);
            exceptionMessage = e.getClass().getName() + ":" + e.getMessage();
        }
        long time = System.currentTimeMillis() - beginTime;
        sendSysLog(point, time, exceptionMessage);
        return result;
    }

    private void sendSysLog(ProceedingJoinPoint point, long time, String exception) {
        SysLogObj sysLogObj = new SysLogObj();
        sysLogObj.setSystem(SYSTEM_NAME);
        sysLogObj.setException(exception);

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        // 自定义注解中携带的信息
        SysLog sysLog = method.getAnnotation(SysLog.class);
        sysLogObj.setLevel(sysLog.level());
        sysLogObj.setOperation(sysLog.operation());
        // 请求方法名
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLogObj.setMethod(className + "." + methodName + "()");
        // 请求参数
        Object[] args = point.getArgs();
        String param = Arrays.stream(args)
                .filter(arg -> !(arg instanceof HttpServletResponse || arg instanceof HttpRequest || arg instanceof HttpSession))
                .map(JSON::toJSONString)
                .collect(Collectors.joining(","));
        sysLogObj.setParams(param);
        // IP地址
        String serverIp = WebUtil.getRequestIp();
        sysLogObj.setIp(serverIp);
        // 当前用户名
        String username = JwtUtil.getUsername();
        sysLogObj.setUsername(username);
        // 执行时长
        sysLogObj.setTime(time);
        sysLogObj.setCreateTime(new Date());
        // 发送至kafka队列
        producerService.sendAsyncMessage(TOPIC_ID, JSON.toJSONString(sysLogObj));
    }

}
