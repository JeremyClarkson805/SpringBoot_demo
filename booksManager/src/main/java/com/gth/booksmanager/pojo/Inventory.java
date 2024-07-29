package com.gth.booksmanager.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {
    private Integer num;
    private String bookUUID;
    private String bookId;
    private String bookStatus;
}
