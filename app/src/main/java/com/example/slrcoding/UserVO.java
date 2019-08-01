package com.example.slrcoding;

// 최민철(수정 : 19.07.22)
public class UserVO {

    private String id;
    private String user_id;
    private String user_email;
    private String user_password;
    private String user_name;
    private String user_year;
    private String user_month;
    private String user_day;
    private String user_phone_num;
    private String user_sex;

    public UserVO(){}

    public void UserVO(String id, String user_id, String user_email, String user_password, String user_name, String user_year,
                       String user_month, String user_day, String user_phone_num, String user_sex){
        this.id=id;
        this.user_id=user_id;
        this.user_email=user_email;
        this.user_password=user_password;
        this.user_name=user_name;
        this.user_year=user_year;
        this.user_month=user_month;
        this.user_day=user_day;
        this.user_phone_num=user_phone_num;
        this.user_phone_num=user_phone_num;
    }


    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_year() {
        return user_year;
    }

    public void setUser_year(String user_year) {
        this.user_year = user_year;
    }

    public String getUser_month() {
        return user_month;
    }

    public void setUser_month(String user_month) {
        this.user_month = user_month;
    }

    public String getUser_day() {
        return user_day;
    }

    public void setUser_day(String user_day) {
        this.user_day = user_day;
    }

    public String getUser_phone_num() {
        return user_phone_num;
    }

    public void setUser_phone_num(String user_phone_num) {
        this.user_phone_num = user_phone_num;
    }

    public String getUser_sex() {
        return user_sex;
    }

    public void setUser_sex(String user_sex) {
        this.user_sex = user_sex;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
