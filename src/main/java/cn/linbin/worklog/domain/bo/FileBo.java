package cn.linbin.worklog.domain.bo;

public class FileBo {

    private String fileName;
    private String folder;
    private String fileUUID;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }

    public String getFileUUID() {
        return fileUUID;
    }

    public void setFileUUID(String fileUUID) {
        this.fileUUID = fileUUID;
    }

    @Override
    public String toString() {
        return "FileBo{" +
                "fileName='" + fileName + '\'' +
                ", folder='" + folder + '\'' +
                ", fileUUID='" + fileUUID + '\'' +
                '}';
    }
}
