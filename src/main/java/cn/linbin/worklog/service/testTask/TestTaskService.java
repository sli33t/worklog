package cn.linbin.worklog.service.testTask;

import cn.linbin.worklog.domain.po.TestTask;
import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageInfo;

public interface TestTaskService {


    /**
     * 查询测试任务
     * @param page
     * @param limit
     * @param param
     * @return
     */
    PageInfo<LbMap> findAll(int page, int limit, LbMap param);

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
     * @param page
     * @param limit
     * @param param
     * @return
     */
    PageInfo<LbMap> findDevFinish(int page, int limit, LbMap param);

    /**
     * 更新测试退回
     * @param testtaskId
     * @param feedbackId
     */
    void updateTestBack(String testtaskId, Integer feedbackId) throws Exception;

    /**
     * 测试完成
     * @param testTask
     */
    void updateTestFinish(TestTask testTask) throws Exception;
}
