package com.example.slrcoding.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slrcoding.R;
import com.example.slrcoding.VO.Meeting_UserVO;

import org.w3c.dom.Text;

import java.util.List;

public class MeetingAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Meeting_UserVO> Meeting_UserVO_List;
    private Context mContext;

    // 생성자
    public MeetingAdapter(List<Meeting_UserVO> list, Context context) {
        this.Meeting_UserVO_List = list;
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.meeting_profile_item,parent,false);

        return new ItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return Meeting_UserVO_List.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView sex;
        CardView connecting;
        ImageView newbie;
        ImageView profile_img;

        public ItemViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.meeting_profile_item_name);
            sex = v.findViewById(R.id.meeting_profile_item_sex);
            connecting = v.findViewById(R.id.meeting_profile_item_connecting);
            newbie = v.findViewById(R.id.meeting_profile_item_newbie_mark);
            profile_img =  v.findViewById(R.id.meeting_profile_item_image);
        }
    }
}
