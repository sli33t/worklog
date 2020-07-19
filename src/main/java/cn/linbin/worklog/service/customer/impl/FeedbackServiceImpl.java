package cn.linbin.worklog.service.customer.impl;

import cn.linbin.worklog.dao.FeedbackDao;
import cn.linbin.worklog.domain.Feedback;
import cn.linbin.worklog.service.customer.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService{

    @Autowired
    private FeedbackDao feedbackDao;

    /**
     * 编辑客户反馈单
     * @param feedback
     */
    @Override
    public void save(Feedback feedback) throws Exception {
        int count = feedbackDao.insert(feedback);
        if (count<=0){
            throw new Exception("客户反馈单写入失败！");
        }
    }
}
