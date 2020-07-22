package cn.linbin.worklog.service.testTask.impl;

import cn.linbin.worklog.constant.FeedbackConstant;
import cn.linbin.worklog.dao.DevTaskDao;
import cn.linbin.worklog.dao.FeedbackDao;
import cn.linbin.worklog.dao.TestTaskDao;
import cn.linbin.worklog.domain.DevTask;
import cn.linbin.worklog.domain.TestTask;
import cn.linbin.worklog.service.testTask.TestTaskService;
import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * @param pageIndex
     * @param pageSize
     * @param param
     * @return
     */
    @Override
    public PageInfo<LbMap> findAll(int pageIndex, int pageSize, LbMap param) {
        PageHelper.startPage(pageIndex, pageSize);
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
    public void update(TestTask testTask) throws Exception{
        if (testTaskDao.updateById(testTask)!=1){
            throw new Exception("更新测试任务时失败，请刷新数据重试");
        }

        if (feedbackDao.updateStatus(testTask.getFeedbackId(), FeedbackConstant.FEEDBACK_STATUS_6)!=1){
            throw new Exception("更新客反状态时失败");
        }

        DevTask devTask = new DevTask();
        devTask.setDevtaskId(testTask.getDevtaskId());
        devTask.setTestReceived(1);
        if (devTaskDao.updateById(devTask)!=1){
            throw new Exception("更新开发任务时失败");
        }
    }
}
