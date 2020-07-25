package cn.linbin.worklog.utils;

import cn.linbin.worklog.config.MyFileProperties;
import cn.linbin.worklog.domain.FileBo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Component
public class UploadFileUtil {

    @Autowired
    private MyFileProperties mfp;
    private static MyFileProperties myFileProperties;

    private static String HorizontalBar = "-";

    private static String empty = "";

    //工具类都是静态方法，所以需要使用 PostConstruct 注解初始化一下
    @PostConstruct
    public void init(){
        myFileProperties = this.mfp;
    }

    /**
     * 上传方法
     * @param file 文件
     * @return 文件路径
     * @throws FileNotFoundException
     */
    public static FileBo upload(MultipartFile file) throws FileNotFoundException {

        // 获得原始文件名+格式
        String fileName = file.getOriginalFilename();

        if (fileName==null||fileName.equals("")){
            throw new FileNotFoundException("文件名称不能为空！");
        }

        //截取文件名
        String name = fileName.substring(0, fileName.lastIndexOf("."));
        //截取文件格式
        String format = fileName.substring(fileName.lastIndexOf(".") + 1);
        //获取当前时间(精确到毫秒)
        /*long MS = System.currentTimeMillis();
        String timeMS = String.valueOf(MS);*/

        /**
         * 重新编号+原文件名作为新文件名，为了防止重复
         */
        String uuid = UUID.randomUUID().toString().toUpperCase().replace(HorizontalBar, empty);
        String newName = uuid + "_" + name + "." + format;

        //先创建文件夹
        String fileLocalPath = "";

        String folderPath = myFileProperties.getMyFolderPath();
        char pathChar = folderPath.charAt(folderPath.length() - 1);
        Date date = new Date();
        String dateOne = new SimpleDateFormat("yyyyMMdd").format(date);
        if (pathChar == '/') {
            fileLocalPath = folderPath + dateOne + "/";
        } else {
            fileLocalPath = folderPath + "/" + dateOne + "/";
        }

        //拼接完整文件名称
        String fullName = fileLocalPath + newName;

        //创建文件夹
        File f = new File(fileLocalPath);
        if (!f.exists()){
            f.mkdirs();
        }

        if (!file.isEmpty()) {
            try {
                FileOutputStream fos = new FileOutputStream(fullName);
                InputStream in = file.getInputStream();
                byte[] bytes = new byte[1024];
                int len = 0;
                while ((len = in.read(bytes)) != -1) {
                    fos.write(bytes, 0, len);
                }
                fos.close();
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        FileBo fileBo = new FileBo();
        fileBo.setFileName(fileName);
        fileBo.setFolder(dateOne);
        fileBo.setFileUUID(uuid);
        return fileBo;
    }
}
