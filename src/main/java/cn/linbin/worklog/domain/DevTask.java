package cn.linbin.worklog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName(value = "TB_DEVTASK")
public class DevTask implements Serializable {

    @TableId(value = "DEVTASK_ID", type = IdType.ID_WORKER_STR)
    private String devtaskId;

    @TableField(value = "FEEDBACK_ID")
    private String feedbackId;

    @TableField(value = "DEVELOP_USER_ID")
    private String developUserId;

    @TableField(value = "RECEIVED")
    private Integer received;

    @TableField(value = "RECEIVE_TIME")
    private String receiveTime;

    @TableField(value = "RECEIVE_DATE")
    private String receiveDate;

    @TableField(value = "FINISHED")
    private String finished;

    @TableField(value = "FINISH_DATE")
    private String finishDate;

    @TableField(value = "FINISH_TIME")
    private String finishTime;

    @TableField(value = "FEEDBACK_TIME")
    private String feedbackTime;

    @TableField(value = "TASK_DATE")
    private String taskDate;

    @TableField(value = "TASK_TIME")
    private String taskTime;

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

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
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

    public String getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(String feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getDevelopUserId() {
        return developUserId;
    }

    public void setDevelopUserId(String developUserId) {
        this.developUserId = developUserId;
    }

    public String getReceiveTime() {
        return receiveTime;
    }

    public void setReceiveTime(String receiveTime) {
        this.receiveTime = receiveTime;
    }

    public String getReceiveDate() {
        return receiveDate;
    }

    public void setReceiveDate(String receiveDate) {
        this.receiveDate = receiveDate;
    }

    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
    }

    public String getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(String finishDate) {
        this.finishDate = finishDate;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public String getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(String feedbackTime) {
        this.feedbackTime = feedbackTime;
    }

    @Override
    public String toString() {
        return "DevTask{" +
                "devtaskId='" + devtaskId + '\'' +
                ", feedbackId='" + feedbackId + '\'' +
                ", developUserId='" + developUserId + '\'' +
                ", received=" + received +
                ", receiveTime='" + receiveTime + '\'' +
                ", receiveDate='" + receiveDate + '\'' +
                ", finished='" + finished + '\'' +
                ", finishDate='" + finishDate + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", feedbackTime='" + feedbackTime + '\'' +
                ", taskDate='" + taskDate + '\'' +
                ", taskTime='" + taskTime + '\'' +
                ", createUserId='" + createUserId + '\'' +
                ", planHour=" + planHour +
                ", realHour=" + realHour +
                ", finishText='" + finishText + '\'' +
                ", taskText='" + taskText + '\'' +
                '}';
    }
}
