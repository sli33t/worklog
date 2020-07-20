package cn.linbin.worklog.service.devTask.impl;

import cn.linbin.worklog.constant.FeedbackConstant;
import cn.linbin.worklog.dao.DevTaskDao;
import cn.linbin.worklog.domain.DevTask;
import cn.linbin.worklog.service.customer.FeedbackService;
import cn.linbin.worklog.service.devTask.DevTaskService;
import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DevTaskServiceImpl implements DevTaskService{

    @Autowired
    private DevTaskDao devTaskDao;

    @Autowired
    private FeedbackService feedbackService;

    @Override
    public PageInfo<LbMap> findAll(int pageIndex, int pageSize, LbMap param) {
        PageHelper.startPage(pageIndex, pageSize);
        List<LbMap> list = devTaskDao.findAll(param);
        return new PageInfo(list);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void edit(DevTask devTask) throws Exception {
        int count = devTaskDao.insert(devTask);
        if (count<=0){
            throw new Exception("开发任务分配失败！");
        }

        //更新客反状态为已经分配开发
        count = feedbackService.updateStatus(devTask.getFeedbackId(), FeedbackConstant.FEEDBACK_STATUS_2);
        if (count<=0){
            throw new Exception("开发任务分配失败！");
        }
    }
}
