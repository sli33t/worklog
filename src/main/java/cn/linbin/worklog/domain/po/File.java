package cn.linbin.worklog.domain.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

@TableName(value = "TB_FILE")
public class File implements Serializable{

    @TableId(value = "FILE_ID", type = IdType.INPUT)
    private String fileId;

    @TableField(value = "FILE_NAME")
    private String fileName;

    @TableField(value = "FILE_URL")
    private String fileUrl;

    @TableField(value = "FILE_FOLDER")
    private String fileFolder;

    @TableField(value = "FILE_UUID")
    private String fileUUID;

    @TableField(value = "USER_ID")
    private String userId;

    /**
     * 0-管理岗查看,1-员工查看,2-全员查看
     */
    @TableField(value = "FILE_TYPE")
    private Integer fileType;

    @TableField(value = "DELETE_FLAG")
    private Integer deleteFlag;

    @TableField(value = "DELETE_DATE")
    private Date deleteDate;

    @TableField(value = "CREATE_TIME")
    private Date createTime;

    public String getFileFolder() {
        return fileFolder;
    }

    public void setFileFolder(String fileFolder) {
        this.fileFolder = fileFolder;
    }

    public String getFileUUID() {
        return fileUUID;
    }

    public void setFileUUID(String fileUUID) {
        this.fileUUID = fileUUID;
    }

    public Integer getFileType() {
        return fileType;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Integer getileType() {
        return fileType;
    }

    public void setFileType(Integer fileType) {
        this.fileType = fileType;
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
        return "File{" +
                "fileId='" + fileId + '\'' +
                ", fileName='" + fileName + '\'' +
                ", fileUrl='" + fileUrl + '\'' +
                ", fileFolder='" + fileFolder + '\'' +
                ", fileUUID='" + fileUUID + '\'' +
                ", userId='" + userId + '\'' +
                ", fileType=" + fileType +
                ", deleteFlag=" + deleteFlag +
                ", deleteDate=" + deleteDate +
                ", createTime=" + createTime +
                '}';
    }
}
