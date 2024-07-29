package com.gth.booksmanager;

import com.gth.booksmanager.mapper.BookMapper;
import com.gth.booksmanager.mapper.ReaderMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestLendBook {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private ReaderMapper readerMapper;

    @Test
    public void test_1() {
        String ISBN = "1001";
        String readerId = "56";

    }
}
