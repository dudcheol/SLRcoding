package com.example.slrcoding.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.slrcoding.FeedWriteActivity;
import com.example.slrcoding.R;
//부모 피드 프래그먼트
//이정찬
/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment implements View.OnClickListener {
    //피드 프래그먼트 여기서 스피너에 아이템 클릭시 자식 프래그먼트로 넘어감.
    Spinner spinner;
    Feed_Child_FragmentOne fragmentOne;
    Feed_Child_FragmentTwo fragmentTwo;

    private GridLayoutManager mGridLayoutManager;
    //에니메이션
    private Animation fab_open, fab_close;
    private boolean isFabOpen = false;
    //플로팅버튼
    private FloatingActionButton fab_main, fab_sub1, fab_sub2;
    public int switch_value;
    public static final int REQUEST_CODE = 1000;
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

        fragmentOne = new Feed_Child_FragmentOne();
        fragmentTwo = new Feed_Child_FragmentTwo();
        //스피너 적용
        ArrayAdapter<String> ad = new ArrayAdapter<>(getActivity(),
                R.layout.custom_spinner,
                getResources().getStringArray(R.array.fragments));
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);
        //스피너 이벤트 처리 클릭시 자식으로 이동
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        switch_value=0;
                        setFragment(fragmentOne);

                        break;
                    case 1:
                        switch_value=1;
                        setFragment(fragmentTwo);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        fab_main.setOnClickListener(this);

        fab_sub1.setOnClickListener(this);

        fab_sub2.setOnClickListener(this);

        //플로팅 버튼
        /*rootView.findViewById(R.id.main_write_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FeedWriteActivity.class);
                //각 카테고리명 넘겨주기

                startActivityForResult(intent,REQUEST_CODE);
            }
        });*/
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


    @Override
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
                int numberOfColumns = 2; // 한줄에 5개의 컬럼을 추가합니다.
                mGridLayoutManager = new GridLayoutManager(getContext(), numberOfColumns);
                if(switch_value==0){
                    fragmentOne.mMainRecyclerView.setLayoutManager(mGridLayoutManager);
                }else if(switch_value==1)
                {
                    fragmentTwo.mMainRecyclerView.setLayoutManager(mGridLayoutManager);
                }


                break;

        }
    }
}
