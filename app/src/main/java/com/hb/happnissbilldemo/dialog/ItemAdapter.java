package com.hb.happnissbilldemo.dialog;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.hb.happnissbilldemo.R;

import java.util.Arrays;

/**
 * Created by HB on 2017/6/3.
 */

public class ItemAdapter extends BaseAdapter {

    final private String[] mItems;
    final private boolean[] mSelected;
    final private String mAll;
    final private Context mContext;

    public ItemAdapter(Context context, String[] members) {
        mItems = members;
        mAll = context.getResources().getString(R.string.prompt_all);
        mContext = context;
        mSelected = new boolean[mItems.length + 1];
        Arrays.fill(mSelected, false);
        if (mItems.length == 0) mSelected[0] = true;
    }

    public String[] getItems() {
        return mItems;
    }

    public boolean[] getSelected() {
        return mSelected;
    }

    @Override
    public int getCount() {
        return mItems.length + 1;
    }

    @Override
    public Object getItem(int position) {
        if (position < mItems.length) return mItems[position];
        return mAll;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CheckBox cb = (CheckBox) convertView;

        if (cb == null) {
            cb = new CheckBox(mContext);
            if (getCount() > 1) {
                cb.setOnClickListener(mListener);
            } else {
                cb.setEnabled(false);
            }
        }

        cb.setTag(position);
        cb.setChecked(mSelected[position]);

        String txt = mAll;
        if (position < mItems.length) txt = mItems[position];
        cb.setText(txt);

        return cb;
    }

    private View.OnClickListener mListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int i = (Integer) v.getTag();
            CheckBox cb = (CheckBox) v;
            mSelected[i] = cb.isChecked();
        }
    };
}

