package com.hb.happnissbilldemo.fragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.hb.happnissbilldemo.R;
import com.hb.happnissbilldemo.rest.Record;

import java.text.DateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by HB on 2017/5/26.
 */

public class RecordAdapter extends BaseAdapter {

    final private LayoutInflater mInflater;
    final private List<Record> mRecordList;

    public RecordAdapter(Context context, List<Record> records) {
        mInflater = LayoutInflater.from(context);
        mRecordList = records;
    }

    @Override
    public int getCount() {
        return mRecordList.size();
    }

    @Override
    public Object getItem(int position) {
        return mRecordList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.record_item, parent, false);
        }

        Record r = mRecordList.get(position);

        TextView tv = (TextView) convertView.findViewById(R.id.name);
        tv.setText(r.getUserName());

        tv = (TextView) convertView.findViewById(R.id.amount);
        tv.setText(String.format(Locale.getDefault(), "%.2f", r.getAmount()));

        tv = (TextView) convertView.findViewById(R.id.time);
        tv.setText(DateFormat.getDateTimeInstance().format(r.getTime()));

        tv = (TextView) convertView.findViewById(R.id.type);
        tv.setText(r.getType());

        tv = (TextView) convertView.findViewById(R.id.comment);
        tv.setText(r.getComment());

        return convertView;
    }
}
