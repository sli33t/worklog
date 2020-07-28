package cn.linbin.worklog.service.devTask;

import cn.linbin.worklog.domain.po.DevTask;
import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface DevTaskService {

    /**
     * 查询所有待分配的任务
     * @param page
     * @param limit
     * @param param
     * @return
     */
    PageInfo<LbMap> findAll(int page, int limit, LbMap param);

    /**
     * 分配开发任务
     * @param devTask
     */
    void edit(DevTask devTask) throws Exception;

    /**
     * 开发完成列表
     * @param page
     * @param limit
     * @param param
     * @return
     */
    PageInfo<LbMap> findDevFinish(int page, int limit, LbMap param);

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

    /**
     * 查询当前开发人员的数量
     * @param userId
     * @return
     */
    int findDevFinishCount(String userId);

    /**
     * 检查客反是否分配过开发任务
     * @param feedbackId
     * @return
     */
    List<LbMap> checkDevTask(Integer feedbackId);


    /**
     * 接收当前用户所有消息
     * @param userId
     */
    void updateDevReceived(String userId) throws Exception;
}
