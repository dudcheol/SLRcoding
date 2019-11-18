package com.example.slrcoding.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.example.slrcoding.Activity.SharingActivity;
import com.example.slrcoding.Board;
import com.example.slrcoding.BoardDetailActivity;
import com.example.slrcoding.FeedDetailActivity;
import com.example.slrcoding.MainActivity;
import com.example.slrcoding.R;
import com.example.slrcoding.UserVO;
import com.example.slrcoding.VO.Main_JunggoVO;
import com.example.slrcoding.fragment.BoardFragment;
import com.example.slrcoding.fragment.FeedFragment;
import com.example.slrcoding.fragment.MainFragment;
import com.example.slrcoding.util.MainListViewType;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.example.slrcoding.MainActivity.uservo;

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

    private String prof_string = "_profileImage.png";

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
//        else if(viewType == VIEW_TYPE_C){
//            v_C = LayoutInflater.from(parent.getContext())
//                    .inflate(R.layout.main_junggo,parent,false);
//            return new CHolder(v_C);
//        }
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
//            case VIEW_TYPE_C:
//                return VIEW_TYPE_C;
            default:
                return -1;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        // 이제 여기에서 어떻게 지지고 볶을지 고민해볼것

        if(holder instanceof AHolder){
            // A : 게시글종류
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
                // Todo : 스포츠와 게임에서 더보기 누르면 기숙사와 밥으로 넘어감 해결해야함
            });

        }else if(holder instanceof  BHolder){
            // B : 내정보
            // 프로필 이미지 동그랗게
            ((BHolder) holder).profile.setBackground(new ShapeDrawable(new OvalShape()));
            ((BHolder) holder).profile.setClipToOutline(true);

            profileSetting(((BHolder)holder).profile);

            ((BHolder) holder).nickname.setText(uservo.getUser_id());
            ((BHolder) holder).name_and_ID.setText(uservo.getUser_name()+" / "+uservo.getUser_email());
            ((BHolder) holder).school.setText("한국산업기술대학교");

            //이정찬 수정 (2019-09-12)
            //카 쉐어링 액티비티 이동시키기
//            ((BHolder) holder).cardView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Context context = v.getContext();
//
//                    Intent intent = new Intent(context,SharingActivity.class);
//                    if (context instanceof Activity) {
//                        ((Activity) context).startActivity(intent);
//                    }
//                }
//            });


//        }else if(holder instanceof CHolder){
            // C : 중고장터
//            ((CHolder)holder).subject.setText(mainListViewTypeList.get(position).getName());
//            if(mainListViewTypeList.get(position).getJunggos()!=null){
//                Main_JunggoListAdapter main_junggoListAdapter = new Main_JunggoListAdapter((Activity)v_C.getContext(),mainListViewTypeList.get(position).getJunggos());
//                ((CHolder) holder).junggo_image_album.setHasFixedSize(true);
//                ((CHolder) holder).junggo_image_album.setLayoutManager(new LinearLayoutManager(v_C.getContext()
//                        , LinearLayoutManager.HORIZONTAL
//                        ,false));
//                ((CHolder)holder).junggo_image_album.setAdapter(main_junggoListAdapter);
//            }
//
//            ((CHolder) holder).go_to_detail.setOnClickListener(v->{
//                MainActivity activity = (MainActivity)mContext;
//                activity.replaceFragment(new BoardFragment(),2);
//            });
        }
    }

    @Override
    public int getItemCount() {
        return mainListViewTypeList.size();
    }

    // 게시글
    public class AHolder extends RecyclerView.ViewHolder{
        TextView subject,go_to_detail;
        ListView listView;
        ProgressBar baord_progressbar;

        public AHolder(@NonNull View itemView) {
            super(itemView);
            // 여기서 A홀더에 있는 것들 findviewid 해준다.
            subject = (TextView)itemView.findViewById(R.id.subject);
            listView = (ListView)itemView.findViewById(R.id.list);
            go_to_detail = itemView.findViewById(R.id.go_to_detail);
            //baord_progressbar = itemView.findViewById(R.id.main_board_progressBar);
        }
    }

    // 내정보
    public class BHolder extends RecyclerView.ViewHolder{
        ImageView profile;
        TextView name_and_ID, school, nickname;
        //이정찬 수정
//        CardView cardView;
        ProgressBar myinfo_progressbar;

        public BHolder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.profile);
            name_and_ID = itemView.findViewById(R.id.name_and_ID);
            school = itemView.findViewById(R.id.school);
            nickname = itemView.findViewById(R.id.nickname);
            //이정찬 수정
//            cardView = itemView.findViewById(R.id.sharing);
            //myinfo_progressbar = itemView.findViewById(R.id.main_myinfo_progressBar);
        }
    }

    // 중고장터
//    private class CHolder extends RecyclerView.ViewHolder {
//        TextView subject,go_to_detail;
//        RecyclerView junggo_image_album;
//        CardView junggo_card;
//        public CHolder(@NonNull View itemView) {
//            super(itemView);
//            subject = (TextView)itemView.findViewById(R.id.subject);
//            junggo_image_album = itemView.findViewById(R.id.junggo_image_album);
//            junggo_card = itemView.findViewById(R.id.junggo_card);
//            go_to_detail = itemView.findViewById(R.id.go_to_detail);
//        }
//    }

    void profileSetting(ImageView img){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageReference = storage.getReferenceFromUrl("gs://slrcoding.appspot.com/");
        StorageReference pathReference = storageReference.child("Profile Images/"+uservo.getUser_id() + prof_string);
        pathReference.getDownloadUrl().addOnSuccessListener(uri -> {
            Glide.with(mContext)
                    .load(uri)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(img);
        });
    }
}
