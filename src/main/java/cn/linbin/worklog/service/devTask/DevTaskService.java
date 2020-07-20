package cn.linbin.worklog.service.devTask;

import cn.linbin.worklog.domain.DevTask;
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

    /**
     * 分配开发任务
     * @param devTask
     */
    void edit(DevTask devTask) throws Exception;

    /**
     * 开发完成列表
     * @param pageIndex
     * @param pageSize
     * @param param
     * @return
     */
    PageInfo<LbMap> findDevFinish(int pageIndex, int pageSize, LbMap param);

    /**
     * 更新开发完成
     * @param devTask
     */
    void updateDevFinish(DevTask devTask) throws Exception;

    /**
     * 更新开发退回
     * @param devtaskId
     */
    void updateDevBack(String devtaskId, Integer feedbackId) throws Exception;
}
