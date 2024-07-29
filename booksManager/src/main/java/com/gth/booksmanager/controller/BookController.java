package com.gth.booksmanager.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gth.booksmanager.mapper.BookMapper;
import com.gth.booksmanager.mapper.ReaderMapper;
import com.gth.booksmanager.pojo.*;
import com.gth.booksmanager.service.BookService;
import com.gth.booksmanager.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookService bookService;
    @Autowired
    private ReaderMapper readerMapper;

//    @GetMapping("/{id}")
//    public Result getById(@PathVariable String id){
//        log.info("根据书本ID查询书籍,ID={}", id);
//        Book b = bookMapper.selectById(id);
//        return Result.success(b.toString());
//    }

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,//通过@RequestParam的defaultValue来设置参数的默认值
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String bookName, String bookId,String bookClassification, String isbn ,String selectType) {
        log.info("分页查询，参数：{},{},{},{},{},{}", page, pageSize, bookName, bookClassification, isbn, selectType);
        PageBean pageBean = bookService.page(page,pageSize,bookName,bookId,bookClassification,isbn,selectType);//selectType表示按照什么排，传数据库字段，按倒序排，传book_looked_num表按看过的人数排倒序，传register_date表按书本上架的日期排倒序
        return Result.success(pageBean);
    }

    @GetMapping("/bookDetail")
    public Result bookDetail(@RequestParam(value = "bookId")String bookId){
        log.info("查询书籍详情：bookId:"+bookId);
        Book b = bookMapper.bookDetail(bookId);
        return Result.success(JSON.toJSON(b));
    }

    @GetMapping("/add_1")
    public Result addBook_1(String bookName,String bookAuthor,String publishHouse,String publicationDate,String isbn,String bookClassification,String bookPhoto,String bookDetail){
        log.info("调用addBook_1方法"+isbn);
        Book book = new Book(bookName,bookAuthor,publishHouse,publicationDate,bookPhoto,isbn,bookClassification,bookDetail);
        log.info("book.getBookName()"+book.getBookName());
        if ((book.getBookName() == null && book.getBookAuthor() == null) || (book.getBookName() == "" && book.getBookAuthor() == "")){//没输入书名和作者，添加录入过的书籍
            log.info("添加同名书：书本isbn:{}",isbn);
            bookService.addBookByISBN(isbn);
            return Result.success();
        }
        if ((book.getBookName() != null && book.getBookName() !="") || (book.getIsbn() != null && book.getIsbn() != "")){
            log.info("首次添加书本：书名:{}, 作者:{}, 出版社:{}, 出版日期:{}, isbn:{}, 书本种类:{}, 书本图片:{}, 书本详情:{}",
                    bookName, bookAuthor, publishHouse, publicationDate, isbn, bookClassification, bookPhoto, bookDetail);
            String result = bookService.addBook_1(book);
            if (result.equals("success")) {
                return Result.success();
            } else {
                return Result.error(result);
            }
        }
        return Result.error("未知错误");
    }

    @PostMapping("/upload")
    public Result upload(String bookName, MultipartFile image) throws Exception {
        String originalFileName = image.getOriginalFilename();//获取文件原始名称
        int index = originalFileName.lastIndexOf(".");
        String extName = originalFileName.substring(index);
        String newFileName = UUID.randomUUID().toString() + extName;//构造唯一文件名
        newFileName = "/Users/gth/Documents/springboot_demo/booksManager/src/main/resources/image/"+newFileName;
        image.transferTo(new File(newFileName));
        log.info("Uploading,FileName:{},Path:{}", bookName, newFileName);
        return Result.success();
    }

    @PostMapping("/lend")
    public Result lendBook(String UUID, String readerId, HttpServletRequest request) {
        log.info("借出书操作");
        log.info("UUID:{}", UUID);
        return bookService.lendBook(UUID, readerId, request);
    }

    @PostMapping("/return")
    public Result returnBook(String UUID, String readerId, HttpServletRequest request) {
        return bookService.returnBook(UUID, readerId, request);
    }

    @PutMapping()
    public Result addBookByISBN(String ISBN,HttpServletRequest request) {
        return bookService.addBookOnlyByISBN(ISBN, request);
    }

    @PostMapping("/addByExcel")
    public Result addBookByExcel(MultipartFile file, HttpServletRequest request){

        return bookService.addBookByExcel(file, request);

    }
}
