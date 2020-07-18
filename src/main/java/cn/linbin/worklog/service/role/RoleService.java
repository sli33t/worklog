package cn.linbin.worklog.service.role;

import cn.linbin.worklog.domain.Role;
import cn.linbin.worklog.utils.LbMap;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RoleService {

    /**
     * 查询所有岗位
     * @return
     */
    PageInfo<Role> findAll(int PageIndex, int PageSize, LbMap param);

    /**
     * 通过ID查找岗位信息
     * @param roleId
     * @return
     */
    Role findById(String roleId);

    /**
     * 保存岗位信息
     * @param role
     * @return
     */
    void save(Role role) throws Exception;

    /**
     * 更新岗位信息
     * @param role
     * @return
     */
    void update(Role role) throws Exception;

    /**
     * 删除岗位信息
     * @param roleId
     * @return
     */
    void delete(String roleId, String rowVersion) throws Exception;

    /**
     * 检查岗位信息
     * @param roleName
     * @return
     */
    List<Role> checkRoleInfo(String roleName);

    /**
     * 分配用户
     * @param map
     * @return
     */
    LbMap updateUserRole(LbMap map);
}
