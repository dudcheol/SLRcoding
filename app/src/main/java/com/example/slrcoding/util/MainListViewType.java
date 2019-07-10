package com.example.slrcoding.util;

import com.example.slrcoding.Board;

public class MainListViewType {
    private String name;
    private int viewType;
    private Board boardObj;

    public void setBoardObj(Board boardObj) {
        this.boardObj = boardObj;
    }

    public Board getBoardObj() {
        return boardObj;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getName() {
        return name;
    }

    public int getViewType() {
        return viewType;
    }
}
