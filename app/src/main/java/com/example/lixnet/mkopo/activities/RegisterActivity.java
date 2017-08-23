package com.example.lixnet.mkopo.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lixnet.mkopo.R;
import com.example.lixnet.mkopo.helpers.GEPreference;
import com.example.lixnet.mkopo.helpers.GsonHelper;
import com.example.lixnet.mkopo.helpers.Token;
import com.example.lixnet.mkopo.models.User;
import com.example.lixnet.mkopo.models.UserAuth;
import com.example.lixnet.mkopo.retrofit.RetrofitInterface;
import com.example.lixnet.mkopo.retrofit.ServiceGenerator;
import com.google.gson.Gson;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "SignupActivity";
    private GEPreference preference;

    @InjectView(R.id.input_name) EditText _nameText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_phone) EditText _phoneText;
    @InjectView(R.id.input_password) EditText _passwordText;
    @InjectView(R.id.input_confpassword) EditText _confpasswordText;
    @InjectView(R.id.btn_signup) Button _signupButton;
    @InjectView(R.id.link_login) TextView _loginLink;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.inject(this);
        preference = new GEPreference(this);

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
                finish();
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

        final ProgressDialog progressDialog = new ProgressDialog(RegisterActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String phone = _phoneText.getText().toString();
        //String password = _passwordText.getText().toString();
        String confpassword = _confpasswordText.getText().toString();

        User user = new User(name, email, phone, confpassword);
        addUser(user, progressDialog);

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


    private void addUser(final User user, final ProgressDialog progressDialog) {

        RetrofitInterface retrofitInterface = ServiceGenerator.getClient().create(RetrofitInterface.class);
        Gson gson = GsonHelper.getBuilder().create();
        final String userJson = gson.toJson(user);
        //Toast.makeText(RegisterActivity.this, userJson, Toast.LENGTH_LONG).show();
        Call<UserAuth> registerUser = retrofitInterface.addUser(userJson);

        registerUser.enqueue(new Callback<UserAuth>() {
            @Override
            public void onResponse(Call<UserAuth> call, retrofit2.Response<UserAuth> response) {
                //Toast.makeText(RegisterActivity.this, userJson, Toast.LENGTH_LONG).show();
                Log.e(TAG, userJson);
                UserAuth user = response.body();
                if (user.getStatus().equals("exist")){
                    Toast.makeText(RegisterActivity.this, "You already have an account!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(RegisterActivity.this, "Account Successfully created!", Toast.LENGTH_LONG).show();
                    processResults(user);
                }
                //confirmPin(user, progressDialog);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserAuth> call, Throwable t) {
                t.printStackTrace();
                //Toast.makeText(RegisterActivity.this, userJson, Toast.LENGTH_LONG).show();
                Log.e(TAG, userJson);
                progressDialog.dismiss();
                Snackbar snackbar = Snackbar.make(_emailText, "Something went wrong", Snackbar.LENGTH_LONG);
                snackbar.setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.show();
                        addUser(user, progressDialog);
                    }
                });
                snackbar.show();
                Toast.makeText(RegisterActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void onSignupSuccess() {
        _signupButton.setEnabled(true);
        setResult(RESULT_OK, null);
        finish();
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _signupButton.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String phone = _phoneText.getText().toString();
        String password = _passwordText.getText().toString();
        String confpassword = _confpasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("Please fill in your full names!");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("Please enter a valid email address!");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            _phoneText.setError("Please enter a valid phone number!");
            valid = false;
        } else {
            _phoneText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            _passwordText.setError("Please enter password with a minimum of 4 characters!");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (confpassword.isEmpty() || confpassword.length() < 4 || !confpassword.matches(password)) {
            _confpasswordText.setError("Passwords don`t match!");
            valid = false;
        } else {
            _confpasswordText.setError(null);
        }

        return valid;
    }

    private void processResults(UserAuth user) {
        if (user.getStatus().equals("created")) {
            String id = user.getUser().getId();
            String name = user.getUser().getName();
            String phn = user.getUser().getPhone();
            String email = user.getUser().getEmail();
            preference.setUser(id, name, phn, email);
            Token.setToken(user.getToken());
            preference.setToken(user.getToken());
            Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}

