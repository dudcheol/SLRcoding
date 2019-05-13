package com.example.slrcoding.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.slrcoding.R;
//부모 피드 프래그먼트
//이정찬
/**
 * A simple {@link Fragment} subclass.
 */
public class FeedFragment extends Fragment {
    //피드 프래그먼트 여기서 스피너에 아이템 클릭시 자식 프래그먼트로 넘어감.
    Spinner spinner;
    Feed_Child_FragmentOne fragmentOne;
    Feed_Child_FragmentTwo fragmentTwo;
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
                        setFragment(fragmentOne);
                        break;
                    case 1:
                        setFragment(fragmentTwo);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return rootView;
    }
    //프래그먼트 적용시키기 함수
    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame,fragment);
        fragmentTransaction.commit();
    }

}
