package com.example.slrcoding.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slrcoding.Activity.SharingActivity;
import com.example.slrcoding.Activity.SharingDetailActivity;
import com.example.slrcoding.FeedDetailActivity;
import com.example.slrcoding.R;
import com.example.slrcoding.VO.SharingVO;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;

/**
 * @Author 이정찬
 */
public class SharingAdapter extends RecyclerView.Adapter<SharingAdapter.SharingViewHolder> {

    private List<SharingVO> shareVOList = new ArrayList<>();

    public SharingAdapter(List<SharingVO> shareVOList) {
        this.shareVOList = shareVOList;
    }

    @NonNull
    @Override
    public SharingAdapter.SharingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SharingViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.sharing_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull SharingAdapter.SharingViewHolder holder, int position) {
        final SharingVO share = shareVOList.get(position);
        holder.type.setText(share.getType());
        if(share.isUse_avail()){
            holder.useavail.setText("미사용");
            holder.avail_cardview.setBackgroundColor(Color.GREEN);
            holder.shareview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, SharingDetailActivity.class);
                    if (context instanceof Activity) {
                        ((Activity) context).startActivity(intent);
                    }
                }
            });
        }else{
            holder.useavail.setText("사용중");
            holder.avail_cardview.setBackgroundColor(Color.RED);
            holder.shareview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toasty.warning(v.getContext(),"사용중입니다.",Toasty.LENGTH_SHORT,true).show();
                }
            });
        }
        holder.user.setText(share.getUser_name());
        holder.start_date.setText(share.getStart_date());
        holder.end_date.setText(share.getEnd_date());


    }

    @Override
    public int getItemCount() {
        return shareVOList.size();
    }

    public class SharingViewHolder extends RecyclerView.ViewHolder {

        public final View shareview;

        private TextView type;
        private TextView useavail;
        private TextView user;
        private TextView start_date;
        private TextView end_date;
        private CardView avail_cardview;
        public SharingViewHolder(@NonNull View itemView) {
            super(itemView);

            type= itemView.findViewById(R.id.sharing_type);
            useavail=itemView.findViewById(R.id.sharing_useavail);
            user = itemView.findViewById(R.id.sharing_user_text);
            start_date =itemView.findViewById(R.id.sharing_duration_start);
            end_date =itemView.findViewById(R.id.sharing_duration_end);
            avail_cardview =itemView.findViewById(R.id.avail_cardview);

            shareview =itemView;
        }
    }
}
