package com.gth.booksmanager.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LendingBook {
    private String bookName;
    private String bookAuthor;
    private String bookUUID;
    private String bookPhoto;
    private String readerName;
    private String borrowDate;
}
