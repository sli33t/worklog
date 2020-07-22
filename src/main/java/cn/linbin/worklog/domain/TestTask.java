package cn.linbin.worklog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "TB_TESTTASK")
public class TestTask implements Serializable {

    @TableId(value = "TESTTASK_ID", type = IdType.ID_WORKER_STR)
    private String testtaskId;

    @TableField(value = "FEEDBACK_ID")
    private Integer feedbackId;

    @TableField(value = "DEVTASK_ID")
    private String devtaskId;

    @TableField(value = "TEST_USER_ID")
    private String testUserId;

    @TableField(value = "FEEDBACK_TIME")
    private Date feedbackTime;

    @TableField(value = "TESTTASK_TIME")
    private Date testtaskTime;

    @TableField(value = "TESTTASK_DATE")
    private Date testtaskDate;

    @TableField(value = "RECEIVED")
    private Integer received;

    @TableField(value = "RECEIVE_TIME")
    private Date receiveTime;

    @TableField(value = "RECEIVE_DATE")
    private Date receiveDate;

    @TableField(value = "FINISHED")
    private Integer finished;

    @TableField(value = "FINISH_TIME")
    private Date finishTime;

    @TableField(value = "FINISH_DATE")
    private Date finishDate;

    @TableField(value = "PLAN_HOUR")
    private Double planHour;

    @TableField(value = "REAL_HOUR")
    private Double realHour;

    @TableField(value = "TEST_TEXT")
    private String testText;

    //是否是问题，0-不是，1-是
    @TableField(value = "IS_PROBLEM")
    private Integer isProblem;

    //前端传入的是 on off
    @TableField(value = "IS_PROBLEM_TEXT", exist = false)
    private String isProblemText;

    /**
     * 是否分配测试
     */
    @TableField(value = "TEST_ARRANGE")
    private Integer testArrange;

    public Integer getIsProblem() {
        return isProblem;
    }

    public void setIsProblem(Integer isProblem) {
        this.isProblem = isProblem;
    }

    public String getIsProblemText() {
        return isProblemText;
    }

    public void setIsProblemText(String isProblemText) {
        this.isProblemText = isProblemText;
    }

    public Integer getTestArrange() {
        return testArrange;
    }

    public void setTestArrange(Integer testArrange) {
        this.testArrange = testArrange;
    }

    public Integer getReceived() {
        return received;
    }

    public void setReceived(Integer received) {
        this.received = received;
    }

    public Date getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(Date receiveTime) {
        this.receiveTime = receiveTime;
    }

    public Date getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(Date receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getTestText() {
        return testText;
    }

    public void setTestText(String testText) {
        this.testText = testText;
    }

    public String getTesttaskId() {
        return testtaskId;
    }

    public void setTesttaskId(String testtaskId) {
        this.testtaskId = testtaskId;
    }

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getDevtaskId() {
        return devtaskId;
    }

    public void setDevtaskId(String devtaskId) {
        this.devtaskId = devtaskId;
    }

    public String getTestUserId() {
        return testUserId;
    }

    public void setTestUserId(String testUserId) {
        this.testUserId = testUserId;
    }

    public Date getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(Date feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    public Date getTesttaskTime() {
        return testtaskTime;
    }

    public void setTesttaskTime(Date testtaskTime) {
        this.testtaskTime = testtaskTime;
    }

    public Date getTesttaskDate() {
        return testtaskDate;
    }

    public void setTesttaskDate(Date testtaskDate) {
        this.testtaskDate = testtaskDate;
    }

    public Integer getFinished() {
        return finished;
    }

    public void setFinished(Integer finished) {
        this.finished = finished;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Date getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(Date finishDate) {
        this.finishDate = finishDate;
    }

    public Double getPlanHour() {
        return planHour;
    }

    public void setPlanHour(Double planHour) {
        this.planHour = planHour;
    }

    public Double getRealHour() {
        return realHour;
    }

    public void setRealHour(Double realHour) {
        this.realHour = realHour;
    }

    @Override
    public String toString() {
        return "TestTask{" +
                "testtaskId='" + testtaskId + '\'' +
                ", feedbackId=" + feedbackId +
                ", devtaskId='" + devtaskId + '\'' +
                ", testUserId='" + testUserId + '\'' +
                ", feedbackTime=" + feedbackTime +
                ", testtaskTime=" + testtaskTime +
                ", testtaskDate=" + testtaskDate +
                ", received=" + received +
                ", receiveTime=" + receiveTime +
                ", receiveDate=" + receiveDate +
                ", finished=" + finished +
                ", finishTime=" + finishTime +
                ", finishDate=" + finishDate +
                ", planHour=" + planHour +
                ", realHour=" + realHour +
                ", testText='" + testText + '\'' +
                ", testArrange=" + testArrange +
                ", isProblem=" + isProblem +
                ", isProblemText=" + isProblemText +
                '}';
    }
}
