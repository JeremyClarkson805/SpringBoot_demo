package com.gth.booksmanager.mapper;

import com.gth.booksmanager.pojo.Reader;
import com.gth.booksmanager.pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface ReaderMapper{

    @Select("select * from reader where openid=#{id}")
    Reader selectByOpenId(String id);

    public void insertReader(Reader r);

    public User selectUserByNameAndId(String readerName, String idno);

    @Select("select * from reader where reader_id=#{id}")
    Reader selectByRId(String id);

    @Select("select reader_id from reader where openid=#{id}")
    String getReaderIdByOpenId(String id);
}
