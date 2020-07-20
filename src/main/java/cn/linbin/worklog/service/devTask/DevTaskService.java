package cn.linbin.worklog.service.devTask;

import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageInfo;

public interface DevTaskService {

    /**
     * 查询所有待分配的任务
     * @param pageIndex
     * @param pageSize
     * @param param
     * @return
     */
    PageInfo<LbMap> findAll(int pageIndex, int pageSize, LbMap param);
}
