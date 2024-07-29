package com.gth.booksmanager;

import com.gth.booksmanager.mapper.ReaderMapper;
import com.gth.booksmanager.pojo.Reader;
import com.gth.booksmanager.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.gth.booksmanager.utils.JwtUtils.generateJwt;

@Slf4j
@SpringBootTest
public class TestToken {
    @Autowired
    ReaderMapper readerMapper;

//    private static String signKey = "tianhao805";
//    private static Long expire = 43200000L;

//    public static String generateJwt(Map<String, Object> claims){
//        String jwt = Jwts.builder()
//                .addClaims(claims)
//                .signWith(SignatureAlgorithm.HS256, signKey)
//                .setExpiration(new Date(System.currentTimeMillis() + expire))
//                .compact();
//        return jwt;
//    }

    @Test
    public void test_1(){
        Map<String, Object> claims = new HashMap<>();
        Reader r = new Reader();
        r.setOpenId("oyPDG63DGsKkGv0vRmbWJFWhNeE4");
        r.setReaderName("关天昊");
        claims.put("openId",r.getOpenId());
        claims.put("readerName",r.getReaderName());
        String jwt = JwtUtils.generateJwt(claims);
        System.out.println("生成的:"+jwt);
        System.out.println("解析jwt的内容:"+ JwtUtils.parseJWT(jwt));
    }

    @Test
    public void test_2() {
        String jwt = "eyJhbGciOiJIUzI1NiJ9.eyJyZWFkZXJOYW1lIjoi5byg5piV5pqEIiwib3BlbklkIjoib3lQREc2M0RHc0trR3YwdlJtYldKRldoTmVFNCIsImlkTnVtYmVyIjoiNDYwMTAyMjAwMzA2MjExODIxIiwiZXhwIjoxNzIxOTYyMjk2fQ.Ap8OHLY_4j7Z2dFfJoAIgQNVFlu8kN-w_rCGXi8qEO0";
        System.out.println(JwtUtils.parseJWT(jwt));
    }

    @Test
    public void test_3() {
        System.out.println(readerMapper.getReaderIdByOpenId("oyPDG63DGsKkGv0vRmbWJFWhNeE4"));
        log.error(readerMapper.getReaderIdByOpenId("oyPDG63DGsKkGv0vRmbWJFWhNeE4"));
    }
}
