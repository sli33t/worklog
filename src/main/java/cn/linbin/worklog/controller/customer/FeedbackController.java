package cn.linbin.worklog.controller.customer;

import cn.linbin.worklog.constant.FeedbackConstant;
import cn.linbin.worklog.controller.BaseController;
import cn.linbin.worklog.domain.Feedback;
import cn.linbin.worklog.service.customer.FeedbackService;
import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
@RequestMapping(value = "/feedback")
public class FeedbackController extends BaseController {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private FeedbackService feedbackService;

    /**
     * 跳转列表页面
     * @return
     */
    @GetMapping(value = "/toFeedback")
    public ModelAndView toFeedback(){
        ModelAndView mv = new ModelAndView();
        Feedback feedback = new Feedback();
        mv.addObject("feedback", feedback);
        mv.setViewName("customer/customer-feedback");
        return mv;
    }


    /**
     * 新增和修改
     * @param feedback
     * @return
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public LbMap edit(Feedback feedback) {
        try {
            if (feedback.getCustomerId().equals("")){
                return LbMap.failResult("客反编辑失败，客户名称不能为空");
            }else if (feedback.getProblemText().equals("")){
                return LbMap.failResult("客反编辑失败，反馈内容不能为空");
            }else if (feedback.getProblemType()==null){
                return LbMap.failResult("客反编辑失败，问题类型不能为空，请选择需求还是bug");
            }else if (feedback.getFeedbackType()==null){
                return LbMap.failResult("客反编辑失败，反馈类型不能为空，请选择内部反馈还是客户反馈");
            }else if (feedback.getRequireDate()==null){
                return LbMap.failResult("客反编辑失败，要求完成日期不能为空");
            }else if (feedback.getPriority()==null){
                return LbMap.failResult("客反编辑失败，优先级别不能为空");
            }

            int code;

            //新增
            if (StringUtils.isEmpty(feedback.getFeedbackId())){
                //初始化的行版本号为0
                feedback.setCreateUserId(userId);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                feedback.setCreateTime(dateFormat.parse(dateFormat.format(new Date())));

                feedback.setRowVersion(0);
                feedback.setStatus(FeedbackConstant.FEEDBACK_STATUS_1);

                //ID为空的为新增
                feedbackService.save(feedback);
                code = 100;
            }else {
                //ID不为空的为修改
                feedback.setModifyUserId(userId);

                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                feedback.setModifyTime(dateFormat.parse(dateFormat.format(new Date())));

                feedbackService.update(feedback);
                code = 200;
            }

            return LbMap.successResult("客户编辑成功", code, 0);
        }catch (Exception e){
            logger.info("客户编辑失败："+e.getMessage());
            return LbMap.failResult("客户编辑失败，"+e.getMessage());
        }
    }


    /**
     * 跳转列表页面
     * @return
     */
    @GetMapping(value = "/toFeedbackList")
    public ModelAndView toFeedbackList(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("customer/customer-feedback-list");
        return mv;
    }

    /**
     * 查询所有客户反馈单
     * @param pageIndex
     * @param pageSize
     * @param jsonStr
     * @return
     */
    @GetMapping(value = "/findAll")
    public LbMap findAll(@RequestParam(defaultValue = "1") int pageIndex, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "") String jsonStr){
        try {
            LbMap map = LbMap.fromObject(jsonStr);
            PageInfo<LbMap> pages = feedbackService.findAll(pageIndex, pageSize, map);
            logger.info("查询成功");
            return LbMap.successResult("客户反馈单查询成功", pages.getList(), pages.getSize());
        }catch (Exception e){
            return LbMap.failResult("客户反馈单查询失败，"+e.getMessage());
        }
    }


    /**
     * 跳转更新页面
     * @param feedbackId
     * @return
     */
    @GetMapping(value = "/toUpdate")
    public ModelAndView toUpdate(Integer feedbackId){
        ModelAndView mv = new ModelAndView();
        Feedback feedback = feedbackService.findById(feedbackId);
        logger.info(feedback.toString());
        mv.setViewName("customer/customer-feedback");
        mv.addObject("feedback", feedback);
        return mv;
    }
}
