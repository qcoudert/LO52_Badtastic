package com.bonsoirdabord.lo52_badtastic;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidGridAdapter;

import java.util.Map;

import hirondelle.date4j.DateTime;

public class CalendarGridAdapter extends CaldroidGridAdapter {

    public CalendarGridAdapter(Context context, int month, int year,
                               Map<String, Object> caldroidData, Map<String, Object> extraData) {
        super(context, month, year, caldroidData, extraData);
    }

    private static String sessionCountToString(int cnt) {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < cnt; i++)
            sb.append('\u2022');

        return sb.toString();
    }

    @Override
    public View getView(int position, View cell, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(cell == null)
            cell = inflater.inflate(R.layout.layout_calendar_cell, null);

        int topPadding = cell.getPaddingTop();
        int leftPadding = cell.getPaddingLeft();
        int bottomPadding = cell.getPaddingBottom();
        int rightPadding = cell.getPaddingRight();

        Map<DateTime, Integer> sessionCount = (Map<DateTime, Integer>) caldroidData.get(CalendarFragment.SESSION_COUNT);
        TextView tv1 = cell.findViewById(R.id.day_num);
        TextView tv2 = cell.findViewById(R.id.num_sess);
        DateTime date = datetimeList.get(position);
        Integer sessCount = sessionCount.get(date);
        int darkerGray = context.getColor(com.caldroid.R.color.caldroid_darker_gray);

        if(date.getMonth() == month)
            tv1.setTextColor(Color.BLACK);
        else
            tv1.setTextColor(darkerGray);

        if(date.equals(today))
            cell.setBackgroundResource(com.caldroid.R.drawable.red_border);
        else
            cell.setBackgroundResource(com.caldroid.R.drawable.cell_bg);

        tv1.setText("" + date.getDay());

        if(sessCount == null || sessCount == 0)
            tv2.setVisibility(View.GONE);
        else {
            tv2.setVisibility(View.VISIBLE);
            tv2.setText(sessionCountToString(sessCount));
        }

        cell.setPadding(leftPadding, topPadding, rightPadding, bottomPadding);
        setCustomResources(date, cell, tv1);

        return cell;
    }

}
