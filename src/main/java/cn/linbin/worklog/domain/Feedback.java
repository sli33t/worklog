package cn.linbin.worklog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "TB_FEEDBACK")
public class Feedback implements Serializable {

    @TableId(value = "FEEDBACK_ID", type = IdType.AUTO)
    private Integer feedbackId;

    @TableField(value = "PROBLEM_TYPE")
    private Integer problemType;

    //反馈类型：内部反馈-0，客户反馈-1
    @TableField(value = "FEEDBACK_TYPE")
    private Integer feedbackType;

    //优先级别：紧急-0，高-1，中-2，低-3
    @TableField(value = "PRIORITY")
    private Integer priority;

    @TableField(value = "PROBLEM_TEXT")
    private String problemText;

    @TableField(value = "REQUIRE_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date requireDate;

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

    @TableField(value = "FINISHED")
    private Integer finished;

    @TableField(value = "FINISH_DATE")
    private Date finishDate;

    @TableField(value = "FINISH_TIME")
    private Date finishTime;

    @TableField(value = "STATUS")
    private Integer status;

    @TableField(value = "CUSTOMER_NAME", exist = false)
    private String customerName;

    @TableField(value = "TEL_NO", exist = false)
    private String telNo;

    @TableField(value = "ADDRESS", exist = false)
    private String address;

    @TableField(value = "EMAIL", exist = false)
    private String email;

    @TableField(value = "AREA_NAME", exist = false)
    private String areaName;

    @TableField(value = "VERSION_NAME", exist = false)
    private String versionName;

    @TableField(value = "DEVELOPER", exist = false)
    private String developer;

    @TableField(value = "FINISH_TEXT", exist = false)
    private String finishText;

    @TableField(value = "CREATE_USER", exist = false)
    private String createUser;

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getFinishText() {
        return finishText;
    }

    public void setFinishText(String finishText) {
        this.finishText = finishText;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getRequireDate() {
        return requireDate;
    }

    public void setRequireDate(Date requireDate) {
        this.requireDate = requireDate;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getFeedbackType() {
        return feedbackType;
    }

    public void setFeedbackType(Integer feedbackType) {
        this.feedbackType = feedbackType;
    }

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

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
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
                "feedbackId=" + feedbackId +
                ", problemType=" + problemType +
                ", feedbackType=" + feedbackType +
                ", priority=" + priority +
                ", problemText='" + problemText + '\'' +
                ", requireDate=" + requireDate +
                ", createUserId='" + createUserId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", createTime=" + createTime +
                ", modifyUserId='" + modifyUserId + '\'' +
                ", modifyTime=" + modifyTime +
                ", rowVersion=" + rowVersion +
                ", finished=" + finished +
                ", finishDate=" + finishDate +
                ", finishTime=" + finishTime +
                ", status=" + status +
                ", customerName='" + customerName + '\'' +
                ", telNo='" + telNo + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", areaName='" + areaName + '\'' +
                ", versionName='" + versionName + '\'' +
                ", developer='" + developer + '\'' +
                ", finishText='" + finishText + '\'' +
                ", createUser='" + createUser + '\'' +
                '}';
    }
}
