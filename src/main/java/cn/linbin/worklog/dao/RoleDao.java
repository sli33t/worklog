package cn.linbin.worklog.dao;

import cn.linbin.worklog.domain.po.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleDao extends BaseMapper<Role>{

    @Insert("INSERT INTO TB_USER_ROLE (ID, USER_ID, ROLE_ID) VALUES (#{id}, #{userId}, #{roleId})")
    int insertUserRole(@Param("id") String id, @Param("userId") String userId, @Param("roleId") String roleId);

    @Delete("DELETE FROM TB_USER_ROLE WHERE ROLE_ID = #{roleId}")
    int deleteUserRole(String roleId);
}
