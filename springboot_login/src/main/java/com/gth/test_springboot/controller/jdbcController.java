package com.gth.test_springboot.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/sql")
public class jdbcController {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostMapping("/register")
    public String register(@RequestBody JSONObject req){
        String username = req.getString("name");
        String passwd = req.getString("passwd");
        try {
            String sql = "insert into user_test (name,passwd) value (?,?)";
            jdbcTemplate.update(sql,username,passwd);
            return "Success";
        }catch (Exception e){
            return "Failed";
        }
    }

    @PostMapping("/login")
    public String login(@RequestBody JSONObject req){
        String username = req.getString("name");
        String passwd = req.getString("passwd");

        try {
            String sql = "select * from user_test where name = \"" + username + "\" ";
            List<Map<String,Object>> maps = jdbcTemplate.queryForList(sql);
            if (maps.isEmpty()) return "no user";
            if (maps.size() == 1){
                if (maps.get(0).get("passwd").equals(passwd)){
                    return "Login success";
                }else return "Wrong Password!";
            }
            return "Unknown Error!";
        } catch (DataAccessException e) {
            System.out.println("login have something went wrong!");
            return "Unknown Error!";
        }

    }
}
