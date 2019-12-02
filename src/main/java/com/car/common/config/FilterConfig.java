package com.car.common.config;

import com.car.common.xss.XssFilter;
import com.car.common.xss.SqlFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;

/**
 * @author zgc
 * @since 2019/10/25
 */
@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean xssFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new XssFilter());
        registration.addUrlPatterns("/*");
        registration.setName("xssFilter");
        registration.setOrder(Integer.MAX_VALUE);
        return registration;
    }

    @Bean
    public FilterRegistrationBean sqlFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new SqlFilter());
        registration.addUrlPatterns("/*");
        registration.setName("sqlFilter");
        registration.setOrder(Integer.MIN_VALUE);
        return registration;
    }
}
