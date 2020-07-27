package cn.linbin.worklog.controller;

import cn.linbin.worklog.common.LoginException;
import cn.linbin.worklog.domain.User;
import cn.linbin.worklog.service.user.UserService;
import cn.linbin.worklog.utils.LbMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class BaseController {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(BaseController.class);

    protected HttpServletRequest request;
    protected HttpServletResponse response;
    protected HttpSession session;

    protected String userId;
    protected String username;
    protected String telNo;
    protected List<LbMap> roleList;
    protected Integer roleType;
    protected User user;

    @Autowired
    private UserService userService;

    @ModelAttribute
    public void init(HttpServletRequest request, HttpServletResponse response, HttpSession session){
        this.request = request;
        this.response = response;
        this.session = session;

        logger.info("请求的地址："+request.getContextPath()+"||"+request.getServletPath()+"||"+request.getMethod());

        User user = (User) session.getAttribute("user");
        if (user==null){
            throw new LoginException("用户没有登录");
        }else{
            this.userId = user.getUserId();
            this.username = user.getUsername();
            this.telNo = user.getTelNo();
            this.user = user;

            if (this.roleList==null||this.roleList.size()==0){
                this.roleList = userService.findRoleByUserId(this.userId);
            }

            roleType = 1;
            if (roleList!=null&&roleList.size()>0){
                for (LbMap role : roleList) {
                    if (role.getInt("roleType")==0){
                        roleType = 0;
                    }
                }
            }
        }
    }
}
