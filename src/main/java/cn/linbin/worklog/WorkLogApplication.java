package cn.linbin.worklog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WorkLogApplication {

    public static void main(String[] args) {
        SpringApplication.run(WorkLogApplication.class, args);
    }

    /*@Value("${tempFilePath}")
    String tempFilePath;*/

    /*@Bean
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
        return factory.createMultipartConfig();

    }*/
}
