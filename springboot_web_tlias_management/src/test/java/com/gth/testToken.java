package com.gth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class testToken {
    // 这是你的签名密钥。在实际应用中，你应该更加小心地保护这个密钥。
    private static final String signKey = "tianhao";
    // 这是JWT的过期时间（以毫秒为单位）。在这个例子中，我们设置它为1小时。
    private static final long expire = 60 * 60 * 1000;

    public static String generateJwt(Map<String, Object> claims){
        String jwt = Jwts.builder()
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS256, signKey)
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .compact();
        return jwt;
    }

    public static Claims parseJWT(String jwt){
        Claims claims = Jwts.parser()
                .setSigningKey(signKey)
                .parseClaimsJws(jwt)
                .getBody();
        return claims;
    }

    public static void main(String[] args) {
        // 创建一个包含你想要在JWT中包含的声明的Map。
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", "user1");
        claims.put("role", "admin");

        // 使用这个Map生成JWT。
        String jwt = generateJwt(claims);

        // 打印生成的JWT。
        System.out.println("Generated JWT: " + jwt);

        System.out.println(parseJWT(jwt));
    }
}
