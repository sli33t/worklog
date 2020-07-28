package cn.linbin.worklog.service.role.impl;

import cn.linbin.worklog.dao.RoleDao;
import cn.linbin.worklog.domain.po.Role;
import cn.linbin.worklog.service.role.RoleService;
import cn.linbin.worklog.utils.LbMap;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    /**
     * 查询所有岗位
     * @return
     */
    @Override
    public PageInfo<Role> findAll(int PageIndex, int PageSize, LbMap param){
        PageHelper.startPage(PageIndex, PageSize);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("CREATE_TIME");
        wrapper.eq("DELETE_FLAG", 0);

        if (!param.getString("roleName").equals("")){
            wrapper.like("ROLE_NAME", param.getString("roleName"));
        }

        if (!param.getString("telNo").equals("")){
            wrapper.like("TEL_NO", param.getString("telNo"));
        }

        List<Role> list = roleDao.selectList(wrapper);
        return new PageInfo(list);
    }

    /**
     * 通过ID查找岗位信息
     * @param roleId
     * @return
     */
    @Override
    public Role findById(String roleId){
        return roleDao.selectById(roleId);
    }

    /**
     * 保存岗位信息
     * @param role
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(Role role) throws Exception{
        int count = roleDao.insert(role);
        if (count<=0){
            throw new Exception("岗位写入失败！");
        }
    }

    /**
     * 更新岗位信息
     * @param role
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Role role) throws Exception{
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("ROLE_ID", role.getRoleId());
        wrapper.eq("ROW_VERSION", role.getRowVersion());
        role.setRowVersion(role.getRowVersion()+1);
        int count = roleDao.update(role, wrapper);
        if (count<=0){
            throw new Exception("岗位更新失败，数据已被他人修改，请重新刷新！");
        }
    }

    /**
     * 删除岗位信息
     * @param roleId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String roleId, String rowVersion) throws Exception{

        Role role = new Role();
        role.setDeleteFlag(1);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        role.setDeleteDate(dateFormat.parse(dateFormat.format(new Date())));

        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("ROLE_ID", roleId);
        wrapper.eq("ROW_VERSION", rowVersion);
        role.setRowVersion(Integer.valueOf(rowVersion)+1);

        int count = roleDao.update(role, wrapper);
        if (count<=0){
            throw new Exception("岗位删除失败，请刷新数据！");
        }
    }

    /**
     * 检查岗位信息
     * @param roleName
     * @return
     */
    @Override
    public List<Role> checkRoleInfo(String roleName) {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.eq("ROLE_NAME", roleName);
        List<Role> list = roleDao.selectList(wrapper);
        if (list!=null&&list.size()>0){
            return list;
        }else {
            return new ArrayList<>();
        }
    }

    /**
     * 用户分配
     * @param map
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public LbMap updateUserRole(LbMap map) {
        String roleId = map.getString("roleId");
        List<String> userIds = (List<String>) map.get("userIds");

        if (userIds==null||userIds.size()==0){
            return LbMap.failResult("没有选择用户信息");
        }

        //先删除
        int delCount = roleDao.deleteUserRole(roleId);
        if (delCount<0){
            return LbMap.failResult("用户岗位删除失败");
        }

        //再新增
        for (String userId : userIds) {
            String id = IdWorker.getIdStr();
            int insCount = roleDao.insertUserRole(id, userId, roleId);
            if (insCount!=1){
                return LbMap.failResult("用户岗位写入失败");
            }
        }

        return LbMap.successResult("用户岗位写入成功");
    }
}
