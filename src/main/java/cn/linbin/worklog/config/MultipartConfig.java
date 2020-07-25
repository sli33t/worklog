package cn.linbin.worklog.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;

import javax.servlet.MultipartConfigElement;
import java.io.File;

@Configuration
public class MultipartConfig {

    @Value("${myfile.tempFilePath}")
    String tempFilePath;

    @Value("${myfile.myFolderPath}")
    String folderPath;

    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        //允许上传的文件最大值
        factory.setMaxFileSize(DataSize.parse("100MB"));
        /// 设置总上传数据总大小
        factory.setMaxRequestSize(DataSize.parse("1024MB"));
        //设置临时文件路径，以防长时间不操作后删除临时文件导致报错
        File f = new File(tempFilePath);
        if (!f.exists()){
            f.mkdirs();
        }
        factory.setLocation(tempFilePath);

        System.out.println(tempFilePath + "||" + folderPath);

        return factory.createMultipartConfig();

    }
}
