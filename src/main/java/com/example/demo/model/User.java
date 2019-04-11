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
    public User(int id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

    public User() {

    }

    private int id;
    private String name;
    private int age;
}
