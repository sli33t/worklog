package cn.linbin.worklog.service.testTask.impl;

import cn.linbin.worklog.constant.FeedbackConstant;
import cn.linbin.worklog.dao.DevTaskDao;
import cn.linbin.worklog.dao.FeedbackDao;
import cn.linbin.worklog.dao.TestTaskDao;
import cn.linbin.worklog.domain.DevTask;
import cn.linbin.worklog.domain.TestTask;
import cn.linbin.worklog.service.testTask.TestTaskService;
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
public class TestTaskServiceImpl implements TestTaskService{

    @Autowired
    private TestTaskDao testTaskDao;

    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private DevTaskDao devTaskDao;

    /**
     * 查询测试任务
     * @param page
     * @param limit
     * @param param
     * @return
     */
    @Override
    public PageInfo<LbMap> findAll(int page, int limit, LbMap param) {
        PageHelper.startPage(page, limit);
        List<LbMap> list = testTaskDao.findAll(param);
        return new PageInfo(list);
    }

    @Override
    public TestTask findById(String testTaskId) {
        return testTaskDao.selectById(testTaskId);
    }

    /**
     * 更新测试任务
     * @param testTask
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(TestTask testTask) throws Exception{
        testTask.setTestArrange(1);
        QueryWrapper<TestTask> wrapper = new QueryWrapper<>();
        wrapper.eq("TESTTASK_ID", testTask.getTesttaskId());
        wrapper.and(Wrapper -> Wrapper.eq("TEST_ARRANGE", 0).or().isNull("TEST_ARRANGE"));
        if (testTaskDao.update(testTask, wrapper)!=1){
            throw new Exception("更新测试任务时失败，该测试任务已经分配");
        }

        if (feedbackDao.updateStatus(testTask.getFeedbackId(), FeedbackConstant.FEEDBACK_STATUS_6)!=1){
            throw new Exception("更新客反状态时失败");
        }

        DevTask devTask = new DevTask();
        devTask.setDevtaskId(testTask.getDevtaskId());
        devTask.setTestReceived(1);
        QueryWrapper<DevTask> devWrapper = new QueryWrapper<>();
        devWrapper.eq("DEVTASK_ID", testTask.getDevtaskId());
        devWrapper.and(Wrapper -> Wrapper.eq("TEST_RECEIVED", 0).or().isNull("TEST_RECEIVED"));
        if (devTaskDao.updateById(devTask)!=1){
            throw new Exception("更新开发任务时失败，该测试任务已经分配");
        }
    }

    @Override
    public int findTestFinishCount(String userId) {
        return testTaskDao.findTestFinishCount(userId);
    }

    @Override
    public PageInfo<LbMap> findDevFinish(int page, int limit, LbMap param) {
        PageHelper.startPage(page, limit);
        List<LbMap> list = testTaskDao.findDevFinish(param);
        return new PageInfo(list);
    }

    @Override
    public void updateTestBack(String testtaskId, Integer feedbackId) throws Exception {
        TestTask testTask = new TestTask();
        //testTask.setTesttaskId(testtaskId);
        testTask.setFinished(2);
        testTask.setTestArrange(0);

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        testTask.setFinishTime(dateTimeFormat.parse(dateTimeFormat.format(new Date())));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        testTask.setFinishDate(dateFormat.parse(dateFormat.format(new Date())));

        testTask.setTestText("测试退回");

        QueryWrapper<TestTask> wrapper = new QueryWrapper<>();
        wrapper.eq("TESTTASK_ID", testtaskId);
        wrapper.and(Wrapper -> Wrapper.eq("FINISHED", 0).or().isNull("FINISHED"));
        if (testTaskDao.update(testTask, wrapper)!=1){
            throw new Exception("测试退回失败，请刷新重试");
        }
    }
}
