package com.hb.happnissbilldemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by HB on 2017/5/26.
 */

public class RecordAdapter extends BaseAdapter {

    final private LayoutInflater mInflater;

    public RecordAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return 100;
    }

    @Override
    public Object getItem(int position) {
        return position;
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

        TextView tv = (TextView) convertView.findViewById(R.id.text);
        tv.setText("" + position);

        return convertView;
    }
}
