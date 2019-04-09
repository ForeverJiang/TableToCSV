package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.toCSV.CSVUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Creator: jiangyongheng
 * Date: 2019/04/08
 * Time: 11:08
 */
@RestController
@RequestMapping(value = "/api/")
public class SelectTableToCSV {

    @Autowired
    private CSVUtil csvUtil;

    @RequestMapping(value = "read")
    public void toRead() {
        csvUtil.read("test");
    }

    @RequestMapping(value = "write")
    public void toWrite() {
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setId("12365");
        user.setAge("34");
        user.setName("张山");
        userList.add(user);
        csvUtil.write("test", userList);
    }

}
