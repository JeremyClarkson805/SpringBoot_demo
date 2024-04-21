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

import java.util.List;

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
        PageBean pageBean = bookService.page(1,10,null,null,null,"register_date");
        log.info(pageBean.toString());
    }
}
