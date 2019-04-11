package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
//防止mapper出现红线
@Mapper
public interface FileDownloadMapper {
    //查询当前库中所有表的表名
    List<String> queryListName();

    List<User> queryAllUser();
}
