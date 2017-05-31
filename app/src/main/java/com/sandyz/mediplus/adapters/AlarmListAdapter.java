package com.sandyz.mediplus.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sandyz.mediplus.beans.DrugsData;
import com.sandyz.mediplus.R;

import java.util.ArrayList;

/**
 * Created by santosh on 31-05-2017.
 */

public class AlarmListAdapter extends BaseAdapter {

    private ArrayList<DrugsData> drugsDatas;
    private Context context;
    private LayoutInflater layoutInflater;

    public AlarmListAdapter(ArrayList<DrugsData> drugsDatas, Context context) {
        this.drugsDatas = drugsDatas;
        this.context = context;
        this.layoutInflater = layoutInflater.from(context);
    }



    @Override
    public int getCount() {
        return drugsDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return drugsDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public void setDrugsDatasList(ArrayList<DrugsData> mTaskArrayList) {
        this.drugsDatas = mTaskArrayList;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = layoutInflater.inflate(R.layout.alarm_list_row, parent, false);

        TextView name = (TextView) convertView.findViewById(R.id.alarm_name_row);
        TextView date = (TextView) convertView.findViewById(R.id.alam_date_row);
        TextView time = (TextView) convertView.findViewById(R.id.alarm_time_row);

        DrugsData data = drugsDatas.get(position);
        name.setText(data.getAlarm_name());
        date.setText(data.getDate());
        time.setText(data.getTime());


        return convertView;
    }
}
