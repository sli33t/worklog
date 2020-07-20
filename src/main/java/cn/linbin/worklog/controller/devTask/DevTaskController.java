package cn.linbin.worklog.controller.devTask;

import cn.linbin.worklog.controller.BaseController;
import cn.linbin.worklog.controller.customer.FeedbackController;
import cn.linbin.worklog.domain.DevTask;
import cn.linbin.worklog.domain.Feedback;
import cn.linbin.worklog.service.customer.FeedbackService;
import cn.linbin.worklog.service.devTask.DevTaskService;
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
@RequestMapping(value = "/devTask")
public class DevTaskController extends BaseController{

    private final static Logger logger = (Logger) LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private DevTaskService devTaskService;

    @Autowired
    private FeedbackService feedbackService;

    /**
     * 跳转列表页面
     * @return
     */
    @GetMapping(value = "/toDevTaskList")
    public ModelAndView toDevTaskList(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("devTask/devTask-list");
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
            PageInfo<LbMap> pages = devTaskService.findAll(pageIndex, pageSize, map);
            logger.info("查询成功");
            return LbMap.successResult("分配开发查询成功", pages.getList(), pages.getSize());
        }catch (Exception e){
            return LbMap.failResult("分配开发查询失败，"+e.getMessage());
        }
    }

    /**
     * 跳转列表页面
     * @return
     */
    @GetMapping(value = "/toDevTask")
    public ModelAndView toDevTask(Integer feedbackId){
        ModelAndView mv = new ModelAndView();
        Feedback feedback = feedbackService.findById(feedbackId);
        mv.addObject("feedback", feedback);
        mv.setViewName("devTask/devTask-add");
        return mv;
    }


    /**
     * 分配开发任务
     * @param feedbackId
     * @param developUserId
     * @param planHour
     * @param taskText
     * @return
     */
    @PostMapping(value = "/edit")
    public LbMap edit(Integer feedbackId, String developUserId, Double planHour, String taskText){
        try {
            if (feedbackId<=0){
                return LbMap.failResult("分配开发任务失败，没有找到客反单号，请刷新重试！");
            }else if (StringUtils.isEmpty(developUserId)){
                return LbMap.failResult("分配开发任务失败，没有找到开发人员！");
            }else if (planHour<=0){
                return LbMap.failResult("分配开发任务失败，预计开发工时不能小于0或等于0！");
            }

            //新增
            DevTask devTask = new DevTask();
            devTask.setFeedbackId(feedbackId);
            devTask.setDevelopUserId(developUserId);
            devTask.setPlanHour(planHour);
            devTask.setTaskText(taskText);

            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            devTask.setTaskTime(dateTimeFormat.parse(dateTimeFormat.format(new Date())));

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            devTask.setTaskDate(dateFormat.parse(dateFormat.format(new Date())));

            Feedback feedback = feedbackService.findById(feedbackId);

            devTask.setCreateUserId(userId);
            devTask.setFeedbackTime(feedback.getCreateTime());

            devTaskService.edit(devTask);

            logger.info("开发任务分配成功");
            return LbMap.successResult("开发任务分配成功");
        }catch (Exception e){
            return LbMap.failResult("开发任务分配失败，"+e.getMessage());
        }
    }

}
