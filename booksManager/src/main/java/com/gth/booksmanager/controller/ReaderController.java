package com.gth.booksmanager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gth.booksmanager.mapper.BookMapper;
import com.gth.booksmanager.mapper.ReaderMapper;
import com.gth.booksmanager.pojo.Book;
import com.gth.booksmanager.pojo.Reader;
import com.gth.booksmanager.pojo.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;


@Slf4j
@RestController
@RequestMapping("/reader")
public class ReaderController {
    @Autowired
    private ReaderMapper readerMapper;

    @GetMapping("/hello")
    public Result Hello(){ return Result.success("Hello World");}

    @GetMapping("/{id}")
    public Result selectByOpenId(String id){
        return null;
    }
}
