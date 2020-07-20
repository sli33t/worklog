package cn.linbin.worklog.controller.devTask;

import cn.linbin.worklog.controller.BaseController;
import cn.linbin.worklog.controller.customer.FeedbackController;
import cn.linbin.worklog.domain.Feedback;
import cn.linbin.worklog.service.customer.FeedbackService;
import cn.linbin.worklog.service.devTask.DevTaskService;
import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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
    public ModelAndView toDevTask(String feedbackId){
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
            System.out.println(feedbackId+"|"+developUserId+"|"+planHour+"|"+taskText);
            logger.info("开发任务分配成功");
            return LbMap.successResult("开发任务分配成功");
        }catch (Exception e){
            return LbMap.failResult("开发任务分配失败，"+e.getMessage());
        }
    }

}
