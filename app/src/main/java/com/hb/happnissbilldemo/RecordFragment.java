package com.hb.happnissbilldemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.happnissbilldemo.dialog.AddRecord;
import com.hb.happnissbilldemo.dialog.SelectParam;
import com.hb.happnissbilldemo.rest.FamilyInfo;
import com.hb.happnissbilldemo.rest.Record;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RecordFragment extends Fragment implements
        OnParamSelectedListener, OnAddRecordListener {

    private FamilyInfo mFamilyInfo;
    private String mName;
    private String mPassword;
    private boolean mAddingRecord = false;
    private ListView mRecordList;
    private TextView mSum;
    private RecordAdapter mRecordAdapter;

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
        SharedPreferences sp = getContext().getSharedPreferences("hbd", Context.MODE_PRIVATE);
        mName = sp.getString("username", "");
        mPassword = sp.getString("password", "");

        getFamilyInfo();

        View view = inflater.inflate(R.layout.fragment_record_list, container, false);
        mRecordList = (ListView) view.findViewById(R.id.record_list);
        mSum = (TextView) view.findViewById(R.id.sum);

        if (mRecordAdapter != null) {
            mRecordList.setAdapter(mRecordAdapter);

            float sum = 0;
            for (int i = 0; i < mRecordAdapter.getCount(); i++) {
                Record r = (Record) mRecordAdapter.getItem(i);
                sum = sum + r.getAmount();
            }

            mSum.setText(String.format(Locale.getDefault(), "%s:%.2f"
                    , getContext().getResources().getString(R.string.prompt_sum), sum));
        }

        Button btn = (Button) view.findViewById(R.id.get_record);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] members;
                String[] types;

                if (mFamilyInfo != null) {
                    members = mFamilyInfo.getMembers();
                    types = mFamilyInfo.getTypes();
                } else {
                    members = new String[]{mName};
                    types = new String[0];
                }
                SelectParam.newInstance(members, types, RecordFragment.this).show(getFragmentManager(), "");
            }
        });

        btn = (Button) view.findViewById(R.id.add_record);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] types;

                if (mFamilyInfo != null) {
                    types = mFamilyInfo.getTypes();
                } else {
                    types = new String[0];
                }
                AddRecord.newInstance(types, RecordFragment.this).show(getFragmentManager(), "");
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

        getRecords(ml, tl, start, end, 0, 1000);
    }

    private void getFamilyInfo() {
        mFamilyInfo = null;
        SharedPreferences sp = getContext().getSharedPreferences("hbd", Context.MODE_PRIVATE);
        String name = sp.getString("username", "");
        String password = sp.getString("password", "");

        HappinessBillService service = RetrofitFactory.getRetrofitService();
        Call<FamilyInfo> c = service.getFamilyInfo(name, password);

        c.enqueue(new Callback<FamilyInfo>() {
            @Override
            public void onResponse(Call<FamilyInfo> call, Response<FamilyInfo> response) {
                if (response.isSuccessful()) {
                    mFamilyInfo = response.body();
                }
            }

            @Override
            public void onFailure(Call<FamilyInfo> call, Throwable t) {

            }
        });
    }

    private void getRecords(List<String> members, List<String> types
            , Calendar startDate, Calendar endDate
            , int start, int count) {
        HappinessBillService service = RetrofitFactory.getRetrofitService();
        Call<List<Record>> c = service.getRecords(mName, mPassword
                , members == null ? null : members.toArray(new String[0])
                , types == null ? null : types.toArray(new String[0])
                , new Timestamp(startDate.getTimeInMillis())
                , new Timestamp(endDate.getTimeInMillis())
                , start, count);
        c.enqueue(new Callback<List<Record>>() {
            @Override
            public void onResponse(Call<List<Record>> call, Response<List<Record>> response) {
                if (!response.isSuccessful()) return;

                mRecordAdapter = new RecordAdapter(getContext(), response.body());
                mRecordList.setAdapter(mRecordAdapter);

                float sum = 0;
                for (Record r : response.body()) {
                    sum = sum + r.getAmount();
                }

                mSum.setText(String.format(Locale.getDefault(), "%s:%.2f"
                        , getContext().getResources().getString(R.string.prompt_sum), sum));
            }

            @Override
            public void onFailure(Call<List<Record>> call, Throwable t) {

            }
        });
    }

    @Override
    public void onAddRecord(float amount, String type, String comment) {
        if (mAddingRecord) {
            Toast.makeText(getContext(), R.string.prompt_busy, Toast.LENGTH_SHORT).show();
            return;
        }

        mAddingRecord = true;
        HappinessBillService service = RetrofitFactory.getRetrofitService();
        Call<ResponseBody> c = service.addRecord(mName, mPassword, amount, type, comment);
        c.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), R.string.prompt_add_record_succeed, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), R.string.prompt_add_record_failed, Toast.LENGTH_SHORT).show();
                }
                mAddingRecord = false;
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), R.string.prompt_add_record_failed, Toast.LENGTH_SHORT).show();
                mAddingRecord = false;
            }
        });
    }
}
