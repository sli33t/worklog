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
     * @param pageIndex
     * @param pageSize
     * @param jsonStr
     * @return
     */
    @GetMapping(value = "/findAll")
    public LbMap findAll(@RequestParam(defaultValue = "1") int pageIndex, @RequestParam(defaultValue = "10") int pageSize, @RequestParam(defaultValue = "") String jsonStr){
        try {
            LbMap param = LbMap.fromObject(jsonStr);
            PageInfo<LbMap> pages = testTaskService.findAll(pageIndex, pageSize, param);
            logger.info("查询成功");
            return LbMap.successResult("分配测试查询成功", pages.getList(), pages.getSize());
        }catch (Exception e){
            return LbMap.failResult("分配测试查询失败，"+e.getMessage());
        }
    }


    /**
     * 跳转测试分配
     * @return
     */
    @GetMapping(value = "/toTestTaskArrange")
    public ModelAndView toTestTaskArrange(String testTaskId, Integer feedbackId, String devTaskId){
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

            testTaskService.update(testTask);

            logger.info("更新成功");
            return LbMap.successResult("分配测试更新成功");
        }catch (Exception e){
            return LbMap.failResult("分配测试更新失败，"+e.getMessage());
        }
    }
}
