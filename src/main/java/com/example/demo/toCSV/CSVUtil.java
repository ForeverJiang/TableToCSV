package com.example.demo.toCSV;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Description: 将选择的表导出为CSV文件
 * Creator: jiangyongheng
 * Date: 2019/04/08
 * Time: 10:03
 */
@Component
public class CSVUtil {

//    @Autowired
//    @Qualifier("singleThreaded")
//    private static ExecutorService executorService;

    public static void read(String name) {

        String filePath = "/Users/jiangyongheng/Desktop/" + name + ".csv";

        try {
            // 创建CSV读对象
            CsvReader csvReader = new CsvReader(filePath, ',', Charset.forName("UTF-8"));

            // 读表头  跳过表头   如果需要表头的话，不要写这句
//            csvReader.readHeaders();
//            String[] head = csvReader.getHeaders(); //获取表头
            while (csvReader.readRecord()) {
                // 读一整行
                System.out.println(csvReader.getRawRecord());
//                // 读这行的某一列
//                System.out.println(csvReader.get("Link"));
//                for (int i = 0; i < head.length; i++)
//                {
//                    System.out.println(head[i] + ":" + csvReader.get(head[i]));
//                }
//                for (String h : head) {
//                    System.out.println(h + ":" + csvReader.get(h));
//                }
            }
            csvReader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    @Async
    public void write(String name, List<? extends Object> objectList) {
//        executorService.submit(() -> {
            try {
                String filePath = "/Users/jiangyongheng/Desktop/" + name + ".csv";
                // 创建CSV写对象
                CsvWriter csvWriter = new CsvWriter(filePath, ',', Charset.forName("GBK"));
                //CsvWriter csvWriter = new CsvWriter(filePath);

                // 写表头
                Field[] declaredFields = User.class.getDeclaredFields();
                String[] headers = new String[declaredFields.length];
                for (int i = 0; i < declaredFields.length; i++) {
                    headers[i] = declaredFields[i].getName();
                }
                csvWriter.writeRecord(headers);

                //写数据
                for (int i = 0; i < objectList.size(); i++) {
                    Field[] fields = objectList.get(i).getClass().getDeclaredFields();
                    String[] content = new String[fields.length];
                    Object oi = objectList.get(i);
                    for (int j = 0; j < fields.length; j++) {
                        if (!fields[j].isAccessible()) {
                            fields[j].setAccessible(true);
                        }
                        content[j] = (String) fields[j].get(oi);
                    }
                    csvWriter.writeRecord(content);
                }
                csvWriter.close();

            } catch (IOException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
//        });

    }

}
