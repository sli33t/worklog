package cn.linbin.worklog.controller;

import cn.linbin.worklog.domain.po.User;
import cn.linbin.worklog.service.analysis.AnalysisService;
import cn.linbin.worklog.service.devTask.DevTaskService;
import cn.linbin.worklog.service.testTask.TestTaskService;
import cn.linbin.worklog.service.user.UserService;
import cn.linbin.worklog.utils.LbMap;
import cn.linbin.worklog.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@RestController
public class IndexController extends BaseController {

    //private final static Logger logger = (Logger) LoggerFactory.getLogger(IndexController.class);

    @Autowired
    private DevTaskService devTaskService;

    @Autowired
    private TestTaskService testTaskService;

    @Autowired
    private UserService userService;

    @Autowired
    private AnalysisService analysisService;

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

    @GetMapping(value = "/getAnalysis")
    public LbMap getAnalysis(String jsonStr){
        try {

            LbMap resultMap = new LbMap();
            //开发总数，已开发数
            LbMap param = LbMap.fromObject(jsonStr);
            LbMap devMap = analysisService.queryDevCount(param);
            resultMap.put("devCount", devMap.getInt("devCount"));
            resultMap.put("devFinishCount", devMap.getInt("devFinishCount"));
            resultMap.put("devRate", devMap.getInt("devRate"));

            LbMap testMap = analysisService.queryTestCount(param);
            resultMap.put("testCount", testMap.getInt("testCount"));
            resultMap.put("testFinishCount", testMap.getInt("testFinishCount"));
            resultMap.put("testRate", testMap.getInt("testRate"));

            LbMap allMap = analysisService.queryAllCount(param);
            resultMap.put("allCount", allMap.getInt("allCount"));
            resultMap.put("allFinishCount", allMap.getInt("allFinishCount"));
            resultMap.put("allRate", allMap.getInt("allRate"));

            LbMap feedbackMap = analysisService.queryFeedbackList(param);
            for(Map.Entry<String, Object> entry: feedbackMap.entrySet()){
                resultMap.put(entry.getKey(), entry.getValue());
            }

            LbMap feedbackTypeMap = analysisService.queryFeedbackTypeList(param);
            for(Map.Entry<String, Object> entry: feedbackTypeMap.entrySet()){
                resultMap.put(entry.getKey(), entry.getValue());
            }

            LbMap priorityMap = analysisService.queryPriority(param);
            for(Map.Entry<String, Object> entry: priorityMap.entrySet()){
                resultMap.put(entry.getKey(), entry.getValue());
            }

            List<LbMap> versionList =  analysisService.queryVersionList(param);
            resultMap.put("versionList", versionList);

            resultMap.put("result", true);
            resultMap.put("msg", "");
            return resultMap;
        }catch (Exception e){
            return new LbMap();
        }
    }

    /**
     * 跳转修改密码
     * @return
     */
    @GetMapping(value = "/toChangePassword")
    public ModelAndView toChangePassword(){
        ModelAndView mv = new ModelAndView();
        try {
            mv.setViewName("system/change-password");
            logger.info("跳转成功");
            return mv;
        }catch (Exception e){
            mv.setViewName("system/error");
            mv.addObject("errorMsg", e.getMessage());
            logger.info("跳转失败");
            return mv;
        }
    }


    /**
     * 修改密码
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @PostMapping(value = "/changePassword")
    public LbMap changePassword(String oldPassword, String newPassword){
        try {
            if (oldPassword.equals("")||newPassword.equals("")){
                return LbMap.successResult("原密码或新密码不能为空");
            }

            String newMd5Password = MD5Util.md5(newPassword, user.getTelNo());
            String oldMd5Password = MD5Util.md5(oldPassword, user.getTelNo());

            if (!user.getPassword().equals(oldMd5Password)){
                return LbMap.failResult("原密码输入不正确");
            }

            User newUser = new User();
            newUser.setUserId(user.getUserId());
            newUser.setPassword(newMd5Password);
            userService.update(newUser, false);

            logger.info("密码修改成功");
            return LbMap.successResult("密码修改成功");
        }catch (Exception e){
            return LbMap.failResult("密码修改失败，"+e.getMessage());
        }
    }
}
