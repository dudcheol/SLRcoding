package com.example.slrcoding;

public class Board {
    private String id;
    private String category;
    private String title;
    private String contents;
    private String name;

    public Board(){

    }

    public Board(String id, String category, String title, String contents, String name) {
        this.id = id;
        this.category= category;
        this.title = title;
        this.contents = contents;
        this.name = name;
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

    @Override
    public String toString() {
        return "Board{" +
                "id='" + id + '\'' +
                ", category='" + category + '\'' +
                ", title='" + title + '\'' +
                ", contents='" + contents + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
