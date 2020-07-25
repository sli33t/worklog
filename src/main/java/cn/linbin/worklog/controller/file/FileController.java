package cn.linbin.worklog.controller.file;

import cn.linbin.worklog.controller.BaseController;
import cn.linbin.worklog.domain.File;
import cn.linbin.worklog.domain.FileBo;
import cn.linbin.worklog.service.file.FileService;
import cn.linbin.worklog.utils.DownloadFileUtil;
import cn.linbin.worklog.utils.LbMap;
import cn.linbin.worklog.utils.UploadFileUtil;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value = "/file")
public class FileController extends BaseController{

    @Autowired
    private FileService fileService;

    private final static Logger logger = (Logger) LoggerFactory.getLogger(FileController.class);

    /**
     * 跳转列表页面
     * @return
     */
    @GetMapping(value = "/toFileList")
    public ModelAndView toFileList(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("file/file-list");
        return mv;
    }

    /**
     * 查询所有文件信息
     * @return
     */
    @GetMapping(value = "/findAll")
    public LbMap findAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "") String jsonStr){
        try {
            LbMap map = LbMap.fromObject(jsonStr);

            if (roleType==0){
                //管理者
                map.put("fileType", 0);
            }else {
                //员工
                map.put("fileType", 1);
            }

            PageInfo<LbMap> pages = fileService.findAll(page, limit, map);
            logger.info("查询成功");
            return LbMap.successResult("文件查询成功", pages.getList(), pages.getTotal());
        }catch (Exception e){
            return LbMap.failResult("文件查询失败，"+e.getMessage());
        }
    }


    /**
     * 跳转新增页面
     * @return
     */
    @GetMapping(value = "/toAdd")
    public ModelAndView toAdd(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("file/file-add");
        return mv;
    }

    /**
     * 文件上传
     * @param file
     * @return
     */
    @PostMapping(value = "/fileUpload")
    public LbMap fileUpload(@RequestParam(name = "file") MultipartFile file){
        try {
            LbMap resultMap = new LbMap();
            //String fileName = file.getOriginalFilename();

            //这里上传文件
            FileBo fileBo = UploadFileUtil.upload(file);

            resultMap.put("result", true);
            resultMap.put("msg", "");
            resultMap.put("fileName", fileBo.getFileName());
            //resultMap.put("fileUrl", "http://www.baidu.com");
            resultMap.put("fileFolder", fileBo.getFolder());
            resultMap.put("fileUUID", fileBo.getFileUUID());
            return resultMap;
        }catch (Exception e){
            logger.info("文件上传失败："+e.getMessage());
            return LbMap.failResult("文件上传失败，"+e.getMessage());
        }
    }


    /**
     * 文件下载
     * @param fileId
     */
    @PostMapping(value = "/downloadFile")
    public void downloadFile(String fileId) throws IOException {
        File file = fileService.findById(fileId);
        if (file!=null){
            String folder = file.getFileFolder();
            String newName = file.getFileName();
            String oldName = file.getFileUUID() + "_" + file.getFileName();
            DownloadFileUtil.download(response, folder, newName, oldName);
        }else {
            throw new IOException("没有找到文件信息");
        }
    }



    /**
     * 新增和修改
     * @param file
     * @return
     */
    @PostMapping(value = "/edit")
    public LbMap edit(File file) {
        try {
            //File filePojo = new File();

            file.setUserId(userId);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            file.setCreateTime(dateFormat.parse(dateFormat.format(new Date())));

            logger.info(file.toString());


            if (file.getFileName().equals("")){
                return LbMap.failResult("文件编辑失败，文件名称不能为空，请重新上传");
            }

            if (file.getFileFolder().equals("")){
                return LbMap.failResult("文件编辑失败，文件路径不能为空，请重新上传");
            }


            //新增
            if (StringUtils.isEmpty(file.getFileId())){
                //初始化的行版本号为0
                file.setDeleteFlag(0);
                //ID为空的为新增
                fileService.save(file);
            }else {
                //ID不为空的为修改

            }

            return LbMap.successResult("文件编辑成功");
        }catch (Exception e){
            logger.info("文件编辑失败："+e.getMessage());
            return LbMap.failResult("文件编辑失败，"+e.getMessage());
        }
    }


    /**
     * 删除
     * @param fileId
     * @return
     */
    @PostMapping(value = "/delete")
    public LbMap delete(String fileId){
        try {
            if (fileId.equals("")){
                return LbMap.failResult("文件删除失败，没有找到文件编号！");
            }
            fileService.delete(fileId);
            return LbMap.successResult("文件删除成功");
        }catch (Exception e){
            return LbMap.failResult("文件删除失败，"+e.getMessage());
        }
    }
}
