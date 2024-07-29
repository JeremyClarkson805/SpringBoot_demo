package com.gth.booksmanager.mapper;

import com.gth.booksmanager.pojo.Inventory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface InventoryMapper {

    //根据bookId返回一本指定借出状态的书
    @Select("select * from inventory where book_id = #{bookId} and inventory.book_status = #{code} limit 1;")  // code=1->已借出 code=0->未借出，
    Inventory getUUIDByBookId(String bookId,String code);

    //更新借出状态
    @Update("update inventory set book_status = #{code} where book_uuid = #{UUID}")
    void updateStatus(String UUID,String code);

    //根据UUID返回一本书的bookId
    @Select("select book_id from inventory where book_uuid = #{UUID}")
    String getBookIdByUUID(String UUID);

    @Select("select book_status from inventory where book_uuid = #{UUID}")
    String getBookStatus(String UUID);
}
