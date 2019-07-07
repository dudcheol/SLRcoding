package com.example.slrcoding;

public class FeedReplyVO {
    //댓글VO
    //이정찬
    private String replyId; //댓글 아이디
    private String replyContent; //댓글 내용
    private String replyName;// 댓글 작성자 어차피 익명!!
    private String replyDate;//날짜
    public FeedReplyVO(){

    }

    public FeedReplyVO(String replyId,  String replyContent, String replyName, String replyDate) {
        this.replyId = replyId;
        this.replyContent = replyContent;
        this.replyName = replyName;
        this.replyDate = replyDate;
    }

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }



    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }

    public String getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(String replyDate) {
        this.replyDate = replyDate;
    }

    @Override
    public String toString() {
        return "FeedReplyVO{" +
                "replyId='" + replyId + '\'' +
                ", replyContent='" + replyContent + '\'' +
                ", replyName='" + replyName + '\'' +
                ", replyDate='" + replyDate + '\'' +
                '}';
    }
}
