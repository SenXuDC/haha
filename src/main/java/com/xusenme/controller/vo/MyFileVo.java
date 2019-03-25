package com.xusenme.controller.vo;

public class MyFileVo {
    private String id;
    private String dir;
    private String folder;
    private String findFilename;

    public String getFindFilename() {
        return findFilename;
    }

    public void setFindFilename(String findFilename) {
        this.findFilename = findFilename;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public String getFolder() {
        return folder;
    }

    public void setFolder(String folder) {
        this.folder = folder;
    }
}
