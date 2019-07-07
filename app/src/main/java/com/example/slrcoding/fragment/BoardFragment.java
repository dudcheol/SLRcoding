package com.example.slrcoding.fragment;


import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.slrcoding.BoardWriteActivity;
import com.example.slrcoding.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {
    // spinner
    Spinner spinner;
    Board_Child_FragmentOne board_FragmentOne;


    //에니메이션
    private Animation bod_open, bod_close;
    private boolean isBodOpen = false;

    //플로팅버튼
    private FloatingActionButton bod_main, bod_sub1;
    public int board_switch_value;
    public static final int REQUEST_CODE = 1000;

    public BoardFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_board, container, false);
        //스피너와 자식 프래그먼트 아이디로 얻어오기
        spinner = rootView.findViewById(R.id.bod_spinner);

        bod_open = AnimationUtils.loadAnimation(getContext(), R.anim.fab_open);
        bod_close = AnimationUtils.loadAnimation(getContext(), R.anim.fab_close);

        bod_main = (FloatingActionButton) rootView.findViewById(R.id.bod_main);
        bod_sub1 = (FloatingActionButton) rootView.findViewById(R.id.bod_sub1);

        board_FragmentOne = new Board_Child_FragmentOne();

        //
        //스피너 적용
        ArrayAdapter<String> bod_spinner = new ArrayAdapter<>(getActivity(),
                R.layout.custom_board_spinner,
                getResources().getStringArray(R.array.bod_fragments));
        // android.R.layout~ 기본제공
        bod_spinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(bod_spinner);

        //스피너 이벤트 처리 클릭시 자식으로 이동
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        board_switch_value=0;
                        setFragment(board_FragmentOne);

                        break;
                    case 1:
                        board_switch_value=1;
                        setFragment(board_FragmentOne);
                        break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //

        bod_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFab();
            }
        });

        bod_sub1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFab();
                Intent intent = new Intent(getActivity(), BoardWriteActivity.class);
                //각 카테고리명 넘겨주기
                startActivityForResult(intent,REQUEST_CODE);
            }
        });

        return rootView;
    }
    private void toggleFab() {

        if (isBodOpen) {
            bod_main.setImageResource(R.drawable.ic_add);
            bod_sub1.startAnimation(bod_close);
            bod_sub1.setClickable(false);
            isBodOpen = false;
        } else {
            bod_main.setImageResource(R.drawable.ic_close);
            bod_sub1.startAnimation(bod_open);
            bod_sub1.setClickable(true);
            isBodOpen = true;
        }
    }

    //프래그먼트 적용시키기 함수
    public void setFragment(Fragment fragment){
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.bod_main_frame,fragment);
        fragmentTransaction.commit();
    }

}
