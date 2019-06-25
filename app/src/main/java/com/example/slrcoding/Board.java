package com.example.slrcoding;

//피드 VO모델
//이정찬
public class Board {
    private String id;
    private String category;
    private String title;
    private String contents;
    private String name;
    private String date;
    public Board(){

    }

    public Board(String id, String category, String title, String contents, String name,String date) {
        this.id = id;
        this.category= category;
        this.title = title;
        this.contents = contents;
        this.name = name;
        this.date = date;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Board{" +
                "id='" + id + '\'' +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", name='" + name + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
