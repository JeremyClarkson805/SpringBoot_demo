package com.gth.booksmanager.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reader {
    private int readerId;
    private String openId;
    private String readerName;
    private String idNumber;
    private String telephone;
    private String readerDepartment;
    private int readerType;
    private String extract;
}
