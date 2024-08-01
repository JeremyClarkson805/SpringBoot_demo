package com.gth.booksmanager.service;

import com.gth.booksmanager.pojo.Book;
import com.gth.booksmanager.pojo.Reader;
import com.gth.booksmanager.pojo.Result;
import jakarta.servlet.http.HttpServletRequest;

public interface ReaderService {
    Reader selectByOpenId(String id);
    boolean IsManager(HttpServletRequest request);
}
