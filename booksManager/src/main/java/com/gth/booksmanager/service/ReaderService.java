package com.gth.booksmanager.service;

import com.gth.booksmanager.pojo.Reader;
import jakarta.servlet.http.HttpServletRequest;

public interface ReaderService {
    Reader selectByOpenId(String id);
    boolean IsManager(HttpServletRequest request);
}
