package cn.linbin.worklog.controller;

import cn.linbin.worklog.domain.User;
import cn.linbin.worklog.service.devTask.DevTaskService;
import cn.linbin.worklog.utils.LbMap;
import cn.linbin.worklog.utils.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

/**
 * 登录控制
 */
@RestController
public class LoginController {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private DevTaskService devTaskService;

    /**
     * 跳转登录页面
     * @return
     */
    @GetMapping(value = "/")
    public ModelAndView toLogin(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("system/login");
        return mv;
    }

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

        mv.setViewName("system/index");
        mv.addObject("user", user);
        mv.addObject("devCount", devCount);
        return mv;
    }

    /**
     * 登录方法
     * @param telNo
     * @param password
     * @param session
     * @return
     */
    @PostMapping(value = "/login")
    public LbMap login(String telNo, String password, HttpSession session) {
        if (StringUtils.isEmpty(telNo)||StringUtils.isEmpty(password)){
            return LbMap.failResult("用户名或者密码不能为空！");
        }

        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(telNo, password);
            subject.login(token);
            User user = (User) subject.getPrincipal();
            if (user!=null){
                session.setAttribute("user", user);
                logger.info("登录成功："+user.toString());
                return LbMap.successResult("");
            }else {
                logger.info("登录失败："+token.getUsername()+"，密码："+ MD5Util.md5(password));
                return LbMap.failResult("用户名不存在");
            }
        }catch (Exception e){
            logger.info("登录失败："+e.getMessage());
            return LbMap.failResult("登录失败："+e.getMessage());
        }
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

    /**
     * 登出方法
     * @param session
     * @return
     */
    @GetMapping(value = "/logout")
    public ModelAndView logout(HttpSession session){
        ModelAndView mv = new ModelAndView();
        session.removeAttribute("user");
        mv.setViewName("system/login");
        return mv;
    }
}
