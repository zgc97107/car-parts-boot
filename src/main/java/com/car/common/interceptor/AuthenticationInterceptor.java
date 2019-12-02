package com.car.common.interceptor;

import com.car.common.annotation.NoLoginRequired;
import com.car.common.exception.UnAuthenticationException;
import com.car.common.utils.JwtUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * @author liyifan
 * @description: 自定义注解拦截器
 * @date 2019/5/24 15:30
 */
public class AuthenticationInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        // 跨域预请求直接通过
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        NoLoginRequired methodAnnotation = method.getAnnotation(NoLoginRequired.class);
        // 没有 @NoLoginRequired 注解，需要认证
        if (methodAnnotation == null) {
            String ajaxHeader = request.getHeader("X-Requested-With");
            boolean isAjax = "XMLHttpRequest".equals(ajaxHeader);
            // 执行认证
            // 从cookie中取token
            String token = JwtUtil.getToken();
            if (StringUtils.isEmpty(token)) {
                if (!isAjax){
                    response.sendRedirect("/login.html");
                }
                throw new UnAuthenticationException("token失效，请重新登录");
            }
            try {
                // token解密
                return JwtUtil.verify(token);
            } catch (Exception e) {
                if (!isAjax){
                    response.sendRedirect("/login.html");
                }
                throw new UnAuthenticationException("token失效，请重新登录");
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
