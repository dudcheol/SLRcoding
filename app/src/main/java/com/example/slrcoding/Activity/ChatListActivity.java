package com.example.slrcoding.Activity;

import android.os.Bundle;

import com.example.slrcoding.Adapter.ChatListAdapter;
import com.example.slrcoding.VO.ChatVO;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.ViewGroup;

import com.example.slrcoding.R;

import java.util.ArrayList;
import java.util.List;
/**
 * @Writer Lee Jeong Chan
 * @Date 2019.08.23
 * MeetingAdapter(강의에선 PeopleFragment)에서 채팅하기 버튼 클릭시 ChatRoomActivity(강의에선 MessageActivity)로 이동하고 채팅방이 파베에
 * 올라가고 파베에서 해당 채팅방을 ChatListActivity에서 정보가져와서 리시트 만들어서 뿌려줄 것.
 * 그리고 ChatListAdapter에서 아이템(채팅방)클릭시 마찬가지로 ChatRoomActivity로 이동시키기.
 */
public class ChatListActivity extends AppCompatActivity {

    private List<ChatVO> mChatList;
    RecyclerView chatListRecyclerView;
    ChatListAdapter chatListAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);

        chatListRecyclerView = findViewById(R.id.chat_recyclerview);

        mChatList = new ArrayList<>();
        mChatList.add(new ChatVO("내아이디","상대방아이디"));
        mChatList.add(new ChatVO("내아이디","상대방아이디"));
        mChatList.add(new ChatVO("내아이디","상대방아이디"));
        mChatList.add(new ChatVO("내아이디","상대방아이디"));
        mChatList.add(new ChatVO("내아이디","상대방아이디"));

        chatListAdapter = new ChatListAdapter(mChatList);
        chatListRecyclerView.setAdapter(chatListAdapter);
        chatListRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }



}
