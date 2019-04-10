package com.example.demo.toCSV;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import com.example.demo.model.User;
import org.apache.catalina.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Description: 将选择的表导出为CSV文件并压缩
 * Creator: jiangyongheng
 * Date: 2019/04/08
 * Time: 10:03
 */
@Component
public class FileUtil {

//    @Autowired
//    @Qualifier("singleThreaded")
//    private static ExecutorService executorService;

    @Async
    public void read(String name) {

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

    /**
     * 异步方式实现csv文件
     *
     * @param name
     * @param objectList
     */
    @Async
    public void write(String name, List<? extends Object> objectList) {
//        executorService.submit(() -> {
        try {
//            String localPath = "/Users/jiangyongheng/Desktop/";
            String localPath = "src/main/resources/file/";
            String filePath = localPath + name + ".csv";
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

            //压缩
            zipUtil(name, filePath);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
//        });
    }

    /**
     * 压缩
     *
     * @param name
     * @param filePath
     * @throws IOException
     */
    public void zipUtil(String name, String filePath) throws IOException {
        //这个是文件夹的绝对路径，如果想要相对路径就自行了解写法
        File fileToZip = new File(filePath);
        //这个是压缩之后的文件绝对路径
//        FileOutputStream fos = new FileOutputStream(
//                "/Users/jiangyongheng/Desktop/" + name + ".zip");
        FileOutputStream fos = new FileOutputStream(
                "src/main/resources/file/" + name + ".zip");
        ZipOutputStream zipOut = new ZipOutputStream(fos);
        zipFile(fileToZip, fileToZip.getName(), zipOut);
        zipOut.close();
        fos.close();
    }

    /**
     * zip压缩
     *
     * @param fileToZip
     * @param fileName
     * @param zipOut
     * @throws IOException
     */

    private void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws IOException {
        if (fileToZip.isHidden()) {
            return;
        }
        if (fileToZip.isDirectory()) {
            if (fileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(fileName));
                zipOut.closeEntry();
            } else {
                zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                zipOut.closeEntry();
            }
            File[] children = fileToZip.listFiles();
            for (File childFile : children) {
                zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
            }
            return;
        }
        FileInputStream fis = new FileInputStream(fileToZip);
        ZipEntry zipEntry = new ZipEntry(fileName);
        zipOut.putNextEntry(zipEntry);
        byte[] bytes = new byte[1024];
        int length;
        while ((length = fis.read(bytes)) >= 0) {
            zipOut.write(bytes, 0, length);
        }
        fis.close();
    }


    /**
     * 文件下载
     *
     * @param request
     * @param response
     * @return
     * @throws UnsupportedEncodingException
     */
    public void downloadFile(HttpServletRequest request,
                             HttpServletResponse response, String name) throws UnsupportedEncodingException {

        String fileName = name + ".zip"; //下载的文件名

        // 如果文件名不为空，则进行下载
        if (fileName != null) {
            //设置文件路径
//            String realPath = "/Users/jiangyongheng/Desktop/";
            String realPath = "src/main/resources/file/";
            File file = new File(realPath, fileName);
            // 如果文件名存在，则进行下载
            if (file.exists()) {
                // 配置文件下载
                response.setHeader("content-type", "application/octet-stream");
                response.setContentType("application/octet-stream");
                // 下载文件能正常显示中文
                response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

                // 实现文件下载
                byte[] buffer = new byte[1024];
                FileInputStream fis = null;
                BufferedInputStream bis = null;
                try {
                    fis = new FileInputStream(file);
                    bis = new BufferedInputStream(fis);
                    OutputStream os = response.getOutputStream();
                    int i = bis.read(buffer);
                    while (i != -1) {
                        os.write(buffer, 0, i);
                        i = bis.read(buffer);
                    }
                    System.out.println("Download the file successfully!");
                } catch (Exception e) {
                    System.out.println("Download the file failed!");
                } finally {
                    if (bis != null) {
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
