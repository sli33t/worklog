package cn.linbin.worklog.service.testTask;

import cn.linbin.worklog.domain.TestTask;
import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageInfo;

import java.text.ParseException;

public interface TestTaskService {


    /**
     * 查询测试任务
     * @param pageIndex
     * @param pageSize
     * @param param
     * @return
     */
    PageInfo<LbMap> findAll(int pageIndex, int pageSize, LbMap param);

    /**
     * 查询测试任务
     * @param testTaskId
     * @return
     */
    TestTask findById(String testTaskId);

    /**
     * 更新测试任务
     * @param testTask
     */
    void update(TestTask testTask) throws Exception;

    /**
     * 查询当前登录人员测试数量
     * @param userId
     * @return
     */
    int findTestFinishCount(String userId);

    /**
     * 查询测试完成
     * @param pageIndex
     * @param pageSize
     * @param param
     * @return
     */
    PageInfo<LbMap> findDevFinish(int pageIndex, int pageSize, LbMap param);

    /**
     * 更新测试退回
     * @param testtaskId
     * @param feedbackId
     */
    void updateTestBack(String testtaskId, Integer feedbackId) throws Exception;
}
