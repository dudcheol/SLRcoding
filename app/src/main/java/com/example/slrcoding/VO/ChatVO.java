package com.example.slrcoding.VO;

/**
 * @Writer Lee Jeong Chan
 * @Date 2019.08.23
 * 채팅방 정보에 들어갈 것은 대량 수정 예상.
 * public Map<String,boolean> users = new HashMap<>(); => 채팅방 유저들
 * public Map<String,Comment> comments = new HashMap<>(); => 채팅방 메시지들
 * public static class Comment(){
 *     String uid;
 *     String message;
 * }
 *
 *     */
public class ChatVO {
    public String uid;
    public String destinationUid;

    public ChatVO() {
    }

    public ChatVO(String uid, String destinationUid) {
        this.uid = uid;
        this.destinationUid = destinationUid;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDestinationUid() {
        return destinationUid;
    }

    public void setDestinationUid(String destinationUid) {
        this.destinationUid = destinationUid;
    }

    @Override
    public String toString() {
        return "ChatVO{" +
                "uid='" + uid + '\'' +
                ", destinationUid='" + destinationUid + '\'' +
                '}';
    }
}
