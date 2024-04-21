package com.gth.booksmanager.service;

import com.gth.booksmanager.pojo.Book;
import com.gth.booksmanager.pojo.PageBean;

public interface BookService{
    //根据ID查询书
    void getBookById(Integer id);

    //分页查询书
    PageBean page(Integer page,Integer pageSize,String bookName,String bookClassification,String isbn,String selectType);
}
