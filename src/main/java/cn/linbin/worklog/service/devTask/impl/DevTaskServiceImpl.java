package cn.linbin.worklog.service.devTask.impl;

import cn.linbin.worklog.constant.FeedbackConstant;
import cn.linbin.worklog.dao.DevTaskDao;
import cn.linbin.worklog.dao.FeedbackDao;
import cn.linbin.worklog.dao.TestTaskDao;
import cn.linbin.worklog.domain.po.DevTask;
import cn.linbin.worklog.domain.po.TestTask;
import cn.linbin.worklog.service.devTask.DevTaskService;
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
public class DevTaskServiceImpl implements DevTaskService{

    @Autowired
    private DevTaskDao devTaskDao;

    @Autowired
    private FeedbackDao feedbackDao;

    @Autowired
    private TestTaskDao testTaskDao;

    @Override
    public PageInfo<LbMap> findAll(int page, int limit, LbMap param) {
        PageHelper.startPage(page, limit);
        List<LbMap> list = devTaskDao.findAll(param);
        return new PageInfo(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(DevTask devTask) throws Exception {
        //检查一下是否存在退回的，如果有就是重新分配
        List<LbMap> list = feedbackDao.haveDevBack(devTask.getFeedbackId());
        if (list!=null&&list.size()>0){
            //更新客反状态为已经分配开发
            if (feedbackDao.updateStatus(devTask.getFeedbackId(), FeedbackConstant.FEEDBACK_STATUS_4)!=1){
                throw new Exception("更新客反状态失败！");
            }
        }else {
            //更新客反状态为已经分配开发
            if (feedbackDao.updateStatus(devTask.getFeedbackId(), FeedbackConstant.FEEDBACK_STATUS_2)!=1){
                throw new Exception("更新客反状态失败！");
            }
        }

        int count = devTaskDao.insert(devTask);
        if (count<=0){
            throw new Exception("开发任务分配失败！");
        }
    }

    @Override
    public PageInfo<LbMap> findDevFinish(int page, int limit, LbMap param) {
        PageHelper.startPage(page, limit);
        List<LbMap> list = devTaskDao.findDevFinish(param);
        return new PageInfo(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDevFinish(DevTask devTask) throws Exception {
        devTask.setFinished(1);

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date time = dateTimeFormat.parse(dateTimeFormat.format(new Date()));
        Date date = dateFormat.parse(dateFormat.format(new Date()));

        devTask.setFinishTime(time);
        devTask.setFinishDate(date);

        QueryWrapper<DevTask> devWrapper =  new QueryWrapper<>();
        devWrapper.eq("DEVTASK_ID", devTask.getDevtaskId());
        devWrapper.and(Wrapper -> Wrapper.eq("FINISHED", 0).or().isNull("FINISHED"));
        if (devTaskDao.update(devTask, devWrapper)!=1){
            throw new Exception("开发完成失败！");
        }

        //更新客反状态为已经开发完成
        if (feedbackDao.updateStatus(devTask.getFeedbackId(), FeedbackConstant.FEEDBACK_STATUS_5)!=1){
            throw new Exception("更新客反状态失败！");
        }

        /*
        //客反在测试完成时才算完成
        Feedback feedback = new Feedback();
        feedback.setFeedbackId(devTask.getFeedbackId());
        feedback.setFinished(1);

        feedback.setFinishTime(time);
        feedback.setFinishDate(date);

        if (feedbackDao.updateById(feedback)!=1){
            throw new Exception("更新客反完成状态失败！");
        }*/

        //通知测试
        if (devTask.getNeedTest()==1){

            QueryWrapper<TestTask> wrapper = new QueryWrapper<>();
            wrapper.eq("FEEDBACK_ID", devTask.getFeedbackId());
            wrapper.eq("DEVTASK_ID", devTask.getDevtaskId());
            List<TestTask> testTaskList = testTaskDao.selectList(wrapper);

            if (testTaskList==null||testTaskList.size()==0){
                TestTask testTask = new TestTask();
                testTask.setFeedbackId(devTask.getFeedbackId());
                testTask.setDevtaskId(devTask.getDevtaskId());
                testTask.setFeedbackTime(devTask.getFeedbackTime());
                if (testTaskDao.insert(testTask)!=1){
                    throw new Exception("写入测试表时失败！");
                }
            }else {
                throw  new Exception("测试任务已经写入！");
            }
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDevBack(String devtaskId, Integer feedbackId) throws Exception {
        DevTask devTask = new DevTask();
        //devTask.setDevtaskId(devtaskId);
        devTask.setFinished(2); //退回

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        devTask.setFinishTime(dateTimeFormat.parse(dateTimeFormat.format(new Date())));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        devTask.setFinishDate(dateFormat.parse(dateFormat.format(new Date())));

        devTask.setFinishText("开发退回");

        QueryWrapper<DevTask> wrapper = new QueryWrapper<>();
        wrapper.eq("DEVTASK_ID", devtaskId);
        wrapper.and(Wrapper -> Wrapper.eq("FINISHED", 0).or().isNull("FINISHED"));
        if (devTaskDao.update(devTask, wrapper)!=1){
            throw new Exception("开发退回失败，请刷新数据重试！");
        }

        //更新客反状态为已经开发完成
        if (feedbackDao.updateStatus(feedbackId, FeedbackConstant.FEEDBACK_STATUS_3)!=1){
            throw new Exception("更新客反状态失败！");
        }
    }

    /**
     * 查询当前开发人员的数量
     * @param userId
     * @return
     */
    @Override
    public int findDevFinishCount(String userId) {
        LbMap map = devTaskDao.findDevFinishCount(userId);
        if (map!=null&&map.size()>0){
            return map.getInt("count");
        }else {
            return 0;
        }
    }

    /**
     * 检查客反是否分配过开发任务
     * @param feedbackId
     * @return
     */
    @Override
    public List<LbMap> checkDevTask(Integer feedbackId) {
        return devTaskDao.checkDevTask(feedbackId);
    }


    /**
     * 更新当前用户的接收状态
     * @param userId
     */
    @Override
    public void updateDevReceived(String userId) throws Exception {
        DevTask devTask = new DevTask();

        devTask.setReceived(1);
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        devTask.setReceiveTime(dateTimeFormat.parse(dateTimeFormat.format(new Date())));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        devTask.setReceiveDate(dateFormat.parse(dateFormat.format(new Date())));

        QueryWrapper<DevTask> wrapper = new QueryWrapper<>();
        wrapper.eq("DEVELOP_USER_ID", userId);
        wrapper.and(Wrapper -> Wrapper.eq("FINISHED", 0).or().isNull("FINISHED"));
        devTaskDao.update(devTask, wrapper);
    }
}
