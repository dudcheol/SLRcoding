package com.example.slrcoding.fragment;


import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.slrcoding.FeedWriteActivity;
import com.example.slrcoding.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
//부모 피드 프래그먼트
//이정찬
/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {
    //피드 프래그먼트 여기서 스피너에 아이템 클릭시 자식 프래그먼트로 넘어감.
    Spinner spinner;
    private FragmentManager fragmentManager;

    Feed_Child_FragmentOne fragmentOne;
    Feed_Child_FragmentTwo fragmentTwo;
    private int f;
    private GridLayoutManager mGridLayoutManager;
    private LinearLayoutManager mLinearLayoutManager;
    private boolean Grid_Linear=true;
    private boolean Grid_Linear2=true;
    //에니메이션
    private Animation fab_open, fab_close;
    private boolean isFabOpen = false;
    //플로팅버튼
    String categoryName;
    private FloatingActionButton fab_main, fab_sub1, fab_sub2;
    public int switch_value;
    private int flag;
    public static final int REQUEST_CODE = 1000;
    public static SearchView mSearchView;
    public Toolbar toolbar;
    public FeedFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_feed, container, false);
        //스피너와 자식 프래그먼트 아이디로 얻어오기
        spinner = rootView.findViewById(R.id.spinner);

        fab_open = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);

        fab_main = (FloatingActionButton) rootView.findViewById(R.id.fab_main);
        fab_sub1 = (FloatingActionButton) rootView.findViewById(R.id.fab_sub1);
        fab_sub2 = (FloatingActionButton) rootView.findViewById(R.id.fab_sub2);

        //fragmentOne = new Feed_Child_FragmentOne();
        //fragmentTwo = new Feed_Child_FragmentTwo();

        //스피너 적용
        ArrayAdapter<String> ad = new ArrayAdapter<>(getActivity(),
                R.layout.custom_spinner,
                getResources().getStringArray(R.array.fragments));
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);

        fragmentManager = getChildFragmentManager();
        fragmentOne = new Feed_Child_FragmentOne();
        fragmentManager.beginTransaction().replace(R.id.main_frame,fragmentOne).commit();

        //스피너 이벤트 처리 클릭시 자식으로 이동
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        categoryName = "기숙사와 밥"; //카테고리 넘겨주기 위함.
                        Toast.makeText(getContext(), "categoryName: "+categoryName, Toast.LENGTH_SHORT).show();

                        switch_value=0;
                        //flag는 검색기능을 위해서 피드 내에서 계속 스피너로 이동 시 보드 리스트를 최신화시키기 위해서 add를 해줌.
                        if(fragmentOne == null || flag == 1){
                            Log.i("FeedFragment","1");
                            fragmentOne = new Feed_Child_FragmentOne();
                            fragmentManager.beginTransaction().add(R.id.main_frame,fragmentOne).commit();
                            flag=0;

                        }
                        if(fragmentOne!=null){
                            Log.i("FeedFragment","2");

                            fragmentManager.beginTransaction().show(fragmentOne).commit();

                        }
                        if(fragmentTwo!=null){
                            Log.i("FeedFragment","3");

                            fragmentManager.beginTransaction().hide(fragmentTwo).commit();
                        }
                        break;
                    case 1:
                        categoryName="스포츠와 게임";
                        Toast.makeText(getContext(), "categoryName: "+categoryName, Toast.LENGTH_SHORT).show();
                        switch_value=1;
                        flag =1;
                        if(fragmentTwo == null){
                            Log.i("FeedFragment","4");

                            fragmentTwo = new Feed_Child_FragmentTwo();
                            fragmentManager.beginTransaction().add(R.id.main_frame,fragmentTwo).commit();
                        }
                        if(fragmentOne!=null){
                            Log.i("FeedFragment","5");

                            fragmentManager.beginTransaction().hide(fragmentOne).commit();

                        }
                        if(fragmentTwo!=null){
                            Log.i("FeedFragment","6");

                            fragmentManager.beginTransaction().show(fragmentTwo).commit();

                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFab();
            }
        });

        fab_sub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFab();
                Intent intent = new Intent(getActivity(), FeedWriteActivity.class);
                //각 카테고리명 넘겨주기
                if(categoryName.equals("기숙사와 밥")){
                    intent.putExtra("code",1);

                }else if(categoryName.equals("스포츠와 게임")){
                    intent.putExtra("code",2);

                }
                startActivity(intent);
            }
        });

        fab_sub2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFab();
                //레이아웃 뷰 전환
                int numberOfColumns = 2; // 한줄에 5개의 컬럼을 추가합니다.
                mGridLayoutManager = new GridLayoutManager(getContext(), numberOfColumns);
                mLinearLayoutManager = new LinearLayoutManager(getContext());
                //각 카테고리를 눌렀을 경우 if문으로 구분하여 Grid와 Line 스위칭
                if(switch_value==0){

                    // true이면 Grid로
                    if(Grid_Linear) {
                        fragmentOne.mMainRecyclerView.setLayoutManager(mGridLayoutManager);
                        Grid_Linear=false;

                        //false 이면 Line으로
                    }else if(!Grid_Linear){
                        fragmentOne.mMainRecyclerView.setLayoutManager(mLinearLayoutManager);
                        Grid_Linear=true;
                    }
                }else if(switch_value==1)
                {

                    if(Grid_Linear2) {
                        fragmentTwo.mMainRecyclerView.setLayoutManager(mGridLayoutManager);
                        Grid_Linear2 = false;
                    }else if(!Grid_Linear2){
                        fragmentTwo.mMainRecyclerView.setLayoutManager(mLinearLayoutManager);
                        Grid_Linear2 = true;
                    }

                }
            }
        });

        //플로팅 버튼
        /*rootView.findViewById(R.id.main_write_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FeedWriteActivity.class);
                //각 카테고리명 넘겨주기

                startActivityForResult(intent,REQUEST_CODE);
            }
        });*/
        toolbar =rootView.findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.search);

        mSearchView = (SearchView) toolbar.getMenu().findItem(R.id.action_search).getActionView();
        Log.i("searchView2","searchVIew2 "+mSearchView);
        return rootView;
    }

    private void toggleFab() {

        if (isFabOpen) {
            fab_main.setImageResource(R.drawable.ic_add);
            fab_sub1.startAnimation(fab_close);
            fab_sub2.startAnimation(fab_close);
            fab_sub1.setClickable(false);
            fab_sub2.setClickable(false);
            isFabOpen = false;
        } else {
            fab_main.setImageResource(R.drawable.ic_close);
            fab_sub1.startAnimation(fab_open);
            fab_sub2.startAnimation(fab_open);
            fab_sub1.setClickable(true);
            fab_sub2.setClickable(true);
            isFabOpen = true;
        }

    }


    //프래그먼트 적용시키기 함수
    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }


    /*@Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.fab_main:
                toggleFab();
                break;
            case R.id.fab_sub1:
                toggleFab();
                Intent intent = new Intent(getActivity(), FeedWriteActivity.class);
                //각 카테고리명 넘겨주기
                startActivityForResult(intent,REQUEST_CODE);
                break;
            case R.id.fab_sub2:
                toggleFab();
                //레이아웃 뷰 전환
                int numberOfColumns = 2; // 한줄에 5개의 컬럼을 추가합니다.
                mGridLayoutManager = new GridLayoutManager(getContext(), numberOfColumns);
                mLinearLayoutManager = new LinearLayoutManager(getContext());
                //각 카테고리를 눌렀을 경우 if문으로 구분하여 Grid와 Line 스위칭
                if(switch_value==0){

                    // true이면 Grid로
                    if(Grid_Linear) {
                        fragmentOne.mMainRecyclerView.setLayoutManager(mGridLayoutManager);
                        Grid_Linear=false;
                    //false 이면 Line으로
                    }else if(!Grid_Linear){
                        fragmentOne.mMainRecyclerView.setLayoutManager(mLinearLayoutManager);
                        Grid_Linear=true;
                    }

                }else if(switch_value==1)
                {

                    if(Grid_Linear) {
                        fragmentTwo.mMainRecyclerView.setLayoutManager(mGridLayoutManager);
                        Grid_Linear = false;
                    }else if(!Grid_Linear){
                        fragmentTwo.mMainRecyclerView.setLayoutManager(mLinearLayoutManager);
                        Grid_Linear = true;
                    }
                }
                break;

        }
    }*/
}
