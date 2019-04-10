package com.example.demo.model;

import lombok.Data;

/**
 * Description:
 * Creator: jiangyongheng
 * Date: 2019/04/08
 * Time: 14:17
 */
@Data
public class User {
    public User(String id, String name, String age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public User() {

    }

    private String id;
    private String name;
    private String age;
}
