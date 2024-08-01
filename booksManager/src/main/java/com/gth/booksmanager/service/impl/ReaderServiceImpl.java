package com.gth.booksmanager.service.impl;

import com.gth.booksmanager.mapper.ReaderMapper;
import com.gth.booksmanager.pojo.Book;
import com.gth.booksmanager.pojo.Reader;
import com.gth.booksmanager.pojo.Result;
import com.gth.booksmanager.service.ReaderService;
import com.gth.booksmanager.utils.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
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

    @Override
    public boolean IsManager(HttpServletRequest request) {
        String openId = JwtUtils.parseJWT(request.getHeader("token")).get("openId").toString();
        Reader manager = readerMapper.selectByOpenId(openId);
        if (manager.getReaderType() == 0) {//检查是否具有管理员权限
            return true;
        }
        else return false;
    }

}
