package com.example.slrcoding.VO;

// 박영철

public class Meeting_UserVO {
    // sex 1남자 2여자
    int sex;
    String name;
    // 현재 접속중 여부
    boolean connecting;
    // 뉴비 여부
    boolean newbie;

    // 생성자
    public Meeting_UserVO(int sex, String name, boolean connecting, boolean newbie){
        this.sex=sex;
        this.name=name;
        this.connecting=connecting;
        this.newbie=newbie;
    }

    public int getSex() {
        return sex;
    }

    public String getName() {
        return name;
    }

    public boolean isConnecting() {
        return connecting;
    }

    public boolean isNewbie() {
        return newbie;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setConnecting(boolean connecting) {
        this.connecting = connecting;
    }

    public void setNewbie(boolean newbie) {
        this.newbie = newbie;
    }
}
