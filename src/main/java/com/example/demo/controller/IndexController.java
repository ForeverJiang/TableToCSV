package com.example.demo.controller;

import com.example.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:
 * Creator: jiangyongheng
 * Date: 2019/04/09
 * Time: 11:55
 */
@RestController
public class IndexController {

    @Autowired
    private UserService userService;

    @RequestMapping("/async")
    public String async() {
        System.out.println("####IndexController####   1");
        userService.sendSms();
        System.out.println("####IndexController####   4");
        return "success";
    }

}
