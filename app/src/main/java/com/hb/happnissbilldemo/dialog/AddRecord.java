package com.hb.happnissbilldemo.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.Toast;

import com.hb.happnissbilldemo.listener.OnAddRecordListener;
import com.hb.happnissbilldemo.R;

/**
 * Created by HB on 2017/6/4.
 */

public class AddRecord extends DialogFragment {

    private EditText mAmount;
    private String mType;
    private EditText mComment;
    private OnAddRecordListener mListener;
    private CheckBox mIncome;

    static public AddRecord newInstance(String[] types, OnAddRecordListener listener) {
        AddRecord ar = new AddRecord();
        Bundle args = new Bundle();
        args.putStringArray("types", types);
        ar.setArguments(args);
        ar.mListener = listener;
        return ar;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_record, container, false);

        Bundle args = getArguments();
        final String[] types = args.getStringArray("types");

        Spinner sp = (Spinner) v.findViewById(R.id.type);
        TableRow tr = (TableRow) v.findViewById(R.id.type_row);
        if (types.length == 0) {
            tr.setVisibility(View.GONE);
            mType = null;
        } else {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext()
                    , android.R.layout.simple_spinner_item, types);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            sp.setAdapter(adapter);
            mType = types[0];
        }

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mType = types[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        mAmount = (EditText) v.findViewById(R.id.amount);
        mComment = (EditText) v.findViewById(R.id.comment);
        mIncome = (CheckBox) v.findViewById(R.id.income);

        Button btn = (Button) v.findViewById(R.id.ok);
        btn.setOnClickListener(mButtonClickListener);

        btn = (Button) v.findViewById(R.id.cancel);
        btn.setOnClickListener(mButtonClickListener);

        return v;
    }

    private View.OnClickListener mButtonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == R.id.ok) {
                try {
                    mListener.onAddRecord(
                            Float.parseFloat(mAmount.getText().toString()) * (mIncome.isChecked() ? 1 : -1),
                            mType,
                            mComment.getText().toString()
                    );
                } catch (Exception e) {
                    Toast.makeText(getContext(), R.string.prompt_add_record_failed, Toast.LENGTH_SHORT).show();
                }
            }
            dismiss();
        }
    };
}
