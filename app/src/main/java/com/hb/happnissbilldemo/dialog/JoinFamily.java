package com.hb.happnissbilldemo.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hb.happnissbilldemo.R;
import com.hb.happnissbilldemo.listener.OnJoinFamilyDoneListener;

/**
 * Created by HB on 2017/6/12.
 */

public class JoinFamily extends DialogFragment {

    private EditText mName;
    private EditText mCode;

    private OnJoinFamilyDoneListener mListener;

    static public JoinFamily newInstance(OnJoinFamilyDoneListener listener) {
        JoinFamily jf = new JoinFamily();
        jf.mListener = listener;
        return jf;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.join_family, container, false);

        mName = (EditText) v.findViewById(R.id.family_name);
        mCode = (EditText) v.findViewById(R.id.family_code);

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
                mListener.onJoinFamilyDone(mName.getText().toString(), mCode.getText().toString());
            }
            dismiss();
        }
    };
}
