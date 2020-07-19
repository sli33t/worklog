package cn.linbin.worklog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "TB_FEEDBACK")
public class Feedback implements Serializable {

    @TableId(value = "FEEDBACK_ID", type = IdType.AUTO)
    private String feedbackId;

    @TableField(value = "PROBLEM_TYPE")
    private Integer problemType;

    @TableField(value = "PROBLEM_TEXT")
    private String problemText;

    @TableField(value = "CREATE_USER_ID")
    private String createUserId;

    @TableField(value = "CUSTOMER_ID")
    private String customerId;

    @TableField(value = "CREATE_TIME")
    private Date createTime;

    @TableField(value = "MODIFY_USER_ID")
    private String modifyUserId;

    @TableField(value = "MODIFY_TIME")
    private Date modifyTime;

    @TableField(value = "ROW_VERSION")
    private Integer rowVersion;

    @TableField(exist = false)
    private String customerName;

    @TableField(exist = false)
    private String telNo;

    @TableField(exist = false)
    private String address;

    @TableField(exist = false)
    private String email;

    @TableField(exist = false)
    private String areaName;

    @TableField(exist = false)
    private String versionName;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public Integer getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(Integer rowVersion) {
        this.rowVersion = rowVersion;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getTelNo() {
        return telNo;
    }

    public void setTelNo(String telNo) {
        this.telNo = telNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getModifyUserId() {
        return modifyUserId;
    }

    public void setModifyUserId(String modifyUserId) {
        this.modifyUserId = modifyUserId;
    }

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public Integer getProblemType() {
        return problemType;
    }

    public void setProblemType(Integer problemType) {
        this.problemType = problemType;
    }

    public String getProblemText() {
        return problemText;
    }

    public void setProblemText(String problemText) {
        this.problemText = problemText;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "feedbackId='" + feedbackId + '\'' +
                ", problemType=" + problemType +
                ", problemText='" + problemText + '\'' +
                ", createUserId='" + createUserId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", createTime=" + createTime +
                ", modifyUserId=" + modifyUserId +
                ", modifyTime=" + modifyTime +
                ", rowVersion=" + rowVersion +
                '}';
    }
}
