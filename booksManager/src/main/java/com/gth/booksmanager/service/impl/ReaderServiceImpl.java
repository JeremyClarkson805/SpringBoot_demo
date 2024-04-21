package com.gth.booksmanager.service.impl;

import com.gth.booksmanager.mapper.ReaderMapper;
import com.gth.booksmanager.pojo.Reader;
import com.gth.booksmanager.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReaderServiceImpl implements ReaderService {
    @Autowired
    private ReaderMapper readerMapper;

    @Override
    public Reader selectByOpenId(String id) {
        Reader reader = new Reader();
        try {
            return readerMapper.selectByOpenId(id);
        }catch (Exception e) {
            return null;
        }
    }
}
