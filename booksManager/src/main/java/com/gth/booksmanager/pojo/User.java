package com.gth.booksmanager.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.ResultMap;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String userName;

    private String idno;
}
