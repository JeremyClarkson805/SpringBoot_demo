package com.gth.booksmanager.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Slf4j
@Service
public class IsbnService {

    public JSONObject getBookInfo(String ISBN){
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String url = "http://data.isbn.work/openApi/getInfoByIsbn?isbn=" + ISBN + "&appKey=ae1718d4587744b0b79f940fbef69e77";
        String resp = "";
        try {
            // 创建httpget.
            HttpGet httpget = new HttpGet(url);
            System.out.println("executing request " + httpget.getURI());
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                System.out.println("--------------------------------------");
                // 打印响应状态
                System.out.println(response.getStatusLine());
                if (entity != null) {
                    // 打印响应内容长度
//                    System.out.println("Response content length: " + entity.getContentLength());
                    // 打印响应内容
                    resp = EntityUtils.toString(entity);
//                    System.out.println("Response content: " + resp);
                }
//                System.out.println("------------------------------------");
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        JSONObject jsonObject = JSON.parseObject(resp);
        JSONObject data = (JSONObject) jsonObject.get("data");
//        jsonObject.get("code");
//        log.info(jsonObject.toJSONString());
//        log.info(data.get("pictures").toString());
        return data;
    }
}