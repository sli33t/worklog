package cn.linbin.worklog.controller;

import cn.linbin.worklog.config.MyFileProperties;
import cn.linbin.worklog.utils.DownloadFileUtil;
import cn.linbin.worklog.utils.UploadFileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * 测试类
 */
//@RestController
public class UploadController {

    @Autowired
    private MyFileProperties myFileProperties;

    //@PostMapping(value = "/upload")
    public String fileUpload(@RequestParam String fileName) throws IOException {
        File file = new File("D:\\"+fileName);

        String myFolderPath = myFileProperties.getMyFolderPath();
        String tempFilePath = myFileProperties.getTempFilePath();

        System.out.println(myFolderPath + "||" + tempFilePath);

        if (file.exists()){
            FileInputStream fileInputStream = new FileInputStream(file);
            MultipartFile multipartFile = new MockMultipartFile(fileName, fileName, "", fileInputStream);
            UploadFileUtil uploadFileUtil = new UploadFileUtil();
            uploadFileUtil.upload(multipartFile);
            return "";
        }else {
            return "没有找到指定的文件";
        }
    }


    //@GetMapping(value = "/download")
    public void fileDownload(HttpServletResponse response, @RequestParam String folder, @RequestParam String returnName) throws Exception {
        DownloadFileUtil downloadUtil = new DownloadFileUtil();
        String newName = downloadUtil.getFileName(returnName);
        downloadUtil.download(response, folder, newName, returnName);
    }
}
