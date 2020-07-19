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

    @TableField(value = "USER_ID")
    private String userId;

    @TableField(value = "CUSTOMER_ID")
    private String customerId;

    @TableField(value = "CREATE_TIME")
    private Date createTime;

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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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
                ", userId='" + userId + '\'' +
                ", customerId='" + customerId + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
