package cn.linbin.worklog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "TB_USER")
public class User implements Serializable {

    @TableId(value = "USER_ID", type = IdType.ID_WORKER_STR)
    private String userId;

    @TableField(value = "USER_NAME")
    private String username;

    @TableField(value = "TEL_NO")
    private String telNo;

    @TableField(value = "EMAIL")
    private String email;

    @TableField(value = "PASSWORD")
    private String password;

    @TableField(value = "DELETE_FLAG")
    private Integer deleteFlag;

    @TableField(value = "DELETE_DATE")
    private Date deleteDate;

    @TableField(value = "ROW_VERSION")
    private Integer rowVersion;

    @TableField(value = "CREATE_TIME")
    private Date createTime;

    /**
     * 用户类型：0-超级管理员，1-员工
     */
    @TableField(value = "USER_TYPE")
    private Integer userType;

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

    public Integer getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(Integer rowVersion) {
        this.rowVersion = rowVersion;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId='" + userId + '\'' +
                ", username='" + username + '\'' +
                ", telNo='" + telNo + '\'' +
                ", email='" + email + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", deleteDate=" + deleteDate +
                ", rowVersion=" + rowVersion +
                ", createTime=" + createTime +
                ", userType=" + userType +
                '}';
    }
}
