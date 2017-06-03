package com.hb.happnissbilldemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.hb.happnissbilldemo.dialog.SelectParam;
import com.hb.happnissbilldemo.rest.FamilyInfo;
import com.hb.happnissbilldemo.rest.Record;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;


public class RecordFragment extends Fragment implements OnParamSelectedListener {

    private List<String> mMembers;
    private Calendar mStartDate;
    private Calendar mEndDate;

    public RecordFragment() {

    }

    public static RecordFragment newInstance() {
        RecordFragment fragment = new RecordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record_list, container, false);

        ListView lv = (ListView) view.findViewById(R.id.record_list);
        lv.setAdapter(new RecordAdapter(getContext()));

        mMembers = null;
        mStartDate = Calendar.getInstance();
        mStartDate.set(Calendar.HOUR_OF_DAY, 0);
        mStartDate.set(Calendar.MINUTE, 0);
        mStartDate.set(Calendar.SECOND, 0);
        mEndDate = Calendar.getInstance();
        mEndDate.set(Calendar.HOUR_OF_DAY, 23);
        mEndDate.set(Calendar.MINUTE, 59);
        mEndDate.set(Calendar.SECOND, 59);

        Button btn = (Button) view.findViewById(R.id.get_record);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GetFamilyTask().execute((Void) null);
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onParamSelected(String[] members, boolean[] memberSelected
            , String[] types, boolean[] typeSelected, Calendar start, Calendar end) {
        ArrayList<String> ml;
        if (memberSelected[members.length]) {
            ml = null;
        } else {
            ml = new ArrayList<>();
            for (int i = 0; i < members.length; i++) {
                if (memberSelected[i]) ml.add(members[i]);
            }
        }

        ArrayList<String> tl;
        if (typeSelected[types.length]) {
            tl = null;
        } else {
            tl = new ArrayList<>();
            for (int i = 0; i < types.length; i++) {
                if (typeSelected[i]) tl.add(types[i]);
            }
        }

        new GetRecordTask(ml, tl, start, end, 0, 1000).execute((Void) null);
    }


    private class GetFamilyTask extends AsyncTask<Void, Void, FamilyInfo> {

        private final String mName;
        private final String mPassword;

        GetFamilyTask() {
            SharedPreferences sp = getContext().getSharedPreferences("hbd", Context.MODE_PRIVATE);
            mName = sp.getString("username", "");
            mPassword = sp.getString("password", "");
        }

        @Override
        protected FamilyInfo doInBackground(Void... params) {
            HappinessBillService service = RetrofitFactory.getRetrofitService();

            try {
                Call<FamilyInfo> c = service.getFamilyInfo(mName, mPassword);
                Response<FamilyInfo> r = c.execute();

                if (r.isSuccessful()) {
                    return r.body();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(final FamilyInfo family) {
            String[] members;
            String[] types;

            if (family != null) {
                members = family.getMembers();
                types = family.getTypes();
            } else {
                members = new String[]{mName};
                types = new String[0];
            }
            SelectParam.newInstance(members, types, RecordFragment.this).show(getFragmentManager(), "");
        }
    }

    private class GetRecordTask extends AsyncTask<Void, Void, List<Record>> {

        private final String mName;
        private final String mPassword;
        private final List<String> mMembers;
        private final List<String> mTypes;
        private final Calendar mStartDate;
        private final Calendar mEndDate;
        private final int mStart;
        private final int mCount;

        GetRecordTask(List<String> members, List<String> types, Calendar startDate, Calendar endDate
                , int start, int count) {
            mMembers = members;
            mTypes = types;
            mStartDate = startDate;
            mEndDate = endDate;
            mStart = start;
            mCount = count;
            SharedPreferences sp = getContext().getSharedPreferences("hbd", Context.MODE_PRIVATE);
            mName = sp.getString("username", "");
            mPassword = sp.getString("password", "");
        }

        @Override
        protected List<Record> doInBackground(Void... params) {
            HappinessBillService service = RetrofitFactory.getRetrofitService();

            try {
                Call<List<Record>> c = service.getRecords(mName, mPassword
                        , mMembers == null ? null : mMembers.toArray(new String[0])
                        , mTypes == null ? null : mTypes.toArray(new String[0])
                        , new Timestamp(mStartDate.getTimeInMillis())
                        , new Timestamp(mEndDate.getTimeInMillis())
                        , mStart, mCount);
                Response<List<Record>> r = c.execute();

                if (r.isSuccessful()) {
                    return r.body();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(final List<Record> records) {

        }
    }
}
