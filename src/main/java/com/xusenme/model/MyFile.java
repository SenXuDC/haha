package com.xusenme.model;

public class MyFile {

    private String id;
    private String filename;
    private String md5;
    private Boolean active;
    private String fastdfsPath;
    private int size;
    private User user;
    private Boolean folder;

    @Override
    public String toString() {
        return "MyFile{" +
                "id='" + id + '\'' +
                ", filename='" + filename + '\'' +
                ", md5='" + md5 + '\'' +
                ", active=" + active +
                ", fastdfsPath='" + fastdfsPath + '\'' +
                ", size=" + size +
                ", user=" + user +
                ", folder=" + folder +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getFastdfsPath() {
        return fastdfsPath;
    }

    public void setFastdfsPath(String fastdfsPath) {
        this.fastdfsPath = fastdfsPath;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Boolean getFolder() {
        return folder;
    }

    public void setFolder(Boolean folder) {
        this.folder = folder;
    }

    public MyFile() {

    }

    public MyFile(String id, String filename, String md5, Boolean active, String fastdfsPath, int size, User user, Boolean folder) {
        this.id = id;
        this.filename = filename;
        this.md5 = md5;
        this.active = active;
        this.fastdfsPath = fastdfsPath;
        this.size = size;
        this.user = user;
        this.folder = folder;
    }
}
