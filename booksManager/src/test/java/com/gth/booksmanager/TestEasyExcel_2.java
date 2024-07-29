package com.gth.booksmanager;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.read.listener.PageReadListener;
import com.gth.booksmanager.mapper.BookMapper;
import com.gth.booksmanager.pojo.Book;
import com.gth.booksmanager.pojo.ExcelData;
import com.gth.booksmanager.pojo.Result;
import com.gth.booksmanager.service.BookService;
import com.gth.booksmanager.service.impl.BookServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@SpringBootTest
public class TestEasyExcel_2 {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private BookService bookService;

    @Test
    public void test_1() {
//        String fileName = System.getProperty("user.home") + File.separator + "testUserInfo.xlsx";
        String fileName = "/Users/gth/Documents/springboot_demo/booksManager/src/test/java/com/gth/booksmanager/testUserInfo.xlsx";
        File file = new File(fileName);

        System.out.println("当前工作目录: " + System.getProperty("user.dir"));
        System.out.println("文件将被创建在: " + file.getAbsolutePath());

        List<UserInfo> userList = new ArrayList<>();
        userList.add(new UserInfo("张三", 25, "zhangsan@example.com"));
        userList.add(new UserInfo("李四", 30, "lisi@example.com"));

        try {
            EasyExcel.write(fileName, UserInfo.class).sheet("用户信息").doWrite(userList);
            System.out.println("Excel文件写入成功: " + fileName);

            if (file.exists()) {
                System.out.println("文件创建成功，大小：" + file.length() + " 字节");
            } else {
                System.out.println("警告：文件未成功创建！");
            }
        } catch (Exception e) {
            System.out.println("写入Excel时发生错误：" + e.getMessage());
            e.printStackTrace();
            return;
        }

        // 读取Excel
        EasyExcel.read(fileName, UserInfo.class, new PageReadListener<UserInfo>(dataList -> {
            for (UserInfo userInfo : dataList) {
                System.out.println("姓名: " + userInfo.getName() + ", 年龄: " + userInfo.getAge() + ", 邮箱: " + userInfo.getEmail());
            }
        })).sheet().doRead();
    }

    @Test
    void test_2() {
        String fileName = "/Users/gth/Documents/springboot_demo/booksManager/src/test/java/com/gth/booksmanager/testUserInfo.xlsx";
        // 读取Excel
        EasyExcel.read(fileName, UserInfo.class, new PageReadListener<UserInfo>(dataList -> {
            for (UserInfo userInfo : dataList) {
                System.out.println("姓名: " + userInfo.getName() + ", 年龄: " + userInfo.getAge() + ", 邮箱: " + userInfo.getEmail());
            }
        })).sheet().doRead();
    }

    @Test
    void test_3() {
        String fileName = "/Users/gth/Documents/springboot_demo/booksManager/src/main/resources/static/testExcel.xlsx";
        // 读取Excel
        EasyExcel.read(fileName, ExcelData.class, new PageReadListener<ExcelData>(dataList -> {
            for (ExcelData excelData : dataList) {
                System.out.println(excelData.toString());
            }
        })).sheet().doRead();
    }

    @Test
    void test_4() {
        String fileName = "/Users/gth/Documents/springboot_demo/booksManager/src/main/resources/static/testExcel.xlsx";
        BookService bs = new BookServiceImpl();

        // 读取Excel
        EasyExcel.read(fileName, ExcelData.class, new PageReadListener<ExcelData>(dataList -> {
            for (ExcelData excelData : dataList) {
                try {
                    Book b = new Book();
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
//                    log.error(b.toString());
                    bookMapper.insertBook_1(b);
                    log.info("插入" + b.getBookName() + "成功");
                } catch (Exception e) {
                    System.out.println("插入" + excelData.getBookName() + "错误");
                }
            }
        })).sheet().doRead();
    }

    @Test
    void test_5() {
        Book b = new Book();
        b.setBookId("1015");
        b.setBookName("白夜行");
        b.setBookAuthor("白夜行");
        b.setPublishHouse("南海出版公司");
        b.setPublicationDate("南海出版公司");
        b.setBookPhoto("https://img1.doubanio.com/view/subject/s/public/s24514468.jpg");
        b.setIsbn("9787544258609");
        b.setBookClassification("悬疑");
        b.setBookDetail("东野圭吾万千书迷心中的无冕之王周刊文春推理小说年度BEST10第1名本格推理小说年度BEST10第2名,《白夜行》是东野圭吾迄今口碑最好的长篇杰作，具备经典名著的一切要素：一宗离奇命案牵出跨度近20年步步惊心的故事：悲凉的爱情、吊诡的命运、令人发指的犯罪、复杂人性的对决与救赎……");
        bookMapper.insertBook_1(b);
    }

    @Test
    void test_6() {
        String fileName = "/Users/gth/Documents/springboot_demo/booksManager/src/main/resources/static/testExcel.xlsx";
        BookService bs = new BookServiceImpl();

        // 读取Excel
        EasyExcel.read(fileName, ExcelData.class, new PageReadListener<ExcelData>(dataList -> {
            for (ExcelData excelData : dataList) {
                try {
                    Book b = new Book();
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
                    bookService.addBook_1(b);
                    log.info("插入" + b.getBookName() + "成功");
                } catch (Exception e) {
                    System.out.println("插入" + excelData.getBookName() + "错误");
                }
            }
        })).sheet().doRead();
    }
}