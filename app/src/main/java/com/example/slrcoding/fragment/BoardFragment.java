package com.example.slrcoding.fragment;


import android.content.Intent;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.slrcoding.BoardWriteActivity;
import com.example.slrcoding.FeedWriteActivity;
import com.example.slrcoding.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A simple {@link Fragment} subclass.
 */
public class BoardFragment extends Fragment {
    // spinner
    Spinner spinner;
    private FragmentManager board_FragmentManager;

    Board_Child_FragmentOne board_FragmentOne;
    Board_Child_FragmentTwo board_FragmentTwo;

    //에니메이션
    private Animation bod_open, bod_close;
    private boolean isBodOpen = false;

    //플로팅버튼
    private FloatingActionButton bod_main, bod_sub1, bod_sub2;
    public int board_switch_value;
    public static final int REQUEST_CODE = 1000;
    String board_categoryName;

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
        bod_sub2 = (FloatingActionButton) rootView.findViewById(R.id.bod_sub2);

        //스피너 적용
        ArrayAdapter<String> ad = new ArrayAdapter<>(getActivity(),
                R.layout.custom_board_spinner,
                getResources().getStringArray(R.array.bod_fragments));
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(ad);

        board_FragmentManager = getChildFragmentManager();
        board_FragmentOne = new Board_Child_FragmentOne();
        board_FragmentManager.beginTransaction().replace(R.id.bod_main_frame,board_FragmentOne).commit();

        //스피너 이벤트 처리 클릭시 자식으로 이동
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        board_categoryName = "책"; //카테고리 넘겨주기 위함.
                        Toast.makeText(getContext(), "categoryName: "+board_categoryName, Toast.LENGTH_SHORT).show();

                        board_switch_value=0;
                        if(board_FragmentOne == null){
                            board_FragmentOne = new Board_Child_FragmentOne();
                            board_FragmentManager.beginTransaction().add(R.id.bod_main_frame,board_FragmentOne).commit();

                        }
                        if(board_FragmentOne!=null){
                            board_FragmentManager.beginTransaction().show(board_FragmentOne).commit();

                        }
                        if(board_FragmentTwo!=null){
                            board_FragmentManager.beginTransaction().hide(board_FragmentTwo).commit();
                        }
                        break;
                    case 1:
                        board_categoryName="의류";
                        Toast.makeText(getContext(), "categoryName: "+board_categoryName, Toast.LENGTH_SHORT).show();
                        board_switch_value=1;
                        if(board_FragmentTwo == null){
                            board_FragmentTwo = new Board_Child_FragmentTwo();
                            board_FragmentManager.beginTransaction().add(R.id.bod_main_frame,board_FragmentTwo).commit();
                        }
                        if(board_FragmentOne!=null){
                            board_FragmentManager.beginTransaction().hide(board_FragmentOne).commit();

                        }
                        if(board_FragmentTwo!=null){
                            board_FragmentManager.beginTransaction().show(board_FragmentTwo).commit();

                        }
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
                if(board_categoryName.equals("책")){
                    intent.putExtra("code",1);

                }else if(board_categoryName.equals("의류")){
                    intent.putExtra("code",2);

                }
                startActivity(intent);
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
