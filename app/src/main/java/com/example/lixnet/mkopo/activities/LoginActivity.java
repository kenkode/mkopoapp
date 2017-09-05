package com.example.lixnet.mkopo.activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import android.content.Intent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;

import com.example.lixnet.mkopo.R;
import com.example.lixnet.mkopo.core.ApplicationConfiguration;
import com.example.lixnet.mkopo.helpers.GEPreference;
import com.example.lixnet.mkopo.helpers.Internet;
import com.example.lixnet.mkopo.helpers.Token;
import com.example.lixnet.mkopo.models.UserAuth;
import com.example.lixnet.mkopo.retrofit.RetrofitInterface;
import com.example.lixnet.mkopo.retrofit.ServiceGenerator;
import com.example.lixnet.mkopo.services.IService;

public class LoginActivity extends AppCompatActivity implements Internet.ConnectivityReceiverListener{
    private static final String TAG = "LoginActivity";
    private static final int REQUEST_SIGNUP = 0;
    private GEPreference preference;
    private ProgressDialog progressDialog;

    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.btn_login) Button _loginButton;
    @InjectView(R.id.link_signup) TextView _signupLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.inject(this);

        if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
            final int REQUEST_CODE_ASK_PERMISSIONS = 123;
            ActivityCompat.requestPermissions(LoginActivity.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
        }

        preference = new GEPreference(this);

        if (preference.isUserLogged()) {
            Token.setToken(preference.getToken());
            startActivity(new Intent(this, SummaryActivity.class));
            finish();
        }

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
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
            }
        });
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        progressDialog = new ProgressDialog(LoginActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        /*if(!email.equals("") && !password.equals("")){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            //Intent intent = new Intent(GESplashActivity.this, GasExpress.class);
            startActivity(intent);
        }*/


        View view = LoginActivity.this.getCurrentFocus();
        if (view != null) {
            InputMethodManager methodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            methodManager.hideSoftInputFromInputMethod(view.getWindowToken(), 0);
        }

        submitDetails(email, password, progressDialog);


        // TODO: Implement your own authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 5000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_SIGNUP) {
            if (resultCode == RESULT_OK) {

                // TODO: Implement successful signup logic here
                // By default we just finish the Activity and log them in automatically

                Intent intent = new Intent(LoginActivity.this, SummaryActivity.class);
                //Intent intent = new Intent(GESplashActivity.this, GasExpress.class);
                startActivity(intent);

                this.finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        //finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() ||  (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && !android.util.Patterns.PHONE.matcher(email).matches())) {
            _emailText.setError("enter a valid email address/ phone number");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            _passwordText.setError("Please enter password with a minimum of 4 characters!");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    private void submitDetails(String email, String password, ProgressDialog progressDialog) {
       // if (Internet.isConnected()) {
            authUser(progressDialog, email, password);
        /*} else {
            showSnack();
        }*/
    }

    @Override
    public void onNetworkConnectionChange(boolean isConnected) {
        checkConnection(isConnected);
    }

    private void checkConnection(boolean isConnected) {
        if (!isConnected) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            showSnack();
        }
    }

    private void showSnack() {
        String message = "No internet.Please check your connection";
        Snackbar snackbar = Snackbar.make(_emailText, message, Snackbar.LENGTH_LONG);
        snackbar.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //ApplicationConfiguration.getInstance().setConnectivityListener(this);
    }

    private void authUser(final ProgressDialog dialog, final String email, final String password) {

        RetrofitInterface retrofitInterface = ServiceGenerator.getClient().create(RetrofitInterface.class);
        Call<UserAuth> userAuthCall = retrofitInterface.authUser(email,password);

        userAuthCall.enqueue(new Callback<UserAuth>() {
            @Override
            public void onResponse(Call<UserAuth> call, retrofit2.Response<UserAuth> response) {
                dialog.dismiss();
                UserAuth userAuth = response.body();
                if (userAuth.getResponse().equals("exist")) {
                    String id = userAuth.getUser().getId();
                    String name = userAuth.getUser().getFull_name();
                    String phn = userAuth.getUser().getPhone_number();
                    String idno = userAuth.getUser().getId_number();
                    String gender = userAuth.getUser().getGender();
                    String email = userAuth.getUser().getEmail();
                    preference.setUser(id, name, phn, email, idno, gender);

                    Token.setToken(userAuth.getToken());
                    preference.setToken(userAuth.getToken());
                    /*Toast.makeText(LoginActivity.this, userAuth.getToken(), Toast.LENGTH_SHORT).show();
                    Log.e(TAG, id+"\n"+name+"\n"+phn+"\n"+email+"\n"+idno+"\n"+gender+"\n");
                    Toast.makeText(LoginActivity.this, id+"\n"+name+"\n"+phn+"\n"+email+"\n", Toast.LENGTH_SHORT).show();*/
                    if(ContextCompat.checkSelfPermission(getBaseContext(), "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED) {
                        final int REQUEST_CODE_ASK_PERMISSIONS = 123;
                        ActivityCompat.requestPermissions(LoginActivity.this, new String[]{"android.permission.READ_SMS"}, REQUEST_CODE_ASK_PERMISSIONS);
                        startService(new Intent(LoginActivity.this, IService.class));
                    }
                    Intent intent = new Intent(LoginActivity.this, SummaryActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Wrong username/ password or you haven`t registered an account!", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                    //intent.putExtra("phone", phone);
                    startActivity(intent);
                }
                //confirmPin(userAuth, dialog);
            }

            @Override
            public void onFailure(Call<UserAuth> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                //Toast.makeText(LoginActivity.this, id+"\n"+name+"\n"+phn+"\n"+email+"\n", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, LoginActivity.class);
                startActivity(intent);
                t.printStackTrace();
                dialog.dismiss();
            }
        });
    }

    private void processResults(UserAuth userAuth, String phone) {
        if (userAuth.getResponse().equals("exist")) {
            String id = userAuth.getUser().getId();
            String name = userAuth.getUser().getFull_name();
            String phn = userAuth.getUser().getPhone_number();
            String idno = userAuth.getUser().getId_number();
            String gender = userAuth.getUser().getGender();
            String email = userAuth.getUser().getEmail();
            preference.setUser(id, name, phn, email, idno, gender);
            Token.setToken(userAuth.getToken());
            preference.setToken(userAuth.getToken());
            Intent intent = new Intent(LoginActivity.this, SummaryActivity.class);
            startActivity(intent);
            finish();
        } else if (userAuth.getResponse().equals("DNE")) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            //intent.putExtra("phone", phone);
            startActivity(intent);
        } else if (userAuth.getResponse().equals("exists")) {
            Toast.makeText(LoginActivity.this, "Email exists", Toast.LENGTH_LONG).show();
        }
    }
}