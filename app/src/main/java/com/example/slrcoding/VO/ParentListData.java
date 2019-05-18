package com.example.slrcoding.VO;

import java.util.ArrayList;
import java.lang.String;

public class ParentListData {

    private String name;
    private int nameIndex;

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

    public int getNameIndex() {
        return nameIndex;
    }

    public void setNameIndex(int nameIndex) {
        this.nameIndex = nameIndex;
    }

}