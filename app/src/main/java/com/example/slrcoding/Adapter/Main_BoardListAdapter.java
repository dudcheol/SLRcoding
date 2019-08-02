package com.example.slrcoding.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.slrcoding.Board;
import com.example.slrcoding.R;

import java.util.List;

public class Main_BoardListAdapter extends BaseAdapter {
    List<Board> boardList;
    LayoutInflater mLayoutInflater;
    Activity activity;

    public Main_BoardListAdapter(Activity activity, List<Board> data){
        this.boardList = data;
        this.activity = activity;
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
        if (mLayoutInflater == null)
            mLayoutInflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (view == null)
            view = mLayoutInflater.inflate(R.layout.main_board_list, null);
        //View v = mLayoutInflater.inflate(R.layout.main_board_list,viewGroup);
        TextView title = (TextView)view.findViewById(R.id.category);
        TextView contents = (TextView)view.findViewById(R.id.title);

        title.setText(boardList.get(i).getTitle());
        contents.setText(boardList.get(i).getContents());

        return view;
    }
}
