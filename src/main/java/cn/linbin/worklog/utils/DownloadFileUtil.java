package cn.linbin.worklog.utils;

import cn.linbin.worklog.config.MyFileProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@Component
public class DownloadFileUtil {

    @Autowired
    private MyFileProperties mfp;
    private static MyFileProperties myFileProperties;

    //工具类都是静态方法，所以需要使用 PostConstruct 注解初始化一下
    @PostConstruct
    public void init(){
        myFileProperties = this.mfp;
    }

	public static String getFileName(String returnName){
		//将前面的32位UUID去掉，得到文件名
		return returnName.substring(33, returnName.length());
	}

    /**
     * 下载方法
     * @param response
     * @param folder 文件夹，由于上传文件时是一天创建一个文件夹，所以下载的时候需要从文件夹里取，这个字段需要存入数据库
     * @param newName 去掉32位UUID的文件名称
     * @param oldName 带着32位UUID的文件名称
     * @throws IOException
     */
	public static void download(HttpServletResponse response, String folder, String newName, String oldName) throws IOException{

	    String fullName = folder;
        char pathChar = folder.charAt(folder.length() - 1);
        if  (pathChar == '/') {
            fullName = fullName + oldName;
        }else {
            fullName = fullName + "/" + oldName;
        }

        String folderPath = myFileProperties.getMyFolderPath();
		File file = new File(folderPath + fullName);
		if (file.exists()){
			response.setContentType("application/octet-stream;charset=utf-8");
			newName = response.encodeURL(new String(newName.getBytes(),"iso8859-1"));
			response.addHeader("Content-Disposition",   "attachment;filename=" + newName);

			byte[] buffer = new byte[1024];
			FileInputStream fis = null;
			BufferedInputStream bis = null;

			try {
				fis = new FileInputStream(file);
				bis = new BufferedInputStream(fis);

				ServletOutputStream outputstream = response.getOutputStream();
				int i = bis.read(buffer);
				while (i != -1){
					outputstream.write(buffer, 0, i);
					i = bis.read(buffer);
				}

				outputstream.flush();
			}catch (Exception e){
				e.printStackTrace();
			}finally {
				bis.close();
				fis.close();
			}
		}else {
			throw new IOException("在文件服务中没有找到[ "+newName+" ]这个文件");
		}
	}

}
