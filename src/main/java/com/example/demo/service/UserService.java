package com.example.demo.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.stream.IntStream;

/**
 * Description:
 * Creator: jiangyongheng
 * Date: 2019/04/09
 * Time: 11:53
 */
@Service
public class UserService {

    @Async
    public void sendSms() {
        System.out.println("####sendSms####   2");
        IntStream.range(0, 5).forEach(d -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println("####sendSms####   3");
    }

}
