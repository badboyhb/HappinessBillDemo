package com.hb.happnissbilldemo.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.happnissbilldemo.R;
import com.hb.happnissbilldemo.dialog.JoinFamily;
import com.hb.happnissbilldemo.listener.OnJoinFamilyDoneListener;
import com.hb.happnissbilldemo.rest.FamilyInfo;
import com.hb.happnissbilldemo.rest.HappinessBillService;
import com.hb.happnissbilldemo.rest.RetrofitFactory;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class FamilyFragment extends Fragment implements OnJoinFamilyDoneListener {

    private String mName;
    private String mPassword;
    private FamilyInfo mFamilyInfo;

    private LinearLayout mLayoutUser;
    private LinearLayout mLayoutFamily;
    private LinearLayout mLayoutMember;
    private LinearLayout mLayoutParent;

    private TextView mFamilyName;
    private TextView mParentName;
    private TextView mFamilyMembers;
    private TextView mFamilyTypes;
    private TextView mFamilyCode;

    private int mSelectedMember;

    public FamilyFragment() {

    }

    public static FamilyFragment newInstance(String param1, String param2) {
        FamilyFragment fragment = new FamilyFragment();
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

        View view = inflater.inflate(R.layout.fragment_family, container, false);

        mLayoutUser = (LinearLayout) view.findViewById(R.id.layout_user);
        mLayoutMember = (LinearLayout) view.findViewById(R.id.layout_member);
        mLayoutParent = (LinearLayout) view.findViewById(R.id.layout_parent);
        mLayoutFamily = (LinearLayout) view.findViewById(R.id.layout_family);

        mFamilyName = (TextView) view.findViewById(R.id.family_name);
        mParentName = (TextView) view.findViewById(R.id.parent_name);
        mFamilyMembers = (TextView) view.findViewById(R.id.family_members);
        mFamilyTypes = (TextView) view.findViewById(R.id.family_types);
        mFamilyCode = (TextView) view.findViewById(R.id.family_code);

        getFamilyInfo();

        Button btn = (Button) view.findViewById(R.id.join);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JoinFamily.newInstance(FamilyFragment.this).show(getFragmentManager(), "");
            }
        });

        btn = (Button) view.findViewById(R.id.create);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFamily();
            }
        });

        btn = (Button) view.findViewById(R.id.unjoin);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                unjoinFamily();
            }
        });

        btn = (Button) view.findViewById(R.id.add_type);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addType();
            }
        });

        btn = (Button) view.findViewById(R.id.delete);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMember();
            }
        });

        return view;
    }

    private void addType() {
        final EditText et = new EditText(getContext());
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.title_add_type)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setView(et)
                .setPositiveButton(R.string.title_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String type = et.getText().toString();
                        if (type.length() < 1) {
                            Toast.makeText(getContext(), R.string.error_add_type_failed, Toast.LENGTH_LONG)
                                    .show();
                            return;
                        }

                        HappinessBillService service = RetrofitFactory.getRetrofitService();
                        Call<ResponseBody> c = service.addType(type, mName, mPassword);

                        c.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (!response.isSuccessful()) {
                                    Toast.makeText(getContext(), R.string.error_add_type_failed,
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                getFamilyInfo();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getContext(), R.string.error_add_type_failed,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton(R.string.title_cancel, null)
                .show();
    }

    private void deleteMember() {
        final String[] members = mFamilyInfo.getMembers();
        mSelectedMember = 0;
        new AlertDialog.Builder(getContext())
                .setTitle(R.string.title_add_type)
                .setIcon(android.R.drawable.ic_dialog_info)
                .setSingleChoiceItems(members, mSelectedMember, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mSelectedMember = which;
                    }
                })
                .setPositiveButton(R.string.title_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mSelectedMember < 0 || mSelectedMember >= members.length) {
                            Toast.makeText(getContext(), R.string.error_delete_member_failed, Toast.LENGTH_LONG)
                                    .show();
                            return;
                        }

                        HappinessBillService service = RetrofitFactory.getRetrofitService();
                        Call<ResponseBody> c = service.deleteMember(mName, members[mSelectedMember], mPassword);

                        c.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (!response.isSuccessful()) {
                                    Toast.makeText(getContext(), R.string.error_delete_member_failed,
                                            Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                getFamilyInfo();
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(getContext(), R.string.error_delete_member_failed,
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton(R.string.title_cancel, null)
                .show();
    }

    private void updateFamilyInfo() {
        if (mFamilyInfo == null) return;

        mFamilyName.setText(mFamilyInfo.getName());
        mParentName.setText(mFamilyInfo.getParentName());
        StringBuilder sb = new StringBuilder();
        for (String s : mFamilyInfo.getMembers()) {
            sb.append(s);
            sb.append(" ; ");
        }
        mFamilyMembers.setText(sb.toString());
        sb = new StringBuilder();
        for (String s : mFamilyInfo.getTypes()) {
            sb.append(s);
            sb.append(" ; ");
        }
        mFamilyTypes.setText(sb.toString());
        mFamilyCode.setText(mFamilyInfo.getCode());
    }

    private void getFamilyInfo() {
        HappinessBillService service = RetrofitFactory.getRetrofitService();
        Call<FamilyInfo> c = service.getFamilyInfo(mName, mPassword);

        c.enqueue(new Callback<FamilyInfo>() {
            @Override
            public void onResponse(Call<FamilyInfo> call, Response<FamilyInfo> response) {
                if (!response.isSuccessful()) {
                    mLayoutFamily.setVisibility(View.GONE);
                    mLayoutUser.setVisibility(View.VISIBLE);
                    mLayoutMember.setVisibility(View.GONE);
                    mLayoutParent.setVisibility(View.GONE);
                    return;
                }

                mLayoutFamily.setVisibility(View.VISIBLE);
                mFamilyInfo = response.body();
                updateFamilyInfo();

                if (mFamilyInfo.getName().equals(mName)) {
                    mLayoutUser.setVisibility(View.GONE);
                    mLayoutMember.setVisibility(View.GONE);
                    mLayoutParent.setVisibility(View.VISIBLE);
                    return;
                }

                mLayoutUser.setVisibility(View.GONE);
                mLayoutMember.setVisibility(View.VISIBLE);
                mLayoutParent.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<FamilyInfo> call, Throwable t) {
                Toast.makeText(getContext(), R.string.error_get_family_failed,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createFamily() {
        HappinessBillService service = RetrofitFactory.getRetrofitService();
        Call<ResponseBody> c = service.createFamily(mName, mPassword);

        c.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), R.string.error_create_family_failed,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                getFamilyInfo();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), R.string.error_create_family_failed,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void unjoinFamily() {
        HappinessBillService service = RetrofitFactory.getRetrofitService();
        Call<ResponseBody> c = service.unjoinFamily(mName, mPassword);

        c.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), R.string.error_unjoin_family_failed,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                getFamilyInfo();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), R.string.error_unjoin_family_failed,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onJoinFamilyDone(String familyName, String familyCode) {
        HappinessBillService service = RetrofitFactory.getRetrofitService();
        Call<ResponseBody> c = service.joinFamily(familyName, mName, mPassword, familyCode);

        c.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), R.string.error_join_family_failed,
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                getFamilyInfo();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getContext(), R.string.error_join_family_failed,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }


}
