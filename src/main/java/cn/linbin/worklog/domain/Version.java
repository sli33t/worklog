package cn.linbin.worklog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "TB_VERSION")
public class Version implements Serializable{

    @TableId(value = "VERSION_ID", type = IdType.ID_WORKER_STR)
    private String versionId;

    @TableField(value = "VERSION_NAME")
    private String versionName;

    @TableField(value = "CREATE_TIME")
    private Date createTime;

    @TableField(value = "DELETE_FLAG")
    private Integer deleteFlag;

    @TableField(value = "DELETE_DATE")
    private Date deleteDate;

    public String getVersionId() {
        return versionId;
    }

    public void setVersionId(String versionId) {
        this.versionId = versionId;
    }

    public String getVersionName() {
        return versionName;
    }

    public void setVersionName(String versionName) {
        this.versionName = versionName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

    @Override
    public String toString() {
        return "Version{" +
                "versionId='" + versionId + '\'' +
                ", versionName='" + versionName + '\'' +
                ", createTime=" + createTime +
                ", deleteFlag=" + deleteFlag +
                ", deleteDate=" + deleteDate +
                '}';
    }
}
