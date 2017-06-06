package com.hb.happnissbilldemo.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hb.happnissbilldemo.R;
import com.hb.happnissbilldemo.rest.HappinessBillService;
import com.hb.happnissbilldemo.rest.RetrofitFactory;
import com.hb.happnissbilldemo.util.Hash;

import java.io.IOException;
import java.util.regex.Pattern;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * A login screen that offers login via email/password.
 */
public class RegisterActivity extends AppCompatActivity {

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserRegisterTask mRegisterTask = null;

    // UI references.
    private EditText mUserNameView;
    private EditText mPhoneNumberView;
    private EditText mEmailView;
    private EditText mPasswordView;
    private EditText mConfirmPasswordView;
    private View mProgressView;
    private View mRegisterFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Set up the login form.
        mUserNameView = (EditText) findViewById(R.id.username);
        mPhoneNumberView = (EditText) findViewById(R.id.phonenumber);
        mEmailView = (EditText) findViewById(R.id.email);
        mPasswordView = (EditText) findViewById(R.id.password);

        mConfirmPasswordView = (EditText) findViewById(R.id.confirmpassword);
        mConfirmPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.register || id == EditorInfo.IME_NULL) {
                    attemptRegister();
                    return true;
                }
                return false;
            }
        });

        Button registerInButton = (Button) findViewById(R.id.register_button);
        registerInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptRegister();
            }
        });

        mRegisterFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.register_progress);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptRegister() {
        if (mRegisterTask != null) {
            return;
        }

        View focusView = checkValid();

        if (focusView != null) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mRegisterTask = new UserRegisterTask();
            mRegisterTask.execute((Void) null);
        }
    }

    private View checkValid() {
        // Reset errors.
        mUserNameView.setError(null);
        mPhoneNumberView.setError(null);
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mConfirmPasswordView.setError(null);

        String str = mUserNameView.getText().toString();
        if (str.length() < 4 || str.length() > 64) {
            mUserNameView.setError(getString(R.string.error_invalid_username));
            return mUserNameView;
        }

        str = mPhoneNumberView.getText().toString();
        if (!Pattern.compile("\\d{4,20}").matcher(str).matches()) {
            mPhoneNumberView.setError(getString(R.string.error_invalid_phonenumber));
            return mPhoneNumberView;
        }

        str = mEmailView.getText().toString();
        if (!Pattern.compile("[\\w\\.]+@[\\w\\.]+").matcher(str).matches()) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            return mEmailView;
        }

        String pwd = mPasswordView.getText().toString();
        if (pwd.length() < 6 || pwd.length() > 64) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            return mPasswordView;
        }

        str = mConfirmPasswordView.getText().toString();
        if (!str.equals(pwd)) {
            mConfirmPasswordView.setError(getString(R.string.error_invalid_confirmpassword));
            return mConfirmPasswordView;
        }

        return null;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mRegisterFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mRegisterFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }


    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserRegisterTask extends AsyncTask<Void, Void, Boolean> {

        final private String mUserName;
        final private String mPhoneNumber;
        final private String mEmail;
        final private String mPassword;

        UserRegisterTask() {
            mUserName = mUserNameView.getText().toString();
            mPhoneNumber = mPhoneNumberView.getText().toString();
            mEmail = mEmailView.getText().toString();
            mPassword = Hash.getHash(mUserName + mPasswordView.getText());
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            HappinessBillService service = RetrofitFactory.getRetrofitService();

            Call<ResponseBody> c = service.register(mUserName, mPassword, mEmail, mPhoneNumber);

            try {
                Response<ResponseBody> r = c.execute();
                return r.isSuccessful();
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mRegisterTask = null;
            showProgress(false);

            if (success) {
                SharedPreferences sp = getSharedPreferences("hbd", Context.MODE_PRIVATE);
                sp.edit().putString("username", mUserName).putString("password", mPassword).apply();
                Toast.makeText(RegisterActivity.this, R.string.error_register_succeed, Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, R.string.error_register_failed, Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        protected void onCancelled() {
            mRegisterTask = null;
            showProgress(false);
        }
    }
}

