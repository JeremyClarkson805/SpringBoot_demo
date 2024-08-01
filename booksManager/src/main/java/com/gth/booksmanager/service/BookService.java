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

    //通过Excel表批量导入书
    Result addBookByExcel(MultipartFile file, HttpServletRequest request);

    //分页查询借出的书
    PageBean getLendOutBook(Integer page,Integer pageSize);

    //更新书籍信息
    Result updateBook(Book book, HttpServletRequest request);

    //通过ISBN导入书并指定唯一编号
    public Result addBookByUUIDAndIsbn(String uuid, String isbn, HttpServletRequest request);

    //自动填充缺失的书本信息
    public Result fillBookInfo(HttpServletRequest request);
}
