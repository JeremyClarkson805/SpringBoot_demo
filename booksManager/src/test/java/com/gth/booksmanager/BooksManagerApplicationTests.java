package com.gth.booksmanager;

import com.github.pagehelper.Page;
import com.gth.booksmanager.mapper.BookMapper;
import com.gth.booksmanager.mapper.ReaderMapper;
import com.gth.booksmanager.pojo.Book;
import com.gth.booksmanager.pojo.PageBean;
import com.gth.booksmanager.pojo.Reader;
import com.gth.booksmanager.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


@Slf4j
@SpringBootTest
class BooksManagerApplicationTests {

    @Autowired
    private ReaderMapper readerMapper;
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookService bookService;

    @Test
    void testSelectBookById(){
        String id = "1001";
        Book b = bookMapper.selectById(id);
        log.info(b.toString());
    }

    @Test
    void testSelectByOpenId(){
        String id = "oyPDG63DGsKkGv0vRmbWJFWhNeE4";
        Reader reader = readerMapper.selectByOpenId(id);
        try {
            log.info(reader.toString());
        } catch (Exception e){
            log.info("该OpenId不存在");
        }
    }

    @Test
    void testList(){
        PageBean pageBean = bookService.page(1,10,null,"1003",null,null,"register_date");
        log.info(pageBean.toString());
    }
    @Test
    public void testSpider() throws IOException, IOException {
        URL url = new URL("https://search.douban.com/book/subject_search?search_text=");
        String bookName = "置身事内：中国政府与经济发展";
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36");
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK){
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            System.out.println(response.toString());
        } else {
            System.out.println("请求失败，状态码："+responseCode);
        }
        connection.disconnect();
    }

    @Test
    public void test(){
        bookService.addBookByISBN("9787559864253");
    }

    @Test
    public void test_2(){
        Book b = new Book();
        b.setBookName("明代国家权力结构及运行机制");
        b.setBookStar(4);
        bookMapper.updateBook_1(b);
    }

    @Test
    public void test_3(){
        Book b = new Book("明代国家权力结构及运行机制","方志远","广西师范大学出版社","2024-03-01","https://img1.doubanio.com/view/subject/s/public/s34791168.jpg","9787559864253","历史","本书是一部明代政治制度史的经典著作，立足于中国古代国家制度的基本特征及形成道路，以明代国家权力的内部结构和运行法则为切入点，勾勒出明代从中央到地方，从皇帝到乡绅里甲层层制衡的庞大国家机器。通过明代权力结构初创、定型、调整、再定型的全过程，展现明代国家权力结构演变的双轨制轨迹，展示中国历代国家权力“内廷机构外廷化、中央机构地方化、监察机构行政化”的普遍规律，揭示中国古代社会制度尤其是君主制度的本质特征。");
        bookService.addBook_1(b);
    }
}
