package cn.linbin.worklog.service.devTask.impl;

import cn.linbin.worklog.constant.FeedbackConstant;
import cn.linbin.worklog.dao.DevTaskDao;
import cn.linbin.worklog.dao.FeedbackDao;
import cn.linbin.worklog.domain.DevTask;
import cn.linbin.worklog.domain.Feedback;
import cn.linbin.worklog.service.devTask.DevTaskService;
import cn.linbin.worklog.utils.LbMap;
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

    @Override
    public PageInfo<LbMap> findAll(int pageIndex, int pageSize, LbMap param) {
        PageHelper.startPage(pageIndex, pageSize);
        List<LbMap> list = devTaskDao.findAll(param);
        return new PageInfo(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(DevTask devTask) throws Exception {
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
    public PageInfo<LbMap> findDevFinish(int pageIndex, int pageSize, LbMap param) {
        PageHelper.startPage(pageIndex, pageSize);
        List<LbMap> list = devTaskDao.findDevFinish(param);
        return new PageInfo(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDevFinish(DevTask devTask) throws Exception {
        if (devTaskDao.updateById(devTask)!=1){
            throw new Exception("开发完成失败！");
        }

        //更新客反状态为已经开发完成
        if (feedbackDao.updateStatus(devTask.getFeedbackId(), FeedbackConstant.FEEDBACK_STATUS_5)!=1){
            throw new Exception("更新客反状态失败！");
        }

        Feedback feedback = new Feedback();
        feedback.setFeedbackId(devTask.getFeedbackId());
        feedback.setFinished(1);

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        feedback.setFinishTime(dateTimeFormat.parse(dateTimeFormat.format(new Date())));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        feedback.setFinishDate(dateFormat.parse(dateFormat.format(new Date())));

        if (feedbackDao.updateById(feedback)!=1){
            throw new Exception("更新客反完成状态失败！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateDevBack(String devtaskId, Integer feedbackId) throws Exception {
        DevTask devTask = new DevTask();
        devTask.setDevtaskId(devtaskId);
        devTask.setFinished(2); //退回

        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        devTask.setFinishTime(dateTimeFormat.parse(dateTimeFormat.format(new Date())));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        devTask.setFinishDate(dateFormat.parse(dateFormat.format(new Date())));

        devTask.setFinishText("开发退回");

        if (devTaskDao.updateById(devTask)!=1){
            throw new Exception("开发退回失败！");
        }

        //更新客反状态为已经开发完成
        if (feedbackDao.updateStatus(feedbackId, FeedbackConstant.FEEDBACK_STATUS_3)!=1){
            throw new Exception("更新客反状态失败！");
        }
    }
}
