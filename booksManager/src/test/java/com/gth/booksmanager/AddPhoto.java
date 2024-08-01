package com.gth.booksmanager;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gth.booksmanager.common.IsbnService;
import com.gth.booksmanager.mapper.BookMapper;
import com.gth.booksmanager.pojo.Book;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

@Slf4j
@SpringBootTest
public class AddPhoto {
    @Autowired
    BookMapper bookMapper;
    @Autowired
    IsbnService isbnService;

    String getBookPhotoByISBN(String ISBN) {
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
//        log.info(data.getString("pictures").replace("[\"","").replace("\"]",""));
        return data.getString("pictures").replace("[\"","").replace("\"]","");
    }

    @Test
    void test_1() {
        List<String> no = bookMapper.getNoPhotoBook();

        for (String ISBN : no) {
            try {
                log.info("ISBN: " + ISBN);
                String photoURL = getBookPhotoByISBN(ISBN);
                log.info("photoURL: " + photoURL);
                Book b = new Book();
                b.setIsbn(ISBN);
                b.setBookPhoto(photoURL);
                bookMapper.updateBook_2(b);
            } catch (Exception e) {
                continue;
            }
        }
        log.info("缺少图片查找补充完成");

//        String photoURL = getBookPhotoByISBN("9787510223327");
//        log.info("photoURL: " + photoURL);
    }

    @Test
    void test_2() throws InterruptedException {

        List<Book> bookList= bookMapper.getMissingBook();

        for (Book book : bookList) {
            JSONObject data = isbnService.getBookInfo(book.getIsbn());
            log.info("data: " + data);
            if (data.isEmpty()) {
                break;
            }
            if (book.getBookName() == "") {
                try {
                    book.setBookName(data.getString("bookName"));
                } catch (Exception e) {
                    log.info("暂无相关信息");
                }
            }
            if (book.getBookAuthor() == "") {
                try {
                    book.setBookAuthor(data.getString("author"));
                } catch (Exception e) {
                    log.info("暂无相关信息");
                }
            }
            if (book.getPublishHouse() == "") {
                try {
                    book.setPublishHouse(data.getString("press"));
                } catch (Exception e) {
                    log.info("暂无相关信息");
                }
            }
            if (book.getPublicationDate() == "") {
                try {
                    book.setPublicationDate(data.getString("pressDate"));
                } catch (Exception e) {
                    log.info("暂无相关信息");
                }
            }
            if (book.getBookPhoto() == "") {
                try {
                    book.setBookPhoto(data.getString("pictures").replace("[\"","").replace("\"]",""));
                } catch (Exception e) {
                    log.info("暂无相关信息");
                }
            }
            if (book.getBookClassification() == "") {
                try {
                    book.setBookClassification(data.getString("clcName"));
                } catch (Exception e) {
                    log.info("暂无相关信息");
                }
            }
            if (book.getBookDetail() == "") {
                try {
                    book.setBookDetail(data.getString("bookDesc"));
                } catch (Exception e) {
                    log.info("暂无相关信息");
                }
            }
            try {
                bookMapper.updateBook_2(book);
            } catch (Exception e) {
                log.info("Error updating" + book.getIsbn());
                continue;
            }
            Thread.currentThread().sleep(200);
        }
        log.info("缺少信息查找补充完成");
    }
}
