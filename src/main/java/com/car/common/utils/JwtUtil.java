package com.car.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author ZGC
 * @date 2019-6-21
 **/
public class JwtUtil {

    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);

    /**
     * 过期时间
     */
    private static final long EXPIRE_TIME = 1000 * 60 * 60 * 24;

    /**
     * token私钥
     */
    private static final String TOKEN_SECRET = "242af3d6319811e9b210d663bd873d93";

    /**
     * 加密
     *
     * @param userId
     * @param classId
     * @param schoolId
     * @return
     */
    public static String sign(long userId, long roleId, String nickname) {
        try {
            //设置过期时间
            Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
            //设置加密算法
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            Map<String, Object> header = new HashMap<>(2);
            header.put("typ", "JWT");
            header.put("alg", "HS256");
            //创建
            return JWT.create()
                    .withHeader(header)
                    .withClaim("userId", userId)
                    .withClaim("roleId", roleId)
                    .withClaim("nickname", nickname)
                    .withExpiresAt(date)
                    .sign(algorithm);
        } catch (UnsupportedEncodingException e) {
            LOGGER.error("token加密失败" + e.getMessage());
            return null;
        }
    }

    /**
     * 获取token
     * @return
     */
    public static String getToken() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = request.getHeader("Access-Token");
        if (token == null) {
            Cookie[] cookies = request.getCookies();
            if (cookies == null) {
                return null;
            }
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                }
            }
        }
        return token;
    }

    /**
     * 验证
     *
     * @param token
     * @return
     */
    public static boolean verify(String token) throws UnsupportedEncodingException,TokenExpiredException {
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        JWTVerifier verifier = JWT.require(algorithm)
                .build();
        verifier.verify(token);
        return true;
    }

    /**
     * 获取token中的信息
     *
     * @return
     */
    public static Long getUserId() {
        String token = JwtUtil.getToken();
        if (token == null) {
            return null;
        }
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("userId").asLong();
    }

    public static Long getRoleId() {
        String token = JwtUtil.getToken();
        if (token == null) {
            return null;
        }
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("roleId").asLong();
    }

    public static String getUsername(){
        String token = JwtUtil.getToken();
        if (token == null) {
            return null;
        }
        DecodedJWT jwt= JWT.decode(token);
        return jwt.getClaim("nickname").asString();
    }
}
