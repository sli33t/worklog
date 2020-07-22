package cn.linbin.worklog.controller;

import cn.linbin.worklog.domain.User;
import cn.linbin.worklog.service.devTask.DevTaskService;
import cn.linbin.worklog.service.testTask.TestTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

@RestController
public class IndexController extends BaseController {

    @Autowired
    private DevTaskService devTaskService;

    @Autowired
    private TestTaskService testTaskService;

    /**
     * 跳转首页
     * @param session
     * @return
     */
    @GetMapping(value = "/toIndex")
    public ModelAndView toIndex(HttpSession session){
        ModelAndView mv = new ModelAndView();
        User user = (User) session.getAttribute("user");

        int devCount = devTaskService.findDevFinishCount(user.getUserId());

        int testCount = testTaskService.findTestFinishCount(user.getUserId());

        mv.setViewName("system/index");
        mv.addObject("user", user);
        mv.addObject("devCount", devCount);
        mv.addObject("testCount", testCount);
        return mv;
    }

    /**
     * 正文部分跳转
     * @return
     */
    @GetMapping(value = "/home")
    public ModelAndView toHome(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("system/home");
        return mv;
    }
}
