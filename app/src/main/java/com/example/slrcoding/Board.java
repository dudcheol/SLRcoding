package com.example.slrcoding;

//피드 VO모델
//이정찬
public class Board {
    private String id;
    private String category;
    private String title;
    private String contents;
    private String name;
    private String regDate;
    private String regModifyDate;
    private Long replyCnt;
    private Long likeCnt;
    public Board(){

    }


    public Board(String id, String category, String title, String contents, String name,String regDate,Long replyCnt,String regModifyDate,Long likeCnt) {

        this.id = id;
        this.category= category;
        this.title = title;
        this.contents = contents;
        this.name = name;
        this.regDate =regDate;
        this.replyCnt = replyCnt;
        this.regModifyDate = regModifyDate;
        this.likeCnt = likeCnt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategory(){return category;}

    public void setCategory(String category){this.category=category;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public Long getReplyCnt() {
        return replyCnt;
    }

    public void setReplyCnt(Long replyCnt) {
        this.replyCnt = replyCnt;

    }

    public String getRegModifyDate() {
        return regModifyDate;
    }

    public void setRegModifyDate(String regModifyDate) {
        this.regModifyDate = regModifyDate;

    }

    public Long getLikeCnt() {
        return likeCnt;
    }

    public void setLikeCnt(Long likeCnt) {
        this.likeCnt = likeCnt;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id='" + id + '\'' +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", name='" + name + '\'' +
                ", regDate='" + regDate + '\'' +
                ", regModifyDate='" + regModifyDate + '\'' +
                ", replyCnt=" + replyCnt +
                ", likeCnt=" + likeCnt +
                '}';
    }
}
