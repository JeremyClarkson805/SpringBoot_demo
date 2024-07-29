package com.gth.booksmanager.service;

import com.gth.booksmanager.pojo.Book;
import com.gth.booksmanager.pojo.PageBean;
import com.gth.booksmanager.pojo.Result;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.multipart.MultipartFile;

public interface BookService{
    //根据ID查询书
    void getBookById(Integer id);

    //分页查询书
    PageBean page(Integer page,Integer pageSize,String bookName,String bookId, String bookClassification,String isbn,String selectType);

    String addBook_1(Book book);

    String addBookByISBN(String isbn);

    Result lendBook(String UUID, String readerId, HttpServletRequest request);

    Result returnBook(String UUID, String readerId, HttpServletRequest request);

    Result addBookOnlyByISBN(String isbn, HttpServletRequest request);

    Result addBookByExcel(MultipartFile file, HttpServletRequest request);
}
