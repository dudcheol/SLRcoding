package com.example.slrcoding.VO;

/**
 * 쉐어링 정보
 * @Author 이정찬
 * @Date 2019.09.14
 */
public class SharingVO {
    private boolean use_avail;
    private int lock_number;
    private String user_name;
    private String type;
    private String start_date;
    private String end_date;

    public SharingVO() {
    }

    public SharingVO(boolean use_avail, int lock_number, String user_name, String type, String start_date, String end_date) {
        this.use_avail = use_avail;
        this.lock_number = lock_number;
        this.user_name = user_name;
        this.type = type;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public boolean isUse_avail() {
        return use_avail;
    }

    public void setUse_avail(boolean use_avail) {
        this.use_avail = use_avail;
    }

    public int getLock_number() {
        return lock_number;
    }

    public void setLock_number(int lock_number) {
        this.lock_number = lock_number;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    @Override
    public String toString() {
        return "SharingVO{" +
                "use_avail=" + use_avail +
                ", lock_number=" + lock_number +
                ", user_name='" + user_name + '\'' +
                ", type='" + type + '\'' +
                ", start_date='" + start_date + '\'' +
                ", end_date='" + end_date + '\'' +
                '}';
    }
}
