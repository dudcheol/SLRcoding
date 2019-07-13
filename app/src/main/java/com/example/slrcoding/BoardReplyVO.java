package com.example.slrcoding;

public class BoardReplyVO {
    // 댓글VO
    // 김연준
    private String board_replyId; //댓글 아이디
    private String board_replyContent; //댓글 내용
    private String board_replyName;// 댓글 작성자(노 익명)
    private String board_replyDate;//날짜
    public BoardReplyVO(){

    }

    public BoardReplyVO(String replyId, String replyContent, String replyName, String replyDate) {
        this.board_replyId = board_replyId;
        this.board_replyContent = board_replyContent;
        this.board_replyName = board_replyName;
        this.board_replyDate = board_replyDate;
    }

    public String getReplyId() {
        return board_replyId;
    }

    public void setReplyId(String replyId) {
        this.board_replyId = replyId;
    }



    public String getReplyContent() {
        return board_replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.board_replyContent = replyContent;
    }

    public String getReplyName() {
        return board_replyName;
    }

    public void setReplyName(String replyName) {
        this.board_replyName = replyName;
    }

    public String getReplyDate() {
        return board_replyDate;
    }

    public void setReplyDate(String replyDate) {
        this.board_replyDate = replyDate;
    }

    @Override
    public String toString() {
        return "BoardReplyVO{" +
                "replyId='" + board_replyId + '\'' +
                ", replyContent='" + board_replyContent + '\'' +
                ", replyName='" + board_replyName + '\'' +
                ", replyDate='" + board_replyDate + '\'' +
                '}';
    }
}
