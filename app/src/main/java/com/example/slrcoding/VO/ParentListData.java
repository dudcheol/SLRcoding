package com.example.slrcoding.VO;

import java.util.ArrayList;
import java.lang.String;

// 최민철(수정 : xx.xx.xx)
public class ParentListData {

    private String name;

    public ArrayList<ChildListData> childListData;

    public ParentListData() {

        childListData = new ArrayList<>();
    }

    public void setName(String name){
        this.name=name;

    }

    public String getName() {

        return this.name;

    }

}