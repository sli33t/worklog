package cn.linbin.worklog.controller.system;

import cn.linbin.worklog.controller.BaseController;
import cn.linbin.worklog.domain.User;
import cn.linbin.worklog.service.system.SelectService;
import cn.linbin.worklog.service.user.UserService;
import cn.linbin.worklog.utils.LbMap;
import cn.linbin.worklog.utils.MD5Util;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping(value = "/select")
public class SelectController extends BaseController{

    @Autowired
    private SelectService selectService;

    @Autowired
    private UserService userService;

    private final static Logger logger = (Logger) LoggerFactory.getLogger(SelectController.class);

    @GetMapping(value = "/toSelectTable")
    public ModelAndView toSelectTable(String tableUrl){
        ModelAndView mv = new ModelAndView();
        try {
            mv.addObject("tableUrl", tableUrl);
            mv.setViewName("common/selectTable");
            logger.info("查询成功");
            return mv;
        }catch (Exception e){
            mv.setViewName("system/error");
            mv.addObject("errorMsg", e.getMessage());
            logger.info("查询失败");
            return mv;
        }
    }

    /**
     * 查询区域
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "/findArea")
    public LbMap findArea(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit){
        try {
            PageInfo<LbMap> pages = selectService.findArea(page, limit);
            logger.info("findArea查询成功");
            return LbMap.successResult("findArea查询成功", pages.getList(), pages.getTotal());
        }catch (Exception e){
            return LbMap.failResult("findArea查询失败，"+e.getMessage());
        }
    }


    /**
     * 查询用户
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "/findUser")
    public LbMap findUser(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit){
        try {
            PageInfo<LbMap> pages = selectService.findUser(page, limit);
            logger.info("findUser查询成功");
            return LbMap.successResult("findUser查询成功", pages.getList(), pages.getTotal());
        }catch (Exception e){
            return LbMap.failResult("findUser查询失败，"+e.getMessage());
        }
    }


    /**
     * 查询区域
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "/findCustomer")
    public LbMap findCustomer(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int limit){
        try {
            PageInfo<LbMap> pages = selectService.findCustomer(page, limit);
            logger.info("findCustomer查询成功");
            return LbMap.successResult("findCustomer查询成功", pages.getList(), pages.getTotal());
        }catch (Exception e){
            return LbMap.failResult("findCustomer查询失败，"+e.getMessage());
        }
    }

    /**
     * 跳转文件上传
     * @return
     */
    @GetMapping(value = "/toFileUpload")
    public ModelAndView toFileUpload(){
        ModelAndView mv = new ModelAndView();
        try {
            mv.setViewName("common/fileUpload");
            logger.info("查询成功");
            return mv;
        }catch (Exception e){
            mv.setViewName("system/error");
            mv.addObject("errorMsg", e.getMessage());
            logger.info("查询失败");
            return mv;
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
