package cn.linbin.worklog.service.file.impl;

import cn.linbin.worklog.dao.FileDao;
import cn.linbin.worklog.domain.File;
import cn.linbin.worklog.service.file.FileService;
import cn.linbin.worklog.utils.IdWorker;
import cn.linbin.worklog.utils.LbMap;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class FileServiceImpl implements FileService{

    @Autowired
    private FileDao fileDao;

    @Override
    public PageInfo<LbMap> findAll(int page, int limit, LbMap param) {
        PageHelper.startPage(page, limit);
        List<LbMap> list = fileDao.findAll(param);
        return new PageInfo(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(File file) throws Exception {
        file.setFileId(IdWorker.getNumId18());
        int count = fileDao.insert(file);
        if (count<=0){
            throw new Exception("文件写入失败！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String fileId) throws Exception {
        File file = new File();
        file.setDeleteFlag(1);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        file.setDeleteDate(dateFormat.parse(dateFormat.format(new Date())));

        QueryWrapper<File> wrapper = new QueryWrapper<>();
        wrapper.eq("FILE_ID", fileId);

        int count = fileDao.update(file, wrapper);
        if (count<=0){
            throw new Exception("文件删除失败，请刷新数据！");
        }
    }
}
