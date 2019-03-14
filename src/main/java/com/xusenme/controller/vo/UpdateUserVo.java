package com.xusenme.controller.vo;

public class UpdateUserVo {

    private String userId;
    private int size;
    private Boolean active;

    @Override
    public String toString() {
        return "UpdateUserVo{" +
                "userId='" + userId + '\'' +
                ", size=" + size +
                ", active=" + active +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
