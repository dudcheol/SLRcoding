package com.example.slrcoding.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slrcoding.Board;
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
    // B : 1 : test
    public static final int VIEW_TYPE_A = 0;
    public static final int VIEW_TYPE_B = 1;

    // 순서별로 어떤 뷰를 보여줄지 리스트에 담아서 결정한다
    private List<MainListViewType> mainListViewTypeList;

    private List<Board> board_A,board_B;
    private View v_A;

    // 받아올 리스트형 객체
    public mainListAdapter(List<MainListViewType> mainListViewTypeList, List<Board> board_A, List<Board> board_B) {
        this.mainListViewTypeList = mainListViewTypeList; // 부모 리사이클러뷰에 어떤 아이템이 들어갈지 결정
        this.board_A = board_A; // 0번째에 들어갈 객체
        this.board_B = board_B; // 1번째에 들어갈 객체
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 지금은 테스트를 위해 main_board만 사용했지만
        // 나중에 데이터가 쌓이면 다른 형태의 뷰타입을 사용할 수 있음
        if(viewType == VIEW_TYPE_A){
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_board,parent,false);
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
        // Todo -- 영철 : 이제 여기에서 어떻게 지지고 볶을지 고민해볼것
        MainListViewType list = mainListViewTypeList.get(position);

        if(holder instanceof AHolder){
            // 받아온 객체를  가져와서 여기서 보여준다
            // position 써서
            // AHolder에서 보여줄 것 구현
            ((AHolder)holder).subject.setText("인기글");

        }else if(holder instanceof  BHolder){
            // BHolder에서 보여줄 것 구현
        }
    }

    @Override
    public int getItemCount() {
        return mainListViewTypeList.size();
    }

    public class AHolder extends RecyclerView.ViewHolder{
        TextView subject,title,content;

        public AHolder(@NonNull View itemView) {
            super(itemView);
            // 여기서 A홀더에 있는 것들 findviewid 해준다.
            subject = (TextView)itemView.findViewById(R.id.subject);
        }
    }

    public class BHolder extends RecyclerView.ViewHolder{
        public BHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
