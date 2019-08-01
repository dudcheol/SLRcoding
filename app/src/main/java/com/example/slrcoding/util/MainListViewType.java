package com.example.slrcoding.util;

import com.example.slrcoding.Board;
import com.example.slrcoding.VO.Main_JunggoVO;

import java.util.List;

public class MainListViewType {
    private String name;
    private int viewType;
    private List<Board> boards;
    private List<Main_JunggoVO> junggos;

    public MainListViewType (int viewType, String name){
        this.viewType=viewType;
        this.name=name;
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

    public List<Board> getBoards() {
        return boards;
    }

    public void setBoards(List<Board> boards) {
        this.boards = boards;
    }

    //임시로만든 VO

    public List<Main_JunggoVO> getJunggos() {
        return junggos;
    }

    public void setJunggos(List<Main_JunggoVO> junggos) {
        this.junggos = junggos;
    }
}