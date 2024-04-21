package com.gth.booksmanager.controller;

import com.gth.booksmanager.mapper.BookMapper;
import com.gth.booksmanager.pojo.Book;
import com.gth.booksmanager.pojo.PageBean;
import com.gth.booksmanager.pojo.Result;
import com.gth.booksmanager.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/book")
public class BookController {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookService bookService;

    @GetMapping("/{id}")
    public Result getById(@PathVariable String id){
        log.info("根据书本ID查询书籍,ID={}", id);
        Book b = bookMapper.selectById(id);
        return Result.success(b.toString());
    }

    @GetMapping
    public Result page(@RequestParam(defaultValue = "1") Integer page,//通过@RequestParam的defaultValue来设置参数的默认值
                       @RequestParam(defaultValue = "10") Integer pageSize,
                       String bookName, String bookClassification, String isbn ,String selectType) {
        log.info("分页查询，参数：{},{},{},{},{},{}", page, pageSize, bookName, bookClassification, isbn, selectType);
        PageBean pageBean = bookService.page(page,pageSize,bookName,bookClassification,isbn,selectType);//selectType表示按照什么排，传数据库字段，按倒序排，传book_looked_num表按看过的人数排倒序，传register_date表按书本上架的日期排倒序
        return Result.success(pageBean);
    }
}
