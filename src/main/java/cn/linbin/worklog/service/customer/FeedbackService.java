package cn.linbin.worklog.service.customer;

import cn.linbin.worklog.domain.Feedback;

public interface FeedbackService {

    /**
     * 编辑客户反馈单
     * @param feedback
     */
    void save(Feedback feedback) throws Exception;
}
