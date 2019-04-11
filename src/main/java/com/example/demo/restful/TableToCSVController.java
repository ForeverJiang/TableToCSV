package com.example.demo.restful;

import com.example.demo.mapper.FileDownloadMapper;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.toCSV.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

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
    private UserService userService;

    @Autowired
    private FileUtil fileUtil;

    @Autowired
    private FileDownloadMapper fileDownloadMapper;


    //将表中的数据写入csv
    @GetMapping(value = "write")
    public ModelAndView toWrite() {
        List<User> userList = new ArrayList<>();
        User user = new User();
        user.setId("12365");
        user.setAge("34");
        user.setName("张山");
        userList.add(user);
        fileUtil.write("test", userList);
        List<String> tables = fileDownloadMapper.queryListName();
        return new ModelAndView("user/tableList", "tables", tables);
    }


    //获取到表的列表
    @GetMapping("/tableList")
    public ModelAndView tableList(Model model) {
        List<User> userList = new ArrayList<User>();
        List<String> tables = fileDownloadMapper.queryListName();

//        model.addAttribute("users", userList);

        return new ModelAndView("user/tableList", "tables", tables);
    }


    //获取用户列表
    @GetMapping("/list")
    public ModelAndView listUser(Model model) {
        List<User> userList = new ArrayList<User>();
        for (int i = 0; i < 10; i++) {
            userList.add(new User(i + "", "张三" + i, 20 + i + ""));
        }

//        model.addAttribute("users", userList);

        return new ModelAndView("user/list", "users", userList);
    }


    //实现Spring Boot 的文件下载功能，映射网址为/download
    @RequestMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        fileUtil.downloadFile(request, response, "test");
    }


    //异步测试
    @RequestMapping("/async")
    public String async() {
        System.out.println("####IndexController####   1");
        userService.sendSms();
        System.out.println("####IndexController####   4");
        return "success";
    }


}
