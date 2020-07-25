package cn.linbin.worklog.test;

import cn.linbin.worklog.config.MyFileProperties;
import cn.linbin.worklog.utils.UploadFileUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;

@SpringBootTest
@RunWith(SpringRunner.class)
public class FileTest {

    @Autowired
    private MyFileProperties myFileProperties;

    @Test
    public void fileUpload() throws Exception {
        String fileName = "客户反馈单导入模板.xlsx";
        File file = new File("D:\\客户反馈单导入模板.xlsx");

        String myFolderPath = myFileProperties.getMyFolderPath();
        String tempFilePath = myFileProperties.getTempFilePath();

        System.out.println(myFolderPath + "||" + tempFilePath);

        if (file.exists()){
            FileInputStream fileInputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, "", fileInputStream);
            UploadFileUtil uploadFileUtil = new UploadFileUtil();
            uploadFileUtil.upload(multipartFile);
            System.out.println("文件上传成功");
        }else {
            System.out.println("文件不存在");
        }
    }
}
