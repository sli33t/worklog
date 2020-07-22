package cn.linbin.worklog.controller.testTask;

import cn.linbin.worklog.controller.BaseController;
import cn.linbin.worklog.domain.Feedback;
import cn.linbin.worklog.domain.TestTask;
import cn.linbin.worklog.service.customer.FeedbackService;
import cn.linbin.worklog.service.testTask.TestTaskService;
import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/testTask")
public class TestTaskController extends BaseController{

    private final static Logger logger = (Logger) LoggerFactory.getLogger(TestTaskController.class);

    @Autowired
    private TestTaskService testTaskService;

    @Autowired
    private FeedbackService feedbackService;

    /**
     * 跳转列表页面
     * @return
     */
    @GetMapping(value = "/toTestTaskList")
    public ModelAndView toTestTaskList(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("testTask/testTask-list");
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
            LbMap param = LbMap.fromObject(jsonStr);
            PageInfo<LbMap> pages = testTaskService.findAll(page, limit, param);
            logger.info("查询成功");
            return LbMap.successResult("分配测试查询成功", pages.getList(), pages.getTotal());
        }catch (Exception e){
            return LbMap.failResult("分配测试查询失败，"+e.getMessage());
        }
    }


    /**
     * 跳转测试分配
     * @return
     */
    @GetMapping(value = "/toTestTaskArrange")
    public ModelAndView toTestTaskArrange(String testTaskId, Integer feedbackId, String devTaskId) throws Exception {
        ModelAndView mv = new ModelAndView();
        Feedback feedback = feedbackService.findById(feedbackId);
        mv.setViewName("testTask/testTask-add");
        mv.addObject("feedback", feedback);
        mv.addObject("testTaskId", testTaskId);
        mv.addObject("devTaskId", devTaskId);
        return mv;

    }


    /**
     * 分配保存
     * @param testTask
     * @return
     */
    @PostMapping(value = "/edit")
    public LbMap edit(TestTask testTask){
        try {
            if (StringUtils.isEmpty(testTask.getTesttaskId())){
                return LbMap.failResult("分配测试失败，没有找到测试编号");
            }

            if (StringUtils.isEmpty(testTask.getDevtaskId())){
                return LbMap.failResult("分配测试失败，没有找到开发编号");
            }

            if (StringUtils.isEmpty(testTask.getTestUserId())){
                return LbMap.failResult("分配测试失败，没有找到测试人员，请刷新数据重新分配");
            }

            if (StringUtils.isEmpty(testTask.getFeedbackId())){
                return LbMap.failResult("分配测试失败，没有找到客反编号");
            }

            testTaskService.update(testTask);

            logger.info("更新成功");
            return LbMap.successResult("分配测试更新成功");
        }catch (Exception e){
            return LbMap.failResult("分配测试更新失败，"+e.getMessage());
        }
    }

    /**
     * 跳转列表页面
     * @return
     */
    @GetMapping(value = "/toTestFinish")
    public ModelAndView toTestFinish(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("testTask/testTask-finish");
        return mv;
    }

    /**
     * 查询测试完成列表
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "/findTestFinishList")
    public LbMap findTestFinishList(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit){
        try {
            LbMap param = new LbMap();
            param.put("testUserId", userId);
            PageInfo<LbMap> pages = testTaskService.findDevFinish(page, limit, param);
            logger.info("查询成功");
            return LbMap.successResult("测试完成查询成功", pages.getList(), pages.getTotal());
        }catch (Exception e){
            return LbMap.failResult("测试完成查询失败，"+e.getMessage());
        }
    }

    /**
     * 测试退回
     * @param testtaskId
     * @param feedbackId
     * @return
     */
    @PostMapping(value = "/updateTestBack")
    public LbMap updateTestBack(String testtaskId, Integer feedbackId){
        try {
            if (StringUtils.isEmpty(testtaskId)){
                return LbMap.failResult("测试退回失败，没有找到测试任务编号");
            }

            if (feedbackId==null||feedbackId<=0){
                return LbMap.failResult("开发退回失败，没有找到客反单号");
            }

            testTaskService.updateTestBack(testtaskId, feedbackId);
            logger.info("开发退回成功");
            return LbMap.successResult("开发退回成功");
        }catch (Exception e){
            return LbMap.failResult("开发退回失败，"+e.getMessage());
        }
    }
}
