package com.example.slrcoding;

public class BoardReplyVO {
    // 댓글VO
    // 김연준
    private String board_replyId; //댓글 아이디
    private String board_replyContent; //댓글 내용
    private String board_replyName;// 댓글 작성자(실명)
    private String board_replyDate;//날짜
    private String board_replyModifyDate; //날짜 변형

    public BoardReplyVO(){
    }

    public BoardReplyVO(String board_replyId, String board_replyContent, String board_replyName, String board_replyDate, String board_replyModifyDate) {
        this.board_replyId = board_replyId;
        this.board_replyContent = board_replyContent;
        this.board_replyName = board_replyName;
        this.board_replyDate = board_replyDate;
        this.board_replyModifyDate = board_replyModifyDate;
    }

    public String getReplyId() {
        return board_replyId;
    }

    public void setReplyId(String board_replyId) {
        this.board_replyId = board_replyId;
    }

    public String getReplyContent() {
        return board_replyContent;
    }

    public void setReplyContent(String board_replyContent) {
        this.board_replyContent = board_replyContent;
    }

    public String getReplyName() {
        return board_replyName;
    }

    public void setReplyName(String board_replyName) {
        this.board_replyName = board_replyName;
    }

    public String getReplyDate() {
        return board_replyDate;
    }

    public void setReplyDate(String board_replyDate) {
        this.board_replyDate = board_replyDate;
    }

    public String getReplyModifyDate() {
        return board_replyModifyDate;
    }

    public void setReplyModifyDate(String board_replyModifyDate) {
        this.board_replyModifyDate = board_replyModifyDate;
    }
    @Override
    public String toString() {
        return "BoardReplyVO{" +
                "replyId='" + board_replyId + '\'' +
                ", replyContent='" + board_replyContent + '\'' +
                ", replyName='" + board_replyName + '\'' +
                ", replyDate='" + board_replyDate + '\'' +
                ", board_replyModifyDate='" + board_replyModifyDate + '\'' +
                '}';
    }
}
