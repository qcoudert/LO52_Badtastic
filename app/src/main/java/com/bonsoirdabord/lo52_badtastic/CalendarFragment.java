package com.bonsoirdabord.lo52_badtastic;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidGridAdapter;
import com.roomorama.caldroid.CalendarHelper;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import hirondelle.date4j.DateTime;

public class CalendarFragment extends CaldroidFragment {

    public static final String SESSION_COUNT = "sessionCount";

    private final Map<DateTime, Integer> sessionCount = new HashMap<>();

    @Override
    public CaldroidGridAdapter getNewDatesGridAdapter(int month, int year) {
        return new CalendarGridAdapter(getActivity(), month, year, getCaldroidData(), extraData);
    }

    @Override
    public Map<String, Object> getCaldroidData() {
        Map<String, Object> ret = super.getCaldroidData();
        ret.put(SESSION_COUNT, sessionCount);

        return ret;
    }

    public void clearSessionCount() {
        sessionCount.clear();
    }

    public void incrementSessionCountForDate(Date date) {
        DateTime key = CalendarHelper.convertDateToDateTime(date);
        Integer curVal = sessionCount.get(key);

        if(curVal == null)
            sessionCount.put(key, 1);
        else
            sessionCount.put(key, curVal + 1);
    }

    public void decrementSessionCountForDate(Date date) {
        DateTime key = CalendarHelper.convertDateToDateTime(date);
        Integer curVal = sessionCount.get(key);

        if(curVal != null && curVal > 0)
            sessionCount.put(key, curVal - 1);
    }

    public void setSessionCountForDate(int sessCount, Date date) {
        sessionCount.put(CalendarHelper.convertDateToDateTime(date), sessCount);
    }

}
