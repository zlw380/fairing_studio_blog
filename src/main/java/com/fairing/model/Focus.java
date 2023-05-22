package com.fairing.model;

import java.util.Date;

public class Focus {
    private Integer id;
    private Long focusId;
    private Date focusTime;

    @Override
    public String toString() {
        return "Focus{" +
                "id=" + id +
                ", focusId=" + focusId +
                ", focusTime=" + focusTime +
                '}';
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getFocusId() {
        return focusId;
    }

    public void setFocusId(Long focusId) {
        this.focusId = focusId;
    }

    public Date getFocusTime() {
        return focusTime;
    }

    public void setFocusTime(Date focusTime) {
        this.focusTime = focusTime;
    }
}
