package cn.linbin.worklog.service.customer.impl;

import cn.linbin.worklog.dao.FeedbackDao;
import cn.linbin.worklog.domain.Feedback;
import cn.linbin.worklog.service.customer.FeedbackService;
import cn.linbin.worklog.utils.LbMap;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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


    @Override
    public void update(Feedback feedback) throws Exception {
        QueryWrapper<Feedback> wrapper = new QueryWrapper<>();
        wrapper.eq("FEEDBACK_ID", feedback.getFeedbackId());
        wrapper.eq("ROW_VERSION", feedback.getRowVersion());
        feedback.setRowVersion(feedback.getRowVersion()+1);
        int count = feedbackDao.update(feedback, wrapper);
        if (count<=0){
            throw new Exception("客户反馈单写入失败！");
        }
    }

    /**
     * 查询客户反馈单列表
     * @param pageIndex
     * @param pageSize
     * @param param
     * @return
     */
    @Override
    public PageInfo<LbMap> findAll(int pageIndex, int pageSize, LbMap param) {
        PageHelper.startPage(pageIndex, pageSize);
        List<LbMap> list = feedbackDao.findAll(param);
        return new PageInfo(list);
    }

    @Override
    public Feedback findById(String feedbackId) {
        return feedbackDao.findById(feedbackId);
    }
}
