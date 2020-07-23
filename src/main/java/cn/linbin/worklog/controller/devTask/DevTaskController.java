package cn.linbin.worklog.controller.devTask;

import cn.linbin.worklog.constant.MQConstant;
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
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/devTask")
public class DevTaskController extends BaseController{

    private final static Logger logger = (Logger) LoggerFactory.getLogger(FeedbackController.class);

    @Autowired
    private DevTaskService devTaskService;

    @Autowired
    private FeedbackService feedbackService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

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
     * @param page
     * @param limit
     * @param jsonStr
     * @return
     */
    @GetMapping(value = "/findAll")
    public LbMap findAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit, @RequestParam(defaultValue = "") String jsonStr){
        try {
            LbMap map = LbMap.fromObject(jsonStr);
            PageInfo<LbMap> pages = devTaskService.findAll(page, limit, map);
            logger.info("查询成功");
            return LbMap.successResult("分配开发查询成功", pages.getList(), pages.getTotal());
        }catch (Exception e){
            return LbMap.failResult("分配开发查询失败，"+e.getMessage());
        }
    }

    /**
     * 跳转列表页面
     * @return
     */
    @GetMapping(value = "/toDevTask")
    public ModelAndView toDevTask(Integer feedbackId) throws Exception {
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

            List<LbMap> list = devTaskService.checkDevTask(feedbackId);
            if (list!=null&&list.size()>0){
                return LbMap.failResult("当前客反"+feedbackId+"已经分配过开发任务，请刷新数据");
            }

            //新增
            DevTask devTask = new DevTask();
            devTask.setFeedbackId(feedbackId);
            devTask.setDevelopUserId(developUserId);
            devTask.setPlanHour(planHour);
            devTask.setRealHour(0.0d);
            devTask.setTaskText(taskText);

            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = dateTimeFormat.parse(dateTimeFormat.format(new Date()));
            devTask.setTaskTime(now);

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            devTask.setTaskDate(dateFormat.parse(dateFormat.format(new Date())));

            Feedback feedback = feedbackService.findById(feedbackId);

            devTask.setCreateUserId(userId);
            devTask.setFeedbackTime(feedback.getCreateTime());

            devTaskService.edit(devTask);

            LbMap queueMap = new LbMap();
            queueMap.put("customerName", feedback.getCustomerName());
            queueMap.put("version", feedback.getVersionName());
            queueMap.put("feedbackId", feedbackId);
            queueMap.put("developUserId", developUserId);
            queueMap.put("taskText", taskText);
            queueMap.put("requireDate", feedback.getRequireDate());
            queueMap.put("now", now);
            queueMap.put("developer", feedback.getDeveloper());
            rabbitTemplate.convertAndSend(MQConstant.DEVELOP_EXCHANGE, MQConstant.DEVELOP_KEY, queueMap.toString());

            logger.info("开发任务分配成功");
            return LbMap.successResult("开发任务分配成功");
        }catch (Exception e){
            return LbMap.failResult("开发任务分配失败，"+e.getMessage());
        }
    }


    /**
     * 跳转列表页面
     * @return
     */
    @GetMapping(value = "/toDevFinish")
    public ModelAndView toDevFinish(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("devTask/devTask-finish");
        return mv;
    }


    /**
     * 开发完成列表
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "/findDevFinishList")
    public LbMap findDevFinishList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit){
        try {
            LbMap param = new LbMap();
            param.put("developUserId", userId);
            PageInfo<LbMap> pages = devTaskService.findDevFinish(page, limit, param);
            logger.info("查询成功");
            return LbMap.successResult("开发完成查询成功", pages.getList(), pages.getTotal());
        }catch (Exception e){
            return LbMap.failResult("开发完成查询失败，"+e.getMessage());
        }
    }


    /**
     * 查询开发数量，在首页显示
     * @return
     */
    @GetMapping(value = "/findDevFinishCount")
    public LbMap findDevFinishCount(){
        try {
            int count = devTaskService.findDevFinishCount(userId);
            logger.info("查询成功");
            return LbMap.successResult("分配开发查询成功", count);
        }catch (Exception e){
            return LbMap.failResult("分配开发查询失败，"+e.getMessage());
        }
    }


    /**
     * 更新开发完成
     * @param devTask
     * @return
     */
    @PostMapping(value = "/updateDevFinish")
    public LbMap updateDevFinish(DevTask devTask){
        try {
            if (devTask.getIsProblemText()!=null&&devTask.getIsProblemText().toLowerCase().equals("on")){
                devTask.setIsProblem(1); //是问题
            }else {
                devTask.setIsProblem(0); //不是问题
            }

            if (devTask.getNeedTestText()!=null&&devTask.getNeedTestText().toLowerCase().equals("on")){
                devTask.setNeedTest(1); //需要测试
            }else {
                devTask.setNeedTest(0); //不需要测试
            }

            logger.info(devTask.toString());
            devTaskService.updateDevFinish(devTask);
            logger.info("开发完成成功");
            return LbMap.successResult("开发完成成功");
        }catch (Exception e){
            return LbMap.failResult("开发完成失败，"+e.getMessage());
        }
    }


    /**
     * 更新开发回退
     * @param devtaskId
     * @param feedbackId
     * @return
     */
    @PostMapping(value = "/updateDevBack")
    public LbMap updateDevBack(String devtaskId, Integer feedbackId){
        try {
            if (StringUtils.isEmpty(devtaskId)){
                return LbMap.failResult("开发退回失败，没有找到开发任务编号");
            }

            if (feedbackId==null||feedbackId<=0){
                return LbMap.failResult("开发退回失败，没有找到客反单号");
            }

            devTaskService.updateDevBack(devtaskId, feedbackId);
            logger.info("开发退回成功");
            return LbMap.successResult("开发退回成功");
        }catch (Exception e){
            return LbMap.failResult("开发退回失败，"+e.getMessage());
        }
    }

}
