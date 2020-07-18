package cn.linbin.worklog.controller;

import cn.linbin.worklog.common.LoginException;
import cn.linbin.worklog.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class BaseController {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(BaseController.class);

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    protected String userId;
    protected String username;
    protected String telNo;

    @ModelAttribute
    public void init(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        this.request = request;
        this.response = response;
        this.session = session;

        logger.info("请求的地址："+request.getContextPath()+"||"+request.getServletPath());

        User user = (User) session.getAttribute("user");
        if (user==null){
            throw new LoginException("用户没有登录");
        }else{
            this.userId = user.getUserId();
            this.username = user.getUsername();
            this.telNo = user.getTelNo();
        }
    }
}
