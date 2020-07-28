package cn.linbin.worklog.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName(value = "TB_WORKHOUR")
public class WorkHour implements Serializable{

    @TableId(value = "WORKHOUR_ID", type = IdType.ID_WORKER_STR)
    private String workHourId;

    @TableField(value = "USER_ID")
    private String userId;

    @TableField(value = "DEVELOP_USER")
    private String developUser;

    @TableField(value = "WORK_COUNT")
    private Double workCount;

    @TableField(value = "PLAN_HOUR")
    private Double planHour;

    @TableField(value = "REAL_HOUR")
    private Double realHour;

    @TableField(value = "DELAY_HOUR")
    private Double delayHour;

    public String getWorkHourId() {
        return workHourId;
    }

    public void setWorkHourId(String workHourId) {
        this.workHourId = workHourId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDevelopUser() {
        return developUser;
    }

    public void setDevelopUser(String developUser) {
        this.developUser = developUser;
    }

    public Double getWorkCount() {
        return workCount;
    }

    public void setWorkCount(Double workCount) {
        this.workCount = workCount;
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

    public Double getDelayHour() {
        return delayHour;
    }

    public void setDelayHour(Double delayHour) {
        this.delayHour = delayHour;
    }

    @Override
    public String toString() {
        return "WorkHour{" +
                "workHourId='" + workHourId + '\'' +
                ", userId='" + userId + '\'' +
                ", developUser='" + developUser + '\'' +
                ", workCount=" + workCount +
                ", planHour=" + planHour +
                ", realHour=" + realHour +
                ", delayHour=" + delayHour +
                '}';
    }
}
