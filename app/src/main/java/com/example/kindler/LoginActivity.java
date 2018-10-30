package com.example.kindler;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kindler.models.UserDetailModel;
import com.example.kindler.util.PreferencesService;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Database.UserViewModel;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;

    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password)
    EditText _passwordText;
    @BindView(R.id.btn_login)
    Button _loginButton;
    @BindView(R.id.link_signup)
    TextView _signupLink;

    CallbackManager callbackManager;

    @BindView(R.id.loginButton)
    Button loginButton;

    DatabaseHelper databaseHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_login);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.kindler",
                    PackageManager.GET_SIGNATURES);

            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
        ButterKnife.bind(this);
        PreferencesService.init(this);
        databaseHelper=new DatabaseHelper(LoginActivity.this);
        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
                Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                faceBookSetUp();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this, R.style.Theme_Design_Light);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically
                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
//        finish();
        if (databaseHelper.checkUserDetailModel(_emailText.getText().toString().trim()
                , _passwordText.getText().toString().trim())) {

            PreferencesService.instance().saveLogin_Status("true");
            finish();
         //   Toast.makeText(this, "Login Success", Toast.LENGTH_LONG).show();
        } else {
            // Snack Bar to show success message that record is wrong
            Toast.makeText(this, "User credentials are wrong or is not Registered", Toast.LENGTH_LONG).show();
           // startActivity(new Intent(LoginActivity.this,SignupActivity.class));
        }

    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
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

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {

            valid = false;
        } else {
            valid=true;
        }

        return valid;
    }


    public boolean validate() {
        boolean valid = true;

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

       if (isEmail(_emailText.getText().toString()) && isPassword(_passwordText.getText().toString())){
           return true;
       }
       else {
           return false;
       }


    }

    public void faceBookSetUp(){
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(LoginActivity.this);
        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        Log.e("LoginActivity", response.toString());
                                        Toast.makeText(LoginActivity.this, "login", Toast.LENGTH_SHORT).show();
                                        // Application code
                                        try {

                                            String email = object.getString("email");
                                            //String birthday = object.getString("birthday"); // 01/31/1980 format
//                                            loginSocialApi(email);

                                            Toast.makeText(LoginActivity.this, "Logged In", Toast.LENGTH_SHORT).show();


                                        } catch (JSONException e) {
                                            Log.e("Exception",e.toString());
                                        }
                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender,birthday");
                        request.setParameters(parameters);
                        request.executeAsync();
//
                    }

                    @Override
                    public void onCancel() {
                        // App code
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        // App code
                    }
                });

        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this,(Arrays.asList(
                "public_profile", "email")));

    }
}
