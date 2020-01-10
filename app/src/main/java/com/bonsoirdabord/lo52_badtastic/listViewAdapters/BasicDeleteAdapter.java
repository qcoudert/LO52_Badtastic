package com.bonsoirdabord.lo52_badtastic.listViewAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.bonsoirdabord.lo52_badtastic.R;
import com.bonsoirdabord.lo52_badtastic.beans.GroupTraining;

import java.util.ArrayList;
import java.util.List;

public class BasicDeleteAdapter extends BaseAdapter implements ListAdapter {

    private List<String> content = new ArrayList<>();
    private List<GroupTraining> groups = new ArrayList<>();
    private Context context;

    public BasicDeleteAdapter(List<GroupTraining> groups, List<String> content, Context context) {
        this.content = content;
        this.context = context;
        this.groups = groups;
    }

    @Override
    public int getCount() {
        return content.size();
    }

    @Override
    public Object getItem(int i) {
        return content.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View viewS = view;
        if(viewS == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            viewS = inflater.inflate(R.layout.basic_list_member_layout, null);
        }

        TextView tv = (TextView)viewS.findViewById(R.id.member_tv_list_view);
        tv.setText((String)getItem(i));

        Button deleteBtn = (Button)viewS.findViewById(R.id.delete_button_list_view);
        deleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content.remove(i);
                groups.remove(i);
                notifyDataSetChanged();
            }
        });

        return viewS;
    }

    public void add(String s) {
        content.add(s);
    }
}
