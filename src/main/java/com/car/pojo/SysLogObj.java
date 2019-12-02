package com.car.pojo;

import lombok.Data;
import org.slf4j.event.Level;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zgc
 * @since 2019/11/11
 */
@Data
public class SysLogObj implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 日志等级
     */
    private Level level;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户操作
     */
    private String operation;

    /**
     * 系统名
     */
    private String system;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 执行时长(毫秒)
     */
    private Long time;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 异常信息
     */
    private String exception;

    /**
     * 创建时间
     */
    private Date createTime;
}
