package com.gth.booksmanager.mapper;


import com.gth.booksmanager.pojo.Book;
import com.gth.booksmanager.pojo.LendingBook;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface BookMapper {

    @Select("select * from book where book_id = #{id}")
    Book selectById(String id);

    public List<Book> list(String bookName,String bookId,String bookClassification,String isbn,String selectType);

    @Select("select * from book where book_id = #{bookId}")
    Book bookDetail(String bookId);

    //根据isbn查询书
    @Select("select * from book where isbn = #{isbn}")
    Book getBookByISBN(String isbn);

    //根据实体类来插入书，首次插入
    void insertBook_1(Book book);

    //根据书本名称来修改书籍信息
    void updateBook_1(Book book);

    //根据书本ISBN来修改书籍信息
    void updateBook_2(Book book);

    //根据bookId来添加相同的书籍
    void insertBook_2(String bookId);

    //往借出的表中插入记录
    void insertLendBook(String UUID,String bookId, String readerId);

    //更新看过的人数
    @Update("update book set book_looked_num=book_looked_num+1 where book_id = #{bookId}")
    void updateLookedNum(String bookId);

    //将库存-1
    @Update("update book set book_num=book_num-1 where book_id = #{bookId}")
    void minusBookNum(String bookId);

    //将库存+1
    @Update("update book set book_num=book_num+1 where book_id = #{bookId}")
    void plusBookNum(String bookId);

    @Update("update borrow set return_date = now() where book_uuid=#{UUID} and reader_id=#{readerId}")
    void updateLendBook(String UUID,String readerId);

    //更新还的书的时间
    void returnLendBook(String UUID);

    //返回书籍名称，作者，借阅人名字，借阅日期
    List<LendingBook> getLendingBook();

    @Delete("delete from book where book_id = #{bookId}")
    void deleteBook(String bookId);

    @Select("select distinct book_classification from book;")
    public List<String> getBookType();

    @Select("select isbn from book where book_photo is null")
    public List<String> getNoPhotoBook();

    public List<Book> getMissingBook();
}
