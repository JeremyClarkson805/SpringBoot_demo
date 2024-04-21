package com.gth.booksmanager.mapper;


import com.gth.booksmanager.pojo.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface BookMapper {

    @Select("select * from book where book_id = #{id}")
    Book selectById(String id);

    public List<Book> list(String bookName,String bookClassification,String isbn,String selectType);

}
