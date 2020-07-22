package cn.linbin.worklog.service.user.impl;

import cn.linbin.worklog.dao.UserDao;
import cn.linbin.worklog.domain.User;
import cn.linbin.worklog.service.user.UserService;
import cn.linbin.worklog.utils.LbMap;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
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
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 查询所有用户
     * @return
     */
    @Override
    public PageInfo<User> findAll(int page, int limit, LbMap param){
        PageHelper.startPage(page, limit);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("CREATE_TIME");
        wrapper.eq("DELETE_FLAG", 0);

        if (!param.getString("username").equals("")){
            wrapper.like("USER_NAME", param.getString("username"));
        }

        if (!param.getString("telNo").equals("")){
            wrapper.like("TEL_NO", param.getString("telNo"));
        }

        List<User> list = userDao.selectList(wrapper);
        return new PageInfo(list);
    }

    /**
     * 通过ID查找用户信息
     * @param userId
     * @return
     */
    @Override
    public User findById(String userId){
        return userDao.selectById(userId);
    }

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void save(User user) throws Exception{
        int count = userDao.insert(user);
        if (count<=0){
            throw new Exception("用户写入失败！");
        }
    }

    @Override
    public void update(User user) throws Exception {
        update(user, true);
    }

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(User user, boolean useRowVersion) throws Exception{
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("USER_ID", user.getUserId());
        if (useRowVersion){
            wrapper.eq("ROW_VERSION", user.getRowVersion());
            user.setRowVersion(user.getRowVersion()+1);
        }
        int count = userDao.update(user, wrapper);
        if (count<=0){
            throw new Exception("用户更新失败，数据已被他人修改，请重新刷新！");
        }
    }

    /**
     * 删除用户信息
     * @param userId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String userId, String rowVersion) throws Exception{
        User user = new User();
        user.setDeleteFlag(1);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        user.setDeleteDate(dateFormat.parse(dateFormat.format(new Date())));

        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("USER_ID", userId);
        wrapper.eq("ROW_VERSION", rowVersion);
        user.setRowVersion(Integer.valueOf(rowVersion)+1);

        int count = userDao.update(user, wrapper);
        if (count<=0){
            throw new Exception("用户删除失败，请刷新数据！");
        }
    }

    /**
     * 检查用户信息
     * @param telNo
     * @param username
     * @return
     */
    @Override
    public List<User> checkUserInfo(String telNo, String username) {
        List<User> list = userDao.checkUserInfo(telNo, username);
        if (list!=null&&list.size()>0){
            return list;
        }else {
            return new ArrayList<>();
        }
    }

    /**
     * 通过岗位查询用户，用于分配用户时回显
     * @param page
     * @param limit
     * @param roleId
     * @return
     */
    @Override
    public PageInfo<LbMap> findByRoleId(int page, int limit, String roleId) {
        PageHelper.startPage(page, limit);
        List<LbMap> list = userDao.findByRoleId(roleId);
        return new PageInfo(list);
    }

    /**
     * 通过用户ID查询角色
     * @param userId
     * @return
     */
    @Override
    public List<LbMap> findRoleByUserId(String userId) {
        return userDao.findRoleByUserId(userId);
    }
}
