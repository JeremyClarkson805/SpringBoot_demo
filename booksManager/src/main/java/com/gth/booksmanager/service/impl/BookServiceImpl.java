package com.gth.booksmanager.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gth.booksmanager.mapper.BookMapper;
import com.gth.booksmanager.pojo.Book;
import com.gth.booksmanager.pojo.PageBean;
import com.gth.booksmanager.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;

    public Book selectById(String id) { return bookMapper.selectById(id); }

    @Override
    public void getBookById(Integer id) {
        return;
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, String bookName, String bookClassification, String isbn, String selectType){
        PageHelper.startPage(page,pageSize);
        List<Book> bookList = bookMapper.list(bookName, bookClassification,isbn,selectType);
        Page<Book> p = (Page<Book>) bookList;
        return new PageBean(p.getTotal(),p.getResult());
    }

}
