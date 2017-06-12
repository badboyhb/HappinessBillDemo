package com.hb.happnissbilldemo.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.happnissbilldemo.R;
import com.hb.happnissbilldemo.activity.LoginActivity;
import com.hb.happnissbilldemo.activity.MainActivity;
import com.hb.happnissbilldemo.rest.HappinessBillService;
import com.hb.happnissbilldemo.rest.RetrofitFactory;
import com.hb.happnissbilldemo.rest.UserInfo;
import com.hb.happnissbilldemo.util.Hash;

import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserFragment extends Fragment {

    private TextView mTvName;
    private TextView mTvEmail;
    private TextView mTvPhoneNumber;
    private TextView mTvFamily;
    private TextView mTvParent;

    private EditText mEtEmail;
    private EditText mEtPhoneNumber;
    private EditText mEtOldPassword;
    private EditText mEtNewPassword;
    private EditText mEtConfirm;

    private TableRow mFamilyRow;
    private TableRow mParentRow;
    private TableRow mOldPasswordRow;
    private TableRow mNewPasswordRow;
    private TableRow mConfirmRow;
    private TableRow mButtonRow;

    private Button mBtnChange;
    private Button mBtnOk;
    private Button mBtnCancel;

    private String mName;
    private String mPassword;

    private UserInfo mUserInfo;

    public UserFragment() {
    }


    public static UserFragment newInstance(String param1, String param2) {
        UserFragment fragment = new UserFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences sp = getContext().getSharedPreferences("hbd", Context.MODE_PRIVATE);
        mName = sp.getString("username", "");
        mPassword = sp.getString("password", "");

        View view = inflater.inflate(R.layout.fragment_user, container, false);

        mTvName = (TextView) view.findViewById(R.id.name);
        mTvEmail = (TextView) view.findViewById(R.id.email);
        mTvPhoneNumber = (TextView) view.findViewById(R.id.phonenumber);
        mTvFamily = (TextView) view.findViewById(R.id.family);
        mTvParent = (TextView) view.findViewById(R.id.parent);

        mEtEmail = (EditText) view.findViewById(R.id.edit_email);
        mEtPhoneNumber = (EditText) view.findViewById(R.id.edit_phonenumber);
        mEtOldPassword = (EditText) view.findViewById(R.id.edit_oldpassword);
        mEtNewPassword = (EditText) view.findViewById(R.id.edit_newpassword);
        mEtConfirm = (EditText) view.findViewById(R.id.edit_confirm);

        mFamilyRow = (TableRow) view.findViewById(R.id.family_row);
        mParentRow = (TableRow) view.findViewById(R.id.parent_row);
        mOldPasswordRow = (TableRow) view.findViewById(R.id.oldpassword_row);
        mNewPasswordRow = (TableRow) view.findViewById(R.id.newpassword_row);
        mConfirmRow = (TableRow) view.findViewById(R.id.confirm_row);
        mButtonRow = (TableRow) view.findViewById(R.id.button_row);

        mBtnChange = (Button) view.findViewById(R.id.change);
        mBtnOk = (Button) view.findViewById(R.id.ok);
        mBtnCancel = (Button) view.findViewById(R.id.cancel);

        setUserInfo();
        getUserInfo();

        mBtnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mTvEmail.setVisibility(View.GONE);
                mEtEmail.setVisibility(View.VISIBLE);
                mEtEmail.setText(mTvEmail.getText());

                mTvPhoneNumber.setVisibility(View.GONE);
                mEtPhoneNumber.setVisibility(View.VISIBLE);
                mEtPhoneNumber.setText(mTvPhoneNumber.getText());

                mFamilyRow.setVisibility(View.GONE);
                mParentRow.setVisibility(View.GONE);
                mBtnChange.setVisibility(View.GONE);

                mOldPasswordRow.setVisibility(View.VISIBLE);
                mNewPasswordRow.setVisibility(View.VISIBLE);
                mConfirmRow.setVisibility(View.VISIBLE);
                mButtonRow.setVisibility(View.VISIBLE);
            }
        });

        mBtnOk.setOnClickListener(mOkCancelClickListener);
        mBtnCancel.setOnClickListener(mOkCancelClickListener);

        Button btn = (Button) view.findViewById(R.id.logout);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sp = getContext().getSharedPreferences("hbd", Context.MODE_PRIVATE);
                sp.edit().putString("username", "").putString("password", "").apply();
                Intent i = new Intent(getContext(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        return view;
    }

    private boolean checkUserInfo() {
        String str = mEtEmail.getText().toString();
        if (!Pattern.compile("[\\w\\.]+@[\\w\\.]+").matcher(str).matches()) {
            mEtEmail.setError(getString(R.string.error_invalid_email));
            mEtEmail.requestFocus();
            return false;
        }

        str = mEtPhoneNumber.getText().toString();
        if (!Pattern.compile("\\d{4,20}").matcher(str).matches()) {
            mEtPhoneNumber.setError(getString(R.string.error_invalid_phonenumber));
            mEtPhoneNumber.requestFocus();
            return false;
        }

        String pwd = mEtNewPassword.getText().toString();
        if (pwd.length() < 6 || pwd.length() > 64) {
            mEtNewPassword.setError(getString(R.string.error_invalid_password));
            mEtNewPassword.requestFocus();
            return false;
        }

        String confirm = mEtConfirm.getText().toString();
        if (!pwd.equals(confirm)) {
            mEtConfirm.setError(getString(R.string.error_invalid_confirmpassword));
            mEtConfirm.requestFocus();
            return false;
        }

        return true;
    }

    View.OnClickListener mOkCancelClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            if (v.getId() == R.id.ok) {
                if (!checkUserInfo()) return;

                changeUserInfo();
            }

            mTvEmail.setVisibility(View.VISIBLE);
            mEtEmail.setVisibility(View.GONE);

            mTvPhoneNumber.setVisibility(View.VISIBLE);
            mEtPhoneNumber.setVisibility(View.GONE);

            mFamilyRow.setVisibility(View.VISIBLE);
            mParentRow.setVisibility(View.VISIBLE);
            mBtnChange.setVisibility(View.VISIBLE);

            mOldPasswordRow.setVisibility(View.GONE);
            mNewPasswordRow.setVisibility(View.GONE);
            mConfirmRow.setVisibility(View.GONE);
            mButtonRow.setVisibility(View.GONE);
        }
    };

    private void changeUserInfo() {
        HappinessBillService service = RetrofitFactory.getRetrofitService();

        final String oldPassword = Hash.getHash(mName + mEtOldPassword.getText());
        final String newPassword = Hash.getHash(mName + mEtNewPassword.getText());

        Call<ResponseBody> c = service.changeUserInfo(mName, oldPassword,
                newPassword, mEtEmail.getText().toString(),
                mEtPhoneNumber.getText().toString());

        c.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), R.string.error_change_user_info_failed,
                            Toast.LENGTH_SHORT).show();
                    return;
                }

                mPassword = newPassword;
                SharedPreferences sp = getContext().getSharedPreferences("hbd", Context.MODE_PRIVATE);
                sp.edit().putString("password", mPassword).apply();
                getUserInfo();

                Toast.makeText(getContext(), R.string.error_change_user_info_succeed,
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), R.string.error_change_user_info_failed,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setUserInfo() {
        if (mUserInfo == null) return;
        mTvName.setText(mUserInfo.getName());
        mTvEmail.setText(mUserInfo.getEmail());
        mTvPhoneNumber.setText(mUserInfo.getPhoneNumber());
        mTvFamily.setText(mUserInfo.getFamily());
        mTvParent.setText(mUserInfo.getRole() == UserInfo.ROLE_PARENT ?
                R.string.prompt_true : R.string.prompt_false);
    }

    private void getUserInfo() {
        HappinessBillService service = RetrofitFactory.getRetrofitService();
        Call<UserInfo> c = service.getUserInfo(mName, mPassword);
        c.enqueue(new Callback<UserInfo>() {
            @Override
            public void onResponse(Call<UserInfo> call, Response<UserInfo> response) {
                if (!response.isSuccessful()) return;

                mUserInfo = response.body();
                setUserInfo();
            }

            @Override
            public void onFailure(Call<UserInfo> call, Throwable t) {
            }
        });
    }
}
