package com.hb.happnissbilldemo.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.hb.happnissbilldemo.OnParamSelectedListener;
import com.hb.happnissbilldemo.R;

import java.util.Calendar;

/**
 * Created by HB on 2017/6/3.
 */

public class SelectParam extends DialogFragment {

    private ItemAdapter mMemberAdapter;
    private ItemAdapter mTypeAdapter;
    private DatePicker mStartDate;
    private DatePicker mEndDate;
    private OnParamSelectedListener mListener;

    static public SelectParam newInstance(String[] members, String[] types, OnParamSelectedListener listener) {
        SelectParam sp = new SelectParam();
        Bundle args = new Bundle();
        args.putStringArray("members", members);
        args.putStringArray("types", types);
        sp.setArguments(args);
        sp.mListener = listener;
        return sp;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.select_param, container, false);

        Bundle args = getArguments();
        String[] members = args.getStringArray("members");
        String[] types = args.getStringArray("types");

        View tv = v.findViewById(R.id.txt_member);
        tv.setTag(v.findViewById(R.id.member_list));
        tv.setOnClickListener(mTextClickListener);

        tv = v.findViewById(R.id.txt_type);
        tv.setTag(v.findViewById(R.id.type_list));
        tv.setOnClickListener(mTextClickListener);

        tv = v.findViewById(R.id.txt_start_date);
        tv.setTag(v.findViewById(R.id.start_date));
        tv.setOnClickListener(mTextClickListener);

        tv = v.findViewById(R.id.txt_end_date);
        tv.setTag(v.findViewById(R.id.end_date));
        tv.setOnClickListener(mTextClickListener);

        mMemberAdapter = new ItemAdapter(getContext(), members);
        ListView lv = (ListView) v.findViewById(R.id.member_list);
        lv.setAdapter(mMemberAdapter);
        setListViewHeightBasedOnChildren(lv);

        mTypeAdapter = new ItemAdapter(getContext(), types);
        lv = (ListView) v.findViewById(R.id.type_list);
        lv.setAdapter(mTypeAdapter);
        setListViewHeightBasedOnChildren(lv);

        Button btn = (Button) v.findViewById(R.id.ok);
        btn.setOnClickListener(mButtonClickListener);

        btn = (Button) v.findViewById(R.id.cancel);
        btn.setOnClickListener(mButtonClickListener);

        mStartDate = (DatePicker) v.findViewById(R.id.start_date);
        mEndDate = (DatePicker) v.findViewById(R.id.end_date);

        return v;
    }

    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.ok) {
                Calendar sc = Calendar.getInstance();
                sc.set(mStartDate.getYear(), mStartDate.getMonth(), mStartDate.getDayOfMonth(), 0, 0, 0);
                sc.set(Calendar.MILLISECOND, 0);
                Calendar se = Calendar.getInstance();
                se.set(mEndDate.getYear(), mEndDate.getMonth(), mEndDate.getDayOfMonth(), 23, 59, 59);
                se.set(Calendar.MILLISECOND, 999);
                mListener.onParamSelected(mMemberAdapter.getItems(), mMemberAdapter.getSelected()
                        , mTypeAdapter.getItems(), mTypeAdapter.getSelected(), sc, se);
            }
            dismiss();
        }
    };

    private View.OnClickListener mTextClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            View tv = (View) v.getTag();
            if (tv.getVisibility() == View.VISIBLE) {
                tv.setVisibility(View.GONE);
            } else {
                tv.setVisibility(View.VISIBLE);
            }
        }
    };

    private static void setListViewHeightBasedOnChildren(ListView listView) {
        if (listView == null) return;

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        View listItem = listAdapter.getView(0, null, listView);
        listItem.measure(0, 0);
        int totalHeight = listItem.getMeasuredHeight() * listAdapter.getCount();

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }
}
