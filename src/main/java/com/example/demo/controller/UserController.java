package com.example.demo.controller;

import com.example.demo.mapper.FileDownloadMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Creator: jiangyongheng
 * Date: 2019/04/10
 * Time: 10:49
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private FileDownloadMapper fileDownloadMapper;

    @RequestMapping("/{id}")
    public String getUser(@PathVariable String id, Model model) {

        model.addAttribute("user", new User(id, "张三", "20"));
        return "/user/detail";
    }

    @GetMapping("/list")
    public ModelAndView listUser(Model model) {
        List<User> userList = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            userList.add(new User(i + "", "张三" + i, 20 + i + ""));
        }

//        model.addAttribute("users", userList);

        return new ModelAndView("user/list", "users", userList);
    }

    @GetMapping("/tableList")
    public ModelAndView tableList(Model model) {
        List<User> userList = new ArrayList<User>();
        List<String> tables = fileDownloadMapper.queryListName();

//        model.addAttribute("users", userList);

        return new ModelAndView("user/tableList", "tables", tables);
    }
}