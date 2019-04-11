package com.example.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

//@Service
//防止mapper出现红线
@Mapper
public interface FileDownloadMapper {
    List<String> queryListName();
}
