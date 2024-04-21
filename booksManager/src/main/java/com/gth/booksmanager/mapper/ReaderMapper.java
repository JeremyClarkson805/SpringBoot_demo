package com.gth.booksmanager.mapper;

import com.gth.booksmanager.pojo.Reader;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReaderMapper{

    @Select("select * from reader where openid=#{id}")
    Reader selectByOpenId(String id);
}
