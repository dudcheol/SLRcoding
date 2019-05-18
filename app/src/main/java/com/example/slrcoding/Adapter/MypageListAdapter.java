package com.example.slrcoding.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.example.slrcoding.R;
import com.example.slrcoding.VO.ParentListData;

import java.util.ArrayList;

public class MypageListAdapter extends BaseExpandableListAdapter {
    private static final int PARENT_ROW = R.layout.mypage_parent_row;
    private static final int CHILD_ROW = R.layout.mypage_child_row;
    private Context context;
    private ArrayList<ParentListData> data;
    private LayoutInflater inflater = null;

    public MypageListAdapter(Context context, ArrayList<ParentListData> data){
        this.data=data;
        this.context=context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(PARENT_ROW, parent, false);
        }
        //여기의 convertView는 부모리스트
        TextView Mypage_Menu = (TextView) convertView.findViewById(R.id.mypage_menu_name);
        Mypage_Menu.setText(data.get(groupPosition).getName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = inflater.inflate(CHILD_ROW, parent, false);
        }
        //여기의 convertView는 자식리스트
        TextView Mypage_Submenu = (TextView) convertView.findViewById(R.id.mypage_submenu_name);
        Mypage_Submenu.setText(data.get(groupPosition).childListData.get(childPosition).getTitle());

        return convertView;
    }
    @Override
    public int getGroupCount() {
        return data.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) { return data.get(groupPosition).childListData.size(); }

    @Override
    public Object getGroup(int groupPosition) {
        return data.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return data.get(groupPosition).childListData.get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}
