package com.example.kindler;

import android.app.ProgressDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kindler.models.UserDetailModel;
import com.example.kindler.util.PreferencesService;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Database.UserViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;
import Database.Profile;

public class SignupActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private UserViewModel mUserViewModel;

    @BindView(R.id.input_name)
    EditText _nameText;
    // @BindView(R.id.input_address)
    //EditText _addressText;
    @BindView(R.id.input_email)
    EditText _emailText;
    //@BindView(R.id.input_mobile)
    //EditText _mobileText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.input_reEnterPassword)
    EditText _reEnterPasswordText;
    @BindView(R.id.btn_signup)
    Button _signupButton;
    @BindView(R.id.link_login)
    TextView _loginLink;

    private DatabaseHelper databaseHelper;

    UserDetailModel userDetailModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        mUserViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        PreferencesService.init(this);

        databaseHelper = new DatabaseHelper(SignupActivity.this);
        userDetailModel=new UserDetailModel();
        _signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        _loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }


    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        _signupButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(SignupActivity.this,
                R.style.Theme_Design_Light);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        // TODO: Implement your own signup logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        onSignupSuccess();
                        // onSignupFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    public void onSignupSuccess() {
        _signupButton.setEnabled(true);

//        finish();


        if (mUserViewModel.register(_emailText.getText().toString().trim(), _passwordText.getText().toString().trim())) {

//            userDetailModel.setName(_nameText.getText().toString().trim());
//            userDetailModel.setEmail(_emailText.getText().toString().trim());
//            userDetailModel.setPassword(_passwordText.getText().toString().trim());
//
//            databaseHelper.addUserDetailModel(userDetailModel);
            // Snack Bar to show success message that record saved successfully
            Database.Profile p = mUserViewModel.getCurrProfile();
            p.setProfileName(_nameText.getText().toString());
            mUserViewModel.setProfile(p);

            PreferencesService.instance().saveLogin_Status("true");
            finish();
            //    Toast.makeText(this, "SignUp Success", Toast.LENGTH_LONG).show();
        } else {
            // Snack Bar to show error message that record already exists
            Toast.makeText(this, "You are already registered .. Try Login", Toast.LENGTH_LONG).show();
            finish();
        }

    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean isEmptyName(String name)
    {
        boolean valid = true;
        //  String name = _nameText.getText().toString();
        if (name.isEmpty() || name.length() < 3) {

            valid = true;
        } else {
            valid=false;
        }

        return valid;
    }

    public boolean isEmail(String email){


        boolean valid = true;
        if (email.isEmpty() || !isEmailValid(email)) {

            valid = false;
        } else {
            valid=true;
        }
        return valid;
    }

    public static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public boolean isPassword(String password){
        boolean valid = true;


        //String address = _addressText.getText().toString();

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {

            valid = false;
        } else {
            valid=true;
        }

        return valid;

    }

    public boolean isConfirmPass(String password,String reEnterPassword){
        boolean valid = true;

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4
                || reEnterPassword.length() > 10 || !(reEnterPassword.equals(password))) {

            valid = false;
        } else {
            valid=true;
        }

        return valid;
    }

    public boolean validate() {
        boolean valid = true;

        if (isEmptyName(_nameText.getText().toString())){
            _nameText.setError("at least 3 characters");
        }
        else  {
            _nameText.setError(null);
        }

        if (isEmail(_emailText.getText().toString())){
            _emailText.setError(null);

        }
        else {

            _emailText.setError("enter a valid email address");
        }

        if (isPassword(_passwordText.getText().toString())){
            _passwordText.setError(null);

        }
        else {

            _passwordText.setError("between 4 and 10 alphanumeric characters");

        }

        if (isConfirmPass(_passwordText.getText().toString(),_reEnterPasswordText.getText().toString())){
            _reEnterPasswordText.setError(null);

        }
        else {

            _reEnterPasswordText.setError("Password Do not match");

        }

        if (!isEmptyName(_nameText.getText().toString()) &&
                isEmail(_emailText.getText().toString()) &&
                isPassword(_passwordText.getText().toString()) &&
                isConfirmPass(_passwordText.getText().toString(),_reEnterPasswordText.getText().toString()))
        {
            return true;
        }
        else
        {
            return false;
        }


    }
}
