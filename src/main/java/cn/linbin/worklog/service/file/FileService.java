package cn.linbin.worklog.service.file;

import cn.linbin.worklog.domain.File;
import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageInfo;

public interface FileService {

    /**
     * 查询所有文件
     * @return
     */
    PageInfo<LbMap> findAll(int pageIndex, int pageSize, LbMap param);

    /**
     * 保存客户信息
     * @param file
     * @return
     */
    void save(File file) throws Exception;

    /**
     * 删除客户信息
     * @param fileId
     * @return
     */
    void delete(String fileId) throws Exception;
}