package com.gth.booksmanager.service;

import com.gth.booksmanager.pojo.Reader;

public interface ReaderService {
    Reader selectByOpenId(String id);
}
