package cn.linbin.worklog.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "TB_ROLE")
public class Role implements Serializable{

    @TableId(value = "ROLE_ID", type = IdType.ID_WORKER_STR)
    private String roleId;

    @TableField(value = "ROLE_NAME")
    private String roleName;

    /**
     * 角色类型：0-管理，1-员工
     */
    @TableField(value = "ROLE_TYPE")
    private Integer roleType;

    @TableField(value = "ROW_VERSION")
    private Integer rowVersion;

    @TableField(value = "CREATE_TIME")
    private Date createTime;

    @TableField(value = "DELETE_FLAG")
    private Integer deleteFlag;

    @TableField(value = "DELETE_DATE")
    private Date deleteDate;

    public Integer getDeleteFlag() {
        return deleteFlag;
    }

    public void setDeleteFlag(Integer deleteFlag) {
        this.deleteFlag = deleteFlag;
    }

    public Date getDeleteDate() {
        return deleteDate;
    }

    public void setDeleteDate(Date deleteDate) {
        this.deleteDate = deleteDate;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Integer getRoleType() {
        return roleType;
    }

    public void setRoleType(Integer roleType) {
        this.roleType = roleType;
    }

    public Integer getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(Integer rowVersion) {
        this.rowVersion = rowVersion;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    @Override
    public String toString() {
        return "Role{" +
                "roleId='" + roleId + '\'' +
                ", roleName='" + roleName + '\'' +
                ", roleType=" + roleType +
                ", rowVersion=" + rowVersion +
                ", createTime=" + createTime +
                '}';
    }
}
