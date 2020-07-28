package cn.linbin.worklog.service.user;

import cn.linbin.worklog.domain.po.User;
import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface UserService {

    /**
     * 查询所有用户
     * @return
     */
    PageInfo<User> findAll(int page, int limit, LbMap param);

    /**
     * 通过ID查找用户信息
     * @param userId
     * @return
     */
    User findById(String userId);

    /**
     * 保存用户信息
     * @param user
     * @return
     */
    void save(User user) throws Exception;

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    void update(User user) throws Exception;

    void update(User user, boolean useRowVersion) throws Exception;

    /**
     * 删除用户信息
     * @param userId
     * @return
     */
    void delete(String userId, String rowVersion) throws Exception;

    /**
     * 检查用户信息
     * @param telNo
     * @param username
     * @return
     */
    List<User> checkUserInfo(String telNo, String username);

    /**
     * 通过岗位查询用户，用于分配用户时回显
     * @param page
     * @param limit
     * @param roleId
     * @return
     */
    PageInfo<LbMap> findByRoleId(int page, int limit, String roleId);

    /**
     * 通过用户查岗位
     * @param userId
     * @return
     */
    List<LbMap> findRoleByUserId(String userId);;
}
