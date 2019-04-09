package com.example.demo.controller;

import com.example.demo.model.User;
import com.example.demo.toCSV.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

/**
 * Description:
 * Creator: jiangyongheng
 * Date: 2019/04/09
 * Time: 14:53
 */
@RestController
public class FileDownloadController {

    @Autowired
    private FileUtil fileUtil;

    //实现Spring Boot 的文件下载功能，映射网址为/download
    @RequestMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        fileUtil.downloadFile(request, response, "test");
    }
}
