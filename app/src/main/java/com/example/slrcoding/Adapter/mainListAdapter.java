package com.example.slrcoding.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slrcoding.R;
import com.example.slrcoding.util.MainListViewType;

import java.util.ArrayList;
import java.util.List;

// 박영철
// 리사이클러뷰 어댑터
// 리사이클러뷰 뷰타입 사용.txt 확인하기

public class mainListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // 뷰 타입 별로 다른 뷰 제공
    // type : flag : subject
    // A : 0 : 인기글
    // B : 1 : 최신글
    public static final int VIEW_TYPE_A = 0;
    public static final int VIEW_TYPE_B = 1;

    // 순서별로 어떤 뷰를 보여줄지 리스트에 담아서 결정한다
    private List<MainListViewType> mainListViewTypeList = new ArrayList<>();

    // 받아올 리스트형 객체
    public mainListAdapter(int ab) {
        super();
    }

    public mainListAdapter(String ab) {
        super();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Todo - 여기 연결된 레이아웃을 viewtype_main_recyclerview_1,2로 바꾸고, 각각에 main_cardview_famous랑 min_cardview_latest 넣으면 될듯?
        if(viewType == VIEW_TYPE_A){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_cardview_famous,parent,false);
            return new AHolder(v);
        }
        else{
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_cardview_latest,parent,false);
            return new BHolder(v);
        }
    }


    // onCreateViewHolder가 호출되기 전에 getItemViewType이 먼저 호출되어
    // 뷰타입을 어떻게 정의할지 확인함
    @Override
    public int getItemViewType(int position) {
        switch (mainListViewTypeList.get(position).getViewType()){
            case VIEW_TYPE_A:
                return VIEW_TYPE_A;
            case VIEW_TYPE_B:
                return VIEW_TYPE_B;

                // Todo : 디폴트의 경우 에러처리 아니면 if문으로 바꾸기
                default:
                    return -1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MainListViewType list = mainListViewTypeList.get(position);
        if(holder instanceof AHolder){
            // 받아온 객체를  가져와서 여기서 보여준다
            // position 써서
            // AHolder에서 보여줄 것 구현
            ((AHolder)holder).titleText.setText("aa");
        }else if(holder instanceof  BHolder){
            // BHolder에서 보여줄 것 구현
            ((BHolder)holder).titleText.setText("aa");
        }
    }

    @Override
    public int getItemCount() {
        return mainListViewTypeList.size();
    }

    public class AHolder extends RecyclerView.ViewHolder{
        TextView titleText;

        public AHolder(@NonNull View itemView) {
            super(itemView);
            // 여기서 A홀더에 있는 것들 findviewid 해준다.
            titleText=(TextView) itemView.findViewById(R.id.latest_content_title);
        }
    }

    public class BHolder extends RecyclerView.ViewHolder{
        TextView titleText;
        TextView contentText;
        public BHolder(@NonNull View itemView) {
            super(itemView);
            titleText=(TextView) itemView.findViewById(R.id.latest_content_title);
            contentText=(TextView) itemView.findViewById(R.id.latest_content_content);
        }
    }
}
