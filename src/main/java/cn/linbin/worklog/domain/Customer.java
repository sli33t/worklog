package cn.linbin.worklog.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "TB_CUSTOMER")
public class Customer implements Serializable{

    @TableId(value = "CUSTOMER_ID", type = IdType.ID_WORKER_STR)
    private String customerId;

    @TableField(value = "CUSTOMER_NAME")
    private String customerName;

    @TableField(value = "TEL_NO")
    private String telNo;

    @TableField(value = "ADDRESS")
    private String address;

    @TableField(value = "EMAIL")
    private String email;

    @TableField(value = "AREA_ID")
    private String areaId;

    @TableField(value = "DELETE_FLAG")
    private Integer deleteFlag;

    @TableField(value = "DELETE_DATE")
    private Date deleteDate;

    @TableField(value = "CREATE_TIME")
    private Date createTime;

    @TableField(value = "ROW_VERSION")
    private Integer rowVersion;

    @TableField(exist = false)
    private String areaName;

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getRowVersion() {
        return rowVersion;
    }

    public void setRowVersion(Integer rowVersion) {
        this.rowVersion = rowVersion;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
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

    @Override
    public String toString() {
        return "Customer{" +
                "customerId='" + customerId + '\'' +
                ", customerName='" + customerName + '\'' +
                ", telNo='" + telNo + '\'' +
                ", address='" + address + '\'' +
                ", deleteFlag=" + deleteFlag +
                ", deleteDate=" + deleteDate +
                ", createTime=" + createTime +
                ", rowVersion=" + rowVersion +
                ", email=" + email +
                ", areaId=" + areaId +
                ", areaName=" + areaName +
                '}';
    }
}
