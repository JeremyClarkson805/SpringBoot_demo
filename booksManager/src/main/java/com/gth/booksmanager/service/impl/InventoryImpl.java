package com.gth.booksmanager.service.impl;

import com.gth.booksmanager.mapper.BookMapper;
import com.gth.booksmanager.mapper.ReaderMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InventoryImpl {
    @Autowired
    private BookMapper bookMapper;
    @Autowired
    private ReaderMapper readerMapper;

}
