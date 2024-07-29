package com.gth.booksmanager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gth.booksmanager.mapper.ReaderMapper;
import com.gth.booksmanager.pojo.Reader;
import com.gth.booksmanager.pojo.Result;
import com.gth.booksmanager.pojo.User;
import com.gth.booksmanager.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    private ReaderMapper readerMapper;

    @GetMapping("/register")
    public Result register(@RequestParam(name = "readerName")String readerName,
                           @RequestParam(name = "idno")String idno,
                           @RequestParam(name = "code")String code) throws IOException {
        log.info("正在注册用户："+readerName+",身份证号:"+idno);
        User u = readerMapper.selectUserByNameAndId(readerName,idno);
        if (u == null){
            return Result.error("NotUser");
        }
        log.info(u.toString());
        String openId = getOpenId(code);
        Reader r1 = new Reader();
        r1.setReaderName(readerName);
        r1.setIdNumber(idno);
        r1.setOpenId(openId);
        if (u.getUserName()!=null){
            try {
                readerMapper.insertReader(r1);
                Map<String, Object> claims = new HashMap<>();
                claims.put("openId", openId);
                claims.put("readerName",r1.getReaderName());
                claims.put("idNumber",r1.getIdNumber());
                String jwt = JwtUtils.generateJwt(claims);
                log.info(jwt);
                return Result.success(jwt);
            } catch (Exception e) {
                log.info("添加用户失败："+e.getMessage());
                return Result.error("Register Error");//重复注册
            }
        }
        return Result.error("未知错误，请联系管理员！");
    }

    @GetMapping("/loginByOpenId")
    public Result testOpenId(@RequestParam(name = "code") String code, @RequestParam(name = "nickName") String nickName) throws IOException {
        String openId = getOpenId(code);//从微信官方服务器返回的OpenId
        try {                           //没出错就说明在数据库中查到该用户的openId了
            Reader r = readerMapper.selectByOpenId(openId);
            log.info("用户信息："+r.toString());
//            String oid = r.getOpenId();
//            log.info("nickName: " + nickName);
//            log.info("OpenId: " + openId);
            return Result.success(r);
        } catch (Exception e){
            log.info("未注册用户nickName：{}，openId：{}",nickName,openId);
            Reader r = new Reader();
            r.setOpenId(openId);
            return Result.error("IllegalUser",r);
        }
    }

    @GetMapping("/loginByCode")
    public Result loginBySession(@RequestParam(name = "code")String code) throws IOException {
        String openId = getOpenId(code);
        log.info(code);
        log.info("loginByCode: " + openId);
        Reader r = readerMapper.selectByOpenId(openId);
        if (r != null) {
            Map<String, Object> claims = new HashMap<>();
            claims.put("openId", openId);
            claims.put("readerName",r.getReaderName());
            claims.put("idNumber",r.getIdNumber());
            String jwt = JwtUtils.generateJwt(claims);
            log.info("token:"+jwt);
            return Result.success(jwt);
        }
        else return Result.error("IllegalUser");
    }

    public String getOpenId(String code) throws IOException {
        String url = "https://api.weixin.qq.com/sns/jscode2session";
        url += "?appid=wx13d5b154c0b3722f";
        url += "&secret=242248755172aa2644abb69183843590";
        url += "&js_code=" + code;
        url += "&grant_type=authorization_code";
        url += "&connect_redirect=1";
        String res = null;
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // DefaultHttpClient();
        HttpGet httpget = new HttpGet(url);    //GET方式
        CloseableHttpResponse response = null;
        // 配置信息
        RequestConfig requestConfig = RequestConfig.custom()          // 设置连接超时时间(单位毫秒)
                .setConnectTimeout(5000)                    // 设置请求超时时间(单位毫秒)
                .setConnectionRequestTimeout(5000)             // socket读写超时时间(单位毫秒)
                .setSocketTimeout(5000)                    // 设置是否允许重定向(默认为true)
                .setRedirectsEnabled(false).build();           // 将上面的配置信息 运用到这个Get请求里
        httpget.setConfig(requestConfig);                         // 由客户端执行(发送)Get请求
        response = httpClient.execute(httpget);                   // 从响应模型中获取响应实体

        HttpEntity responseEntity = response.getEntity();
        log.info("响应状态为:" + response.getStatusLine());
        if (responseEntity != null) {
            res = EntityUtils.toString(responseEntity);
//            System.out.println("响应内容长度为:" + responseEntity.getContentLength());
//            System.out.println("响应内容为:" + res);
        }
        // 释放资源
        if (httpClient != null) {
            httpClient.close();
        }
        if (response != null) {
            response.close();
        }
        JSONObject jo = JSON.parseObject(res);
        String openid = jo.getString("openid");
//        log.info("nickName:" + nickName);
//        log.info("openid:" + openid);
        return openid;
    }
}
