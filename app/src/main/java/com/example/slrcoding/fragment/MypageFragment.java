package com.example.slrcoding.fragment;


import android.app.ExpandableListActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.slrcoding.Adapter.MypageListAdapter;
import com.example.slrcoding.R;
import com.example.slrcoding.VO.ChildListData;
import com.example.slrcoding.VO.ParentListData;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
//최민철
public class MypageFragment extends Fragment {

    String submenu1[] = {"내가 쓴 글", "댓글 단 글", "스크랩"};
    String submenu2[] = {"프로필 이미지 변경", "닉네임 변경", "로그 아웃"};
    String submenu3[] = {"알림 설정", "메시지 설정"};
    String submenu4[] = {"문의하기", "공지사항", "커뮤니티 이용규칙"};

    private ArrayList<ParentListData> parentListData;
    private ExpandableListView parentListView;
    public MypageFragment() {
        // Required empty public constructor
    }
    public MypageListAdapter adpater;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View MyPage_View = inflater.inflate(R.layout.fragment_mypage, container, false);
        parentListView = (ExpandableListView)MyPage_View.findViewById(R.id.mypage_parentListView);
        registerForContextMenu(parentListView);

        Display newDisplay = getActivity().getWindowManager().getDefaultDisplay();
        int width = newDisplay.getWidth();
        parentListView.setIndicatorBounds(width-200, width);

        // arrayList
        parentListData = new ArrayList<>();

        ParentListData parentListData_community = new ParentListData();
        ParentListData parentListData_account = new ParentListData();
        ParentListData parentListData_setting = new ParentListData();
        ParentListData parentListData_info = new ParentListData();
        parentListData_community.setName("커뮤니티");
        parentListData_account.setName("계정");
        parentListData_setting.setName("앱 설정");
        parentListData_info.setName("앱 정보");

        parentListData.add(parentListData_community);
        parentListData.add(parentListData_account);
        parentListData.add(parentListData_setting);
        parentListData.add(parentListData_info);

        // submenu1 등록
        for(int i=0;i<submenu1.length;i++){
            ChildListData childListData1 = new ChildListData();
            childListData1.setTitle(submenu1[i]);
            parentListData.get(0).childListData.add(childListData1);
        }

        // submenu2 등록
        for(int i=0;i<submenu2.length;i++){
            ChildListData childListData2 = new ChildListData();
            childListData2.setTitle(submenu2[i]);
            parentListData.get(1).childListData.add(childListData2);
        }

        // submenu3 등록
        for(int i=0;i<submenu3.length;i++){
            ChildListData childListData3 = new ChildListData();
            childListData3.setTitle(submenu3[i]);
            parentListData.get(2).childListData.add(childListData3);
        }

        // submenu4 등록
        for(int i=0;i<submenu4.length;i++){
            ChildListData childListData4 = new ChildListData();
            childListData4.setTitle(submenu4[i]);
            parentListData.get(3).childListData.add(childListData4);
        }

        adpater = new MypageListAdapter(getActivity(), parentListData);
        parentListView.setAdapter(adpater);

        // Inflate the layout for this fragment
        return MyPage_View;
    }

}
