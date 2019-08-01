package com.example.slrcoding.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.slrcoding.Board;
import com.example.slrcoding.BoardDetailActivity;
import com.example.slrcoding.FeedDetailActivity;
import com.example.slrcoding.MainActivity;
import com.example.slrcoding.R;
import com.example.slrcoding.VO.Main_JunggoVO;
import com.example.slrcoding.fragment.BoardFragment;
import com.example.slrcoding.fragment.FeedFragment;
import com.example.slrcoding.fragment.MainFragment;
import com.example.slrcoding.util.MainListViewType;

import java.util.List;

// 박영철
// 리사이클러뷰 어댑터
// 리사이클러뷰 뷰타입 사용.txt 확인하기

public class MainListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    // 뷰 타입 별로 다른 뷰 제공
    // type : flag : subject
    // A : 0 : 게시글종류
    // B : 1 : 내정보
    // C : 2 : 중고장터
    public static final int VIEW_TYPE_A = 0;
    public static final int VIEW_TYPE_B = 1;
    public static final int VIEW_TYPE_C = 2;

    // 순서별로 어떤 뷰를 보여줄지 리스트에 담아서 결정한다
    private List<MainListViewType> mainListViewTypeList;

    private View v_A,v_B,v_C;
    private Context mContext;
    private Activity mActivity;

    // 받아올 리스트형 객체
    public MainListAdapter(List<MainListViewType> mainListViewTypeList, Context context, Activity activity) {
        this.mainListViewTypeList = mainListViewTypeList; // 부모 리사이클러뷰에 어떤 아이템이 들어갈지 결정
        this.mContext=context;
        this.mActivity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == VIEW_TYPE_A){
            v_A = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_board,parent,false);
            return new AHolder(v_A);
        }
        else if(viewType == VIEW_TYPE_B){
            v_B = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_myinfo,parent,false);
            return new BHolder(v_B);
        }
        else if(viewType == VIEW_TYPE_C){
            v_C = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.main_junggo,parent,false);
            return new CHolder(v_C);
        }
        else{
            // Todo : 정보를 받아오는데 에러가 발생했다는 것을 알리는 레이아웃 생성해도 괜찮을듯
            return null;
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
            case VIEW_TYPE_C:
                return VIEW_TYPE_C;
                // Todo : 디폴트의 경우 에러처리 아니면 if문으로 바꾸기
                default:
                    return -1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // 이제 여기에서 어떻게 지지고 볶을지 고민해볼것

        if(holder instanceof AHolder){
            // 받아온 객체를  가져와서 여기서 보여준다
            // position 써서
            // AHolder에서 보여줄 것 구현
            ((AHolder)holder).subject.setText(mainListViewTypeList.get(position).getName());

            List<Board> boards = mainListViewTypeList.get(position).getBoards();
            if(boards!=null) {
                Main_BoardListAdapter boardListAdapter = new Main_BoardListAdapter((Activity) v_A.getContext(), boards);
                ((AHolder) holder).listView.setAdapter(boardListAdapter);
                ((AHolder) holder).listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Intent intent = new Intent(mContext, FeedDetailActivity.class);
                        intent.putExtra("category",boards.get(i).getCategory());
                        intent.putExtra("id",boards.get(i).getId());
                        mContext.startActivity(intent);
                    }
                });
            }

            ((AHolder) holder).go_to_detail.setOnClickListener(view -> {
                MainActivity activity = (MainActivity)mContext;
                activity.replaceFragment(new FeedFragment(),1);
            });

        }else if(holder instanceof  BHolder){
            // BHolder에서 보여줄 것 구현

        }else if(holder instanceof CHolder){
            ((CHolder)holder).subject.setText(mainListViewTypeList.get(position).getName());
            if(mainListViewTypeList.get(position).getJunggos()!=null){
                Main_JunggoListAdapter main_junggoListAdapter = new Main_JunggoListAdapter((Activity)v_C.getContext(),mainListViewTypeList.get(position).getJunggos());
                ((CHolder) holder).junggo_image_album.setHasFixedSize(true);
                ((CHolder) holder).junggo_image_album.setLayoutManager(new LinearLayoutManager(v_C.getContext()
                        , LinearLayoutManager.HORIZONTAL
                        ,false));
                //((CHolder) holder).junggo_card.bringChildToFront(((CHolder) holder).junggo_image_album);
                ((CHolder)holder).junggo_image_album.setAdapter(main_junggoListAdapter);
            }

            ((CHolder) holder).go_to_detail.setOnClickListener(v->{
                MainActivity activity = (MainActivity)mContext;
                activity.replaceFragment(new BoardFragment(),2);
            });
        }
    }

    @Override
    public int getItemCount() {
        return mainListViewTypeList.size();
    }

    public class AHolder extends RecyclerView.ViewHolder{
        TextView subject,go_to_detail;
        ListView listView;

        public AHolder(@NonNull View itemView) {
            super(itemView);
            // 여기서 A홀더에 있는 것들 findviewid 해준다.
            subject = (TextView)itemView.findViewById(R.id.subject);
            listView = (ListView)itemView.findViewById(R.id.list);
            go_to_detail = itemView.findViewById(R.id.go_to_detail);
        }
    }

    public class BHolder extends RecyclerView.ViewHolder{
        public BHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

    private class CHolder extends RecyclerView.ViewHolder {
        TextView subject,go_to_detail;
        RecyclerView junggo_image_album;
        CardView junggo_card;
        public CHolder(@NonNull View itemView) {
            super(itemView);
            subject = (TextView)itemView.findViewById(R.id.subject);
            junggo_image_album = itemView.findViewById(R.id.junggo_image_album);
            junggo_card = itemView.findViewById(R.id.junggo_card);
            go_to_detail = itemView.findViewById(R.id.go_to_detail);
        }
    }
}
