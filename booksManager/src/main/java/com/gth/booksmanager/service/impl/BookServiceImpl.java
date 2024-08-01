package com.gth.booksmanager.service.impl;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.gth.booksmanager.common.IsbnService;
import com.gth.booksmanager.mapper.BookMapper;
import com.gth.booksmanager.mapper.InventoryMapper;
import com.gth.booksmanager.mapper.ReaderMapper;
import com.gth.booksmanager.pojo.*;
import com.gth.booksmanager.service.BookService;
import com.gth.booksmanager.service.ReaderService;
import com.gth.booksmanager.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class BookServiceImpl implements BookService {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private ReaderMapper readerMapper;
    @Autowired
    private InventoryMapper inventoryMapper;
    @Autowired
    private IsbnService isbnService;
    @Autowired
    private ReaderService readerService;

    public Book selectById(String id) { return bookMapper.selectById(id); }

    @Override
    public void getBookById(Integer id) {
        return;
    }

    @Override
    public PageBean page(Integer page, Integer pageSize, String bookName, String bookId, String bookClassification, String isbn, String selectType){
        PageHelper.startPage(page,pageSize);
        List<Book> bookList = bookMapper.list(bookName, bookId, bookClassification,isbn,selectType);
        Page<Book> p = (Page<Book>) bookList;
        return new PageBean(p.getTotal(),p.getResult());
    }

    @Override
    public String addBook_1(Book book) {//首次添加某本书
        try {
            bookMapper.insertBook_1(book);
            Book b = bookMapper.getBookByISBN(book.getIsbn());
            bookMapper.insertBook_2(b.getBookId());
            return "success";
        } catch (Exception e) {
            log.info("Error inserting:"+e.getMessage());
            return e.getMessage();
        }
    }

    @Override
    public String addBookByISBN(String isbn) {//添加已经添加过的（重复的书）
        Book book = bookMapper.getBookByISBN(isbn);//返回带ISBN和书名的book实体类
        if (book != null){
            bookMapper.insertBook_2(book.getBookId());//往inventory库中插入书
            book.setBookNum(String.valueOf(Integer.parseInt(book.getBookNum()) + 1));//book书本数+1
            bookMapper.updateBook_2(book);
            return "success";
        } else return "此ISBN未曾被录入";
    }

    @Override
    public Result lendBook(String UUID, String readerId, HttpServletRequest request) {
        String openId = JwtUtils.parseJWT(request.getHeader("token")).get("openId").toString();
        readerId = readerMapper.getReaderIdByOpenId(openId);
        log.info("解析后的readerId:" + readerId);
        Reader manager = readerMapper.selectByOpenId(openId);
        Reader reader = readerMapper.selectByRId(readerId);
        String bookId = inventoryMapper.getBookIdByUUID(UUID);
        Book book = bookMapper.bookDetail(bookId);
        if (manager.getReaderType() != 0) {//检查是否具有管理员权限
            return Result.error("Illegal manager");
        }
        if (reader == null) {//检查读者是否合法
            return Result.error("Illegal user");
        }
        log.info(inventoryMapper.getBookStatus(UUID));
        if ("1".equals(inventoryMapper.getBookStatus(UUID))) {
            return Result.error("Book has been lend out");
        }
        //更新要借出的书的状态
        inventoryMapper.updateStatus(UUID,"1");
        bookMapper.minusBookNum(bookId);

        //将记录写入借出表
        log.error("UUID:" + UUID);
        bookMapper.insertLendBook(UUID,bookId,readerId);
        bookMapper.updateLookedNum(bookId);//更新看过的人数
        return Result.success();
    }

    public Result returnBook(String UUID, String readerId, HttpServletRequest request) {
        String openId = JwtUtils.parseJWT(request.getHeader("token")).get("openId").toString();
        readerId = readerMapper.getReaderIdByOpenId(openId);
        log.info("解析后的readerId:" + readerId);
        Reader manager = readerMapper.selectByOpenId(openId);
        Reader reader = readerMapper.selectByRId(readerId);
        String bookId = inventoryMapper.getBookIdByUUID(UUID);
        Book book = bookMapper.bookDetail(bookId);
        if (manager.getReaderType() != 0) {//检查是否具有管理员权限
            return Result.error("Illegal manager");
        }
        if (reader == null) {//检查读者是否合法
            return Result.error("Illegal user");
        }
//        log.info(inventoryMapper.getBookStatus(UUID));
        if ("0".equals(inventoryMapper.getBookStatus(UUID))) { //防止书已经还回去 又还一次
            return Result.error("something went wrong");
        }
        //更新要借出的书的状态
        inventoryMapper.updateStatus(UUID,"0");
        bookMapper.plusBookNum(bookId);

        //将记录写入借出表
        bookMapper.returnLendBook(UUID);

        return Result.success();
    }

    public Result addBookOnlyByISBN(String isbn, HttpServletRequest request) {
        IsbnService isbnService = new IsbnService();
        JSONObject data = null;
        if (!readerService.IsManager(request)) {//检查是否具有管理员权限
            return Result.error("Illegal manager");
        }
        Book book = bookMapper.getBookByISBN(isbn);
        if (book != null) {//已经添加过同样的书籍
            bookMapper.insertBook_2(book.getBookId());//往库存库中新增
            bookMapper.plusBookNum(book.getBookId());
            return Result.success();
        }
        try {
            data = isbnService.getBookInfo(isbn);
        } catch (Exception e) {
            return Result.error("parse error");
        }
        book = new Book();
        book.setIsbn(isbn);
        book.setBookName(data.getString("bookName"));
        book.setBookAuthor(data.getString("author"));
        book.setPublishHouse(data.getString("press"));
        book.setPublicationDate(data.getString("pressDate"));
        book.setBookPhoto(data.getString("pictures").replace("[\"","").replace("\"]",""));
        book.setBookClassification(data.getString("clcName"));
        book.setBookDetail(data.getString("bookDesc"));
        log.info(book.toString());
        this.addBook_1(book);
        return Result.success();
    }

    @Override
    public Result addBookByExcel(MultipartFile file, HttpServletRequest request) {
        if (!readerService.IsManager(request)) {//检查是否具有管理员权限
            return Result.error("Illegal manager");
        }
        String originalFileName = file.getOriginalFilename();//获取文件原始名称
        int index = originalFileName.lastIndexOf(".");
        String extName = originalFileName.substring(index);
        String newFileName = UUID.randomUUID().toString() + extName;//构造唯一文件名
        newFileName = "/Users/gth/Documents/springboot_demo/booksManager/src/main/resources/file/"+newFileName;
        try {
            file.transferTo(new File(newFileName));
        } catch (IOException e) {
            return Result.error("Upload Error");
        }
        log.info("Uploading,FileName:{},Path:{}", originalFileName, newFileName);

        // 读取Excel
        EasyExcel.read(newFileName, ExcelData.class, new PageReadListener<ExcelData>(dataList -> {
            for (ExcelData excelData : dataList) {
                try {
                    Book book = bookMapper.getBookByISBN(excelData.getIsbn());
                    if (book != null) {//已经添加过同样的书籍
                        bookMapper.insertBook_2(book.getBookId());//往库存库中新增
                        bookMapper.plusBookNum(book.getBookId());
                        continue;
                    }
                    Book b = new Book();
                    String uuid = excelData.getUUID();
                    log.info("uuid:" + uuid);
//                    System.out.println(excelData.toString());
                    b.setBookId(excelData.getBookId());
                    b.setBookName(excelData.getBookName());
                    b.setBookAuthor(excelData.getBookAuthor());
                    b.setPublishHouse(excelData.getPublishHouse());
                    b.setPublicationDate(excelData.getPublicationDate());
                    b.setBookPhoto(excelData.getBookPhoto());
                    b.setIsbn(excelData.getIsbn());
                    b.setBookClassification(excelData.getBookClassification());
                    b.setBookDetail(excelData.getBookDetail());
                    if (uuid == null) {
                        this.addBook_1(b);
                        log.info("插入" + b.getBookName() + "成功");
                    } if (uuid != null) {
                        bookMapper.insertBook_1(b);
                        inventoryMapper.insertWithUUID(uuid, bookMapper.getBookByISBN(excelData.getIsbn()).getBookId());
                    }

                } catch (Exception e) {
                    System.out.println("插入" + excelData.getBookName() + "错误");
                }
            }
        })).sheet().doRead();
        return Result.success();
    }

    public PageBean getLendOutBook(Integer page,Integer pageSize){
        PageHelper.startPage(page,pageSize);
        List<LendingBook> lendingList = bookMapper.getLendingBook();
        Page<LendingBook> p = (Page<LendingBook>) lendingList;
        return new PageBean(p.getTotal(),p.getResult());

    }

    public Result updateBook(Book book, HttpServletRequest request) {
        if (!readerService.IsManager(request)) {//检查是否具有管理员权限
            return Result.error("Illegal manager");
        }
        bookMapper.updateBook_2(book);
        return Result.success();
    }

    public Result addBookByUUIDAndIsbn(String uuid, String isbn, HttpServletRequest request) {
        IsbnService isbnService = new IsbnService();
        JSONObject data = null;
        if (!readerService.IsManager(request)) {//检查是否具有管理员权限
            return Result.error("Illegal manager");
        }
        Book book = bookMapper.getBookByISBN(isbn);
        if (book != null) {//已经添加过同样的书籍
            bookMapper.insertBook_2(book.getBookId());//往库存库中新增
            bookMapper.plusBookNum(book.getBookId());
            return Result.success();
        }
        try {
            data = isbnService.getBookInfo(isbn);
            if (data == null) {
                return Result.error("parse error");
            }
        } catch (Exception e) {
            return Result.error("parse error");
        }
        book = new Book();
        book.setIsbn(isbn);
        book.setBookName(data.getString("bookName"));
        book.setBookAuthor(data.getString("author"));
        book.setPublishHouse(data.getString("press"));
        book.setPublicationDate(data.getString("pressDate"));
        book.setBookPhoto(data.getString("pictures").replace("[\"","").replace("\"]",""));
        book.setBookClassification(data.getString("clcName"));
        book.setBookDetail(data.getString("bookDesc"));
        log.info(book.toString());
        try {
            bookMapper.insertBook_1(book);
            inventoryMapper.insertWithUUID(uuid, bookMapper.getBookByISBN(isbn).getBookId());
            return Result.success();
        } catch (Exception e) {
            return Result.error(e.getMessage());
        }
    }

    @Override
    public Result fillBookInfo(HttpServletRequest request) {
        if (!readerService.IsManager(request)) {//检查是否具有管理员权限
            return Result.error("Illegal manager");
        }
        IsbnService isbnService = new IsbnService();
        log.info("正在查找并填充相关信息");
        List<Book> bookList= bookMapper.getMissingBook();
        for (Book book : bookList) {
            JSONObject data = isbnService.getBookInfo(book.getIsbn());
            if (data == null) {
                return Result.error("接口额度达当日极限，请稍后再试");
            } if (book.getBookName() == "") {
                try {
                    book.setBookName(data.getString("bookName"));
                } catch (Exception e) {
                    log.info("暂无相关信息");
                }
            } if (book.getBookAuthor() == "") {
                try {
                    book.setBookAuthor(data.getString("author"));
                } catch (Exception e) {
                    log.info("暂无相关信息");
                }
            } if (book.getPublishHouse() == "") {
                try {
                    book.setPublishHouse(data.getString("press"));
                } catch (Exception e) {
                    log.info("暂无相关信息");
                }
            } if (book.getPublicationDate() == "") {
                try {
                    book.setPublicationDate(data.getString("pressDate"));
                } catch (Exception e) {
                    log.info("暂无相关信息");
                }
            } if (book.getBookPhoto() == "") {
                try {
                    book.setBookPhoto(data.getString("pictures").replace("[\"","").replace("\"]",""));
                } catch (Exception e) {
                    log.info("暂无相关信息");
                }
            } if (book.getBookClassification() == "") {
                try {
                    book.setBookClassification(data.getString("clcName"));
                } catch (Exception e) {
                    log.info("暂无相关信息");
                }
            } if (book.getBookDetail() == "") {
                try {
                    book.setBookDetail(data.getString("bookDesc"));
                } catch (Exception e) {
                    log.info("暂无相关信息");
                }
            }
            try {
                bookMapper.updateBook_2(book);
            } catch (Exception e) {
                log.info("Error updating" + book.getIsbn());
                continue;
            }
            try {
                Thread.currentThread().sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return Result.success();
    }

}
