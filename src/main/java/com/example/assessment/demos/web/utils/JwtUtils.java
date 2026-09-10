package com.example.assessment.demos.web.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;
import java.util.Map;


public class JwtUtils {

    // 密钥，与测试类中保持一致
    public static final String SECRET_KEY = "token";

    // 过期时间：12小时（毫秒）
    private static final long EXPIRATION_TIME = 12 * 60 * 60 * 1000;

    /**
     * 生成JWT令牌
     * @param claims 包含在令牌中的数据
     * @return JWT令牌字符串
     */
    public static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .addClaims(claims)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .compact();
    }

    /**
     * 解析JWT令牌
     * @param token JWT令牌字符串
     * @return 解析出的Claims对象，包含令牌中的数据
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    /**
     * 获取JWT令牌的剩余有效时间（秒）
     * @param token JWT令牌字符串
     * @return 剩余有效时间（秒），如果已过期则返回0
     */
    public static long getRemainingExpirationSeconds(String token) {
        Claims claims = parseToken(token);
        Date expiration = claims.getExpiration();
        long remainingMillis = expiration.getTime() - System.currentTimeMillis();
        return Math.max(remainingMillis / 1000, 0);
    }
}