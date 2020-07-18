package cn.linbin.worklog.controller.role;

import cn.linbin.worklog.controller.BaseController;
import cn.linbin.worklog.domain.Role;
import cn.linbin.worklog.service.role.RoleService;
import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "/role")
public class RoleController extends BaseController {

    private final static Logger logger = (Logger) LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;


    /**
     * 跳转列表页面
     * @return
     */
    @GetMapping(value = "/toRoleList")
    public ModelAndView toRoleList(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("role/role-list");
        return mv;
    }


    /**
     * 查询所有岗位信息
     * @return
     */
    @GetMapping(value = "/findAll")
    public LbMap findAll(@RequestParam(defaultValue = "1") int pageIndex, @RequestParam(defaultValue = "10") int pageSize,  @RequestParam(defaultValue = "") String jsonStr){
        try {
            LbMap map = LbMap.fromObject(jsonStr);
            logger.info(jsonStr + "||" + map.toString());
            PageInfo<Role> pages = roleService.findAll(pageIndex, pageSize, map);
            logger.info("查询成功");
            return LbMap.successResult("岗位查询成功", pages.getList(), pages.getSize());
        }catch (Exception e){
            return LbMap.failResult("岗位查询失败，"+e.getMessage());
        }
    }


    /**
     * 跳转新增页面
     * @return
     */
    @GetMapping(value = "/toAdd")
    public ModelAndView toAdd(){
        ModelAndView mv = new ModelAndView();
        mv.setViewName("role/role-add");
        return mv;
    }

    /**
     * 通过编号查询岗位信息
     * @param roleId
     * @return
     */
    @GetMapping(value = "/toUpdate")
    public ModelAndView toUpdate(String roleId){
        ModelAndView mv = new ModelAndView();
        try {
            if (roleId.equals("")){
                throw new Exception("没有找到岗位编号");
            }

            Role role = roleService.findById(roleId);
            mv.addObject("role", role);
            mv.setViewName("role/role-update");
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
     * 新增和修改
     * @param role
     * @return
     */
    @PostMapping(value = "/edit")
    public LbMap edit(Role role) {
        try {
            if (role.getRoleName().equals("")){
                LbMap.failResult("岗位编辑失败，岗位名称不能为空！");
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            role.setCreateTime(dateFormat.parse(dateFormat.format(new Date())));

            //新增
            if (StringUtils.isEmpty(role.getRoleId())){
                List<Role> roleList = roleService.checkRoleInfo(role.getRoleName());

                if (roleList!=null&&roleList.size()>0){
                    for (Role oldRole : roleList) {
                        if (oldRole.getRoleName().equals(role.getRoleName())){
                            return LbMap.failResult("岗位名称已经存在");
                        }
                    }
                }

                //初始化的行版本号为0
                role.setRowVersion(0);
                role.setDeleteFlag(0);
                //ID为空的为新增
                roleService.save(role);
            }else {
                //ID不为空的为修改
                roleService.update(role);
            }

            return LbMap.successResult("岗位编辑成功");
        }catch (Exception e){
            logger.info("岗位编辑失败："+e.getMessage());
            return LbMap.failResult("岗位编辑失败，"+e.getMessage());
        }
    }

    /**
     * 删除
     * @param roleId
     * @return
     */
    @PostMapping(value = "/delete")
    public LbMap delete(String roleId, String rowVersion){
        try {
            if (roleId.equals("")){
                return LbMap.failResult("岗位删除失败，没有找到岗位编号！");
            }
            roleService.delete(roleId, rowVersion);
            return LbMap.successResult("岗位删除成功");
        }catch (Exception e){
            return LbMap.failResult("岗位删除失败，"+e.getMessage());
        }
    }

    /**
     * 跳转分配用户页面
     * @param roleId
     * @return
     */
    @GetMapping(value = "/toUserRole")
    public ModelAndView toUserRole(String roleId){
        ModelAndView mv = new ModelAndView();
        try {
            if (roleId.equals("")){
                throw new Exception("没有找到岗位编号");
            }

            Role role = roleService.findById(roleId);
            mv.addObject("role", role);
            mv.setViewName("role/role-userRole");
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
     * 保存分配用户
     * @param jsonStr
     * @return
     */
    @PostMapping(value = "/updateUserRole")
    public LbMap updateUserRole(String jsonStr){
        LbMap map = LbMap.fromObject(jsonStr);
        if (map.size()==0){
            return LbMap.failResult("没有选择用户信息");
        }

        try {
            return roleService.updateUserRole(map);
        }catch (Exception e){
            return LbMap.failResult("用户分配失败，"+e.getMessage());
        }
    }

}
