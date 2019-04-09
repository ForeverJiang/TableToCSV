package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.toCSV.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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
public class TableToCSVController {

    @Autowired
    private FileUtil fileUtil;

    @RequestMapping(value = "read")
    public void toRead() {
        fileUtil.read("test");
    }

    @RequestMapping(value = "write")
    public void toWrite() {
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setId("12365");
        user.setAge("34");
        user.setName("张山");
        userList.add(user);
        fileUtil.write("test", userList);
    }

}
