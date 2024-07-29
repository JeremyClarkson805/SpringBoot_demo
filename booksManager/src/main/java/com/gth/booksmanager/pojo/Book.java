package com.gth.booksmanager.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private String bookId;
    private String bookName;
    private String bookAuthor;
    private String publishHouse;
    private String registerDate;
    private String publicationDate;
    private String bookPhoto;
    private String isbn;
    private String bookClassification;
    private int collectNum;
    private float bookStar;
    private int bookLookedNum;
    private String bookNum;
    private String bookDetail;

    public Book(String bookName, String bookAuthor, String publishHouse, String publicationDate, String bookPhoto, String isbn, String bookClassification, String bookDetail) {
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.publishHouse = publishHouse;
        this.publicationDate = publicationDate;
        this.bookPhoto = bookPhoto;
        this.isbn = isbn;
        this.bookClassification = bookClassification;
        this.bookDetail = bookDetail;
    }
}

