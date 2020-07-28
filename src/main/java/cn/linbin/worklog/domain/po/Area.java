package cn.linbin.worklog.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "TB_AREA")
public class Area implements Serializable {

    @TableId(value = "AREA_ID", type = IdType.ID_WORKER_STR)
    private String areaId;

    @TableField(value = "AREA_NAME")
    private String areaName;

    @TableField(value = "DELETE_FLAG")
    private Integer deleteFlag;

    @TableField(value = "DELETE_DATE")
    private Date deleteDate;

    @TableField(value = "CREATE_TIME")
    private Date createTime;

    @TableField(value = "ROW_VERSION")
    private Integer rowVersion;

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(Integer rowVersion) {
        this.rowVersion = rowVersion;
    }

    @Override
    public String toString() {
        return "Area{" +
                "areaId='" + areaId + '\'' +
                ", areaName='" + areaName + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", deleteDate=" + deleteDate +
                ", createTime=" + createTime +
                ", rowVersion=" + rowVersion +
                '}';
    }
}
