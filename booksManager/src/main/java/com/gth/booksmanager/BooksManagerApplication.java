package com.gth.booksmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

//@ServletComponentScan

@SpringBootApplication
public class BooksManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BooksManagerApplication.class, args);
    }

}
