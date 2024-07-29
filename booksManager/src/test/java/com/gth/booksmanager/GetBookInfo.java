package com.gth.booksmanager;



import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gth.booksmanager.common.IsbnService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.text.ParseException;

@Slf4j
@SpringBootTest
class BookInfoTest {

//    @Test
//    void test_1(){
//        String url = "http://data.isbn.work/openApi/getInfoByIsbn";
//        try {
//            String result = HttpClientUtils.get(url);
//            System.out.println(result);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    void test_2(String ISBN){
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
//        log.info(data.toString());
    }


    @Test
    void test_3() {
//        test_2("9787020170791");
        IsbnService isbnService = new IsbnService();
        JSONObject data = isbnService.getBookInfo("9787020170791");
        log.info(data.toString());
        log.info(data.getString("pictures").replace("[\"","").replace("\"]",""));
    }



}