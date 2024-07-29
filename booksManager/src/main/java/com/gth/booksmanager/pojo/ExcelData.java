package com.gth.booksmanager.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ExcelData {
    private String bookId;
    private String bookName;
    private String bookAuthor;
    private String publishHouse;
    private String publicationDate;
    private String bookPhoto;
    private String isbn;
    private String bookClassification;
    private String bookDetail;
}
