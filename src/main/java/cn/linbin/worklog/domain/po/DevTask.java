package cn.linbin.worklog.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "TB_DEVTASK")
public class DevTask implements Serializable {

    @TableId(value = "DEVTASK_ID", type = IdType.ID_WORKER_STR)
    private String devtaskId;

    @TableField(value = "FEEDBACK_ID")
    private Integer feedbackId;

    @TableField(value = "DEVELOP_USER_ID")
    private String developUserId;

    @TableField(value = "RECEIVED")
    private Integer received;

    @TableField(value = "RECEIVE_TIME")
    private Date receiveTime;

    @TableField(value = "RECEIVE_DATE")
    private Date receiveDate;

    @TableField(value = "FINISHED")
    private Integer finished;

    @TableField(value = "FINISH_DATE")
    private Date finishDate;

    @TableField(value = "FINISH_TIME")
    private Date finishTime;

    @TableField(value = "FEEDBACK_TIME")
    private Date feedbackTime;

    @TableField(value = "TASK_DATE")
    private Date taskDate;

    @TableField(value = "TASK_TIME")
    private Date taskTime;

    @TableField(value = "CREATE_USER_ID")
    private String createUserId;

    @TableField(value = "PLAN_HOUR")
    private Double planHour;

    @TableField(value = "REAL_HOUR")
    private Double realHour;

    @TableField(value = "TASK_TEXT")
    private String taskText;

    @TableField(value = "FINISH_TEXT")
    private String finishText;

    //错误引入人
    @TableField(value = "IMPORT_USER_ID")
    private String importUserId;

    //是否是问题，0-不是，1-是
    @TableField(value = "IS_PROBLEM")
    private Integer isProblem;

    //前端传入的是 on off
    @TableField(value = "IS_PROBLEM_TEXT", exist = false)
    private String isProblemText;

    @TableField(value = "NEED_TEST")
    private Integer needTest;

    @TableField(value = "NEED_TEST_TEXT", exist = false)
    private String needTestText;

    @TableField(value = "TEST_RECEIVED")
    private Integer testReceived;

    public String getNeedTestText() {
        return needTestText;
    }

    public void setNeedTestText(String needTestText) {
        this.needTestText = needTestText;
    }

    public Integer getNeedTest() {
        return needTest;
    }

    public void setNeedTest(Integer needTest) {
        this.needTest = needTest;
    }

    public Integer getTestReceived() {
        return testReceived;
    }

    public void setTestReceived(Integer testReceived) {
        this.testReceived = testReceived;
    }

    public String getIsProblemText() {
        return isProblemText;
    }

    public void setIsProblemText(String isProblemText) {
        this.isProblemText = isProblemText;
    }

    public Integer getIsProblem() {
        return isProblem;
    }

    public void setIsProblem(Integer isProblem) {
        this.isProblem = isProblem;
    }

    public String getImportUserId() {
        return importUserId;
    }

    public void setImportUserId(String importUserId) {
        this.importUserId = importUserId;
    }

    public String getTaskText() {
        return taskText;
    }

    public void setTaskText(String taskText) {
        this.taskText = taskText;
    }

    public String getFinishText() {
        return finishText;
    }

    public void setFinishText(String finishText) {
        this.finishText = finishText;
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

    public Integer getReceived() {
        return received;
    }

    public void setReceived(Integer received) {
        this.received = received;
    }

    public Date getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(Date taskDate) {
        this.taskDate = taskDate;
    }

    public Date getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(Date taskTime) {
        this.taskTime = taskTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getDevtaskId() {
        return devtaskId;
    }

    public void setDevtaskId(String devtaskId) {
        this.devtaskId = devtaskId;
    }

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getDevelopUserId() {
        return developUserId;
    }

    public void setDevelopUserId(String developUserId) {
        this.developUserId = developUserId;
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

    public Date getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(Date feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    @Override
    public String toString() {
        return "DevTask{" +
                "devtaskId='" + devtaskId + '\'' +
                ", feedbackId=" + feedbackId +
                ", developUserId='" + developUserId + '\'' +
                ", received=" + received +
                ", receiveTime=" + receiveTime +
                ", receiveDate=" + receiveDate +
                ", finished=" + finished +
                ", finishDate=" + finishDate +
                ", finishTime=" + finishTime +
                ", feedbackTime=" + feedbackTime +
                ", taskDate=" + taskDate +
                ", taskTime=" + taskTime +
                ", createUserId='" + createUserId + '\'' +
                ", planHour=" + planHour +
                ", realHour=" + realHour +
                ", taskText='" + taskText + '\'' +
                ", finishText='" + finishText + '\'' +
                ", importUserId='" + importUserId + '\'' +
                ", isProblem=" + isProblem +
                ", isProblemText='" + isProblemText + '\'' +
                ", needTest=" + needTest +
                ", needTestText='" + needTestText + '\'' +
                ", testReceived=" + testReceived +
                '}';
    }
}
