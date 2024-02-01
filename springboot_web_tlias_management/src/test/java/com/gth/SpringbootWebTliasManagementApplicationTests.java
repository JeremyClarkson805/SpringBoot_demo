package com.gth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwt;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class SpringbootWebTliasManagementApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void testUuid(){
        for (int i=0;i<1000;i++){
            String uuid = UUID.randomUUID().toString();
            System.out.println(uuid);
        }
    }

    //生成JWT
    @Test
    public void testGenJwt(){
        Map<String,Object> claims = new HashMap<>();
        claims.put("id",1);
        claims.put("name","tom");
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256,"gthcmy")//设置签名算法
                .setClaims(claims)//自定义内容（载荷）
                .setExpiration(new Date(System.currentTimeMillis() + 3600*1000))//设置有效期为1h
                .compact();
        System.out.println(jwt);

    }

    @Test
    public void testParseJwt(){
        Claims claims = Jwts.parser()
                .setSigningKey("gthcmy")
                .parseClaimsJws("eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoidG9tIiwiaWQiOjEsImV4cCI6MTcwNjUzOTM4N30.0l4p0PngiHfUzf6wbPlqVusApex8FMlFa7U4HXFUogc")
                .getBody();
        System.out.println(claims);
    }
}
