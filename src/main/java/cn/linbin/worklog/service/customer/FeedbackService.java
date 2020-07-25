package cn.linbin.worklog.service.customer;

import cn.linbin.worklog.domain.Feedback;
import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageInfo;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FeedbackService {

    /**
     * 编辑客户反馈单
     * @param feedback
     */
    void save(Feedback feedback) throws Exception;

    /**
     * 更新客户反馈单
     * @param feedback
     */
    void update(Feedback feedback) throws Exception;

    /**
     * 查询客户反馈单列表
     * @param page
     * @param limit
     * @param param
     * @return
     */
    PageInfo<LbMap> findAll(int page, int limit, LbMap param);

    /**
     * 通过ID查询客户反馈单
     * @param feedbackId
     * @return
     */
    Feedback findById(Integer feedbackId) throws Exception;

    /**
     * 更新状态
     * @param feedbackId
     * @param status
     * @return
     */
    int updateStatus(Integer feedbackId, Integer status);

    /**
     * EXCEL导入客户反馈单
     * @param file
     */
    LbMap excelToFeedback(MultipartFile file, String userId) throws Exception;
}
