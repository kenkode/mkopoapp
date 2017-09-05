package com.example.lixnet.mkopo.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lixnet.mkopo.R;
import com.example.lixnet.mkopo.helpers.GEPreference;
import com.example.lixnet.mkopo.helpers.GsonHelper;
import com.example.lixnet.mkopo.models.Password;
import com.example.lixnet.mkopo.models.User;
import com.example.lixnet.mkopo.models.UserAuth;
import com.example.lixnet.mkopo.retrofit.RetrofitInterface;
import com.example.lixnet.mkopo.retrofit.ServiceGenerator;
import com.example.lixnet.mkopo.services.IService;
import com.google.gson.Gson;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;

public class UpdatePasswordActivity extends AppCompatActivity {

    private GEPreference preference;
    private ProgressDialog progressDialog;

    @InjectView(R.id.current_password) EditText _currentPasswordText;
    @InjectView(R.id.new_password) EditText _newPasswordText;
    @InjectView(R.id.confirm_password) EditText _confirmPasswordText;
    @InjectView(R.id.btn_update) Button _updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.inject(this);

        preference = new GEPreference(this);

        _updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    _updateButton.setEnabled(true);
                    return;
                }

                _updateButton.setEnabled(false);

                progressDialog = new ProgressDialog(UpdatePasswordActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Updating Password...");
                progressDialog.show();

                String currentpassword = _currentPasswordText.getText().toString();
                String newpassword = _newPasswordText.getText().toString();
                String confpassword = _confirmPasswordText.getText().toString();
;
                updatePassword(preference.getUser().get(GEPreference.USER_ID), currentpassword, confpassword, progressDialog);

                // TODO: Implement your own signup logic here.

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onSignupSuccess or onSignupFailed
                                // depending on success
                                _updateButton.setEnabled(true);
                                progressDialog.dismiss();
                            }
                        }, 5000);
            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    public boolean validate() {
        boolean valid = true;

        String currentpassword = _currentPasswordText.getText().toString();
        String password = _newPasswordText.getText().toString();
        String confpassword = _confirmPasswordText.getText().toString();



        if (currentpassword.isEmpty()) {
            _currentPasswordText.setError("Please enter your current password!");
            valid = false;
        } else {
            _currentPasswordText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4) {
            _newPasswordText.setError("Please enter password with a minimum of 4 characters!");
            valid = false;
        } else {
            _newPasswordText.setError(null);
        }

        if (confpassword.isEmpty() || confpassword.length() < 4 || !confpassword.matches(password)) {
            _confirmPasswordText.setError("Passwords don`t match!");
            valid = false;
        } else {
            _confirmPasswordText.setError(null);
        }

        return valid;
    }

    private void updatePassword(final String user_id, final String current_password, final String confirm_password, final ProgressDialog progressDialog) {

        RetrofitInterface retrofitInterface = ServiceGenerator.getClient().create(RetrofitInterface.class);
        Gson gson = GsonHelper.getBuilder().create();
        //final String userJson = gson.toJson(user);
        //Toast.makeText(RegisterActivity.this, userJson, Toast.LENGTH_LONG).show();
        Call<Password> updateUser = retrofitInterface.updatePassword(user_id, current_password,confirm_password);

        updateUser.enqueue(new Callback<Password>() {
            @Override
            public void onResponse(Call<Password> call, retrofit2.Response<Password> response) {
                //Toast.makeText(RegisterActivity.this, userJson, Toast.LENGTH_LONG).show();
                //Log.e(TAG, userJson);
                Password user = response.body();
                if (user.getResponse().equals("1")){
                    Toast.makeText(UpdatePasswordActivity.this, "Wrong current password... Please try again!", Toast.LENGTH_LONG).show();
                }else if (user.getResponse().equals("0")){
                    Toast.makeText(UpdatePasswordActivity.this, "Password Successfully updated...Please login with your new password!", Toast.LENGTH_LONG).show();
                    preference.unsetUser();
                    stopService(new Intent(UpdatePasswordActivity.this, IService.class));
                    Intent intent = new Intent(UpdatePasswordActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(UpdatePasswordActivity.this, "An error occurred... Please try again later!", Toast.LENGTH_LONG).show();
                }
                //confirmPin(user, progressDialog);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<Password> call, Throwable t) {
                t.printStackTrace();
                //Toast.makeText(RegisterActivity.this, userJson, Toast.LENGTH_LONG).show();
                //Log.e(TAG, userJson);
                progressDialog.dismiss();
                Snackbar snackbar = Snackbar.make(_currentPasswordText, "Something went wrong", Snackbar.LENGTH_LONG);
                snackbar.setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.show();
                        updatePassword(user_id, current_password, confirm_password, progressDialog);
                    }
                });
                snackbar.show();
                Toast.makeText(UpdatePasswordActivity.this, "Error", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // action with ID action_refresh was selected
            case R.id.logout:
                //Toast.makeText(this, "Refresh selected", Toast.LENGTH_SHORT).show();

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to log out?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        preference.unsetUser();
                        dialog.dismiss();
                        stopService(new Intent(UpdatePasswordActivity.this, IService.class));
                        Intent intent = new Intent(UpdatePasswordActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();

                break;
            // action with ID action_settings was selected
            default:
                break;
        }

        return true;
    }

}

