package com.example.slrcoding.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.slrcoding.Board;
import com.example.slrcoding.R;

import java.util.List;

public class BoardListAdapter extends BaseAdapter {
    List<Board> boardList;
    LayoutInflater mLayoutInflater;

    public BoardListAdapter(List<Board> data){
        boardList = data;
    }

    @Override
    public int getCount() {
        return boardList.size();
    }

    @Override
    public Object getItem(int i) {
        return boardList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = mLayoutInflater.inflate(R.layout.main_board_list,null);
        TextView category = (TextView)v.findViewById(R.id.category);
        TextView title = (TextView)v.findViewById(R.id.title);

        category.setText(boardList.get(i).getCategory());
        title.setText(boardList.get(i).getTitle());

        return v;
    }
}
