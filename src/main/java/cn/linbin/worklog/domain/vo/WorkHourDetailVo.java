package cn.linbin.worklog.domain.vo;

import java.util.Date;

public class WorkHourDetailVo {

    private Integer feedbackId;
    private String developUser;
    private Double planHour;
    private Double realHour;
    private Date feedbackTime;
    private Date finishTime;
    private Date requireDate;
    private Double delayDays;

    public Integer getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Integer feedbackId) {
        this.feedbackId = feedbackId;
    }

    public String getDevelopUser() {
        return developUser;
    }

    public void setDevelopUser(String developUser) {
        this.developUser = developUser;
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

    public Date getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(Date feedbackTime) {
        this.feedbackTime = feedbackTime;
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

    public Double getDelayDays() {
        return delayDays;
    }

    public void setDelayDays(Double delayDays) {
        this.delayDays = delayDays;
    }

    @Override
    public String toString() {
        return "WorkHourDetailVo{" +
                "feedbackId='" + feedbackId + '\'' +
                ", developUser='" + developUser + '\'' +
                ", planHour=" + planHour +
                ", realHour=" + realHour +
                ", feedbackTime=" + feedbackTime +
                ", finishTime=" + finishTime +
                ", requireDate=" + requireDate +
                ", delayDays=" + delayDays +
                '}';
    }
}
