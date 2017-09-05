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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lixnet.mkopo.R;
import com.example.lixnet.mkopo.helpers.GEPreference;
import com.example.lixnet.mkopo.helpers.GsonHelper;
import com.example.lixnet.mkopo.helpers.Token;
import com.example.lixnet.mkopo.models.Password;
import com.example.lixnet.mkopo.models.Profile;
import com.example.lixnet.mkopo.models.UserAuth;
import com.example.lixnet.mkopo.retrofit.RetrofitInterface;
import com.example.lixnet.mkopo.retrofit.ServiceGenerator;
import com.example.lixnet.mkopo.services.IService;
import com.google.gson.Gson;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit2.Call;
import retrofit2.Callback;

public class UpdateProfileActivity extends AppCompatActivity {

    private GEPreference preference;
    private ProgressDialog progressDialog;

    @InjectView(R.id.input_name) EditText _nameText;
    @InjectView(R.id.input_email) EditText _emailText;
    @InjectView(R.id.input_phone) EditText _phoneText;
    @InjectView(R.id.input_idnumber) EditText _idnoText;
    @InjectView(R.id.input_gender)
    Spinner _genderText;
    @InjectView(R.id.btn_update) Button _updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.inject(this);
        preference = new GEPreference(this);

        _nameText.setText(preference.getUser().get(GEPreference.USER_NAME));
        _emailText.setText(preference.getUser().get(GEPreference.USER_EMAIL));
        _phoneText.setText(preference.getUser().get(GEPreference.USER_PHONE));
        _idnoText.setText(preference.getUser().get(GEPreference.USER_IDNO));
        if(preference.getUser().get(GEPreference.USER_GENDER).equals("male")){
            _genderText.setSelection(0);
        }else{
            _genderText.setSelection(1);
        }


        _updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) {
                    _updateButton.setEnabled(true);
                    return;
                }

                _updateButton.setEnabled(false);

                progressDialog = new ProgressDialog(UpdateProfileActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Updating Profile...");
                progressDialog.show();

                String name = _nameText.getText().toString();
                String email = _emailText.getText().toString();
                String phn = _phoneText.getText().toString();
                String idno = _idnoText.getText().toString();
                String gender = _genderText.getSelectedItem().toString();

                Profile user = new Profile(preference.getUser().get(GEPreference.USER_ID),name, email, phn, idno, gender);
                updateUser(user, progressDialog);

                // TODO: Implement your own signup logic here.

                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {
                                // On complete call either onSignupSuccess or onSignupFailed
                                // depending on success
                                _updateButton.setEnabled(true);
                                // onSignupFailed();
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

    private void updateUser(final Profile user, final ProgressDialog progressDialog) {

        RetrofitInterface retrofitInterface = ServiceGenerator.getClient().create(RetrofitInterface.class);
        Gson gson = GsonHelper.getBuilder().create();
        final String userJson = gson.toJson(user);
        //Toast.makeText(RegisterActivity.this, userJson, Toast.LENGTH_LONG).show();
        Call<UserAuth> updUser = retrofitInterface.updateUser(userJson);

        updUser.enqueue(new Callback<UserAuth>() {
            @Override
            public void onResponse(Call<UserAuth> call, retrofit2.Response<UserAuth> response) {
                //Toast.makeText(RegisterActivity.this, userJson, Toast.LENGTH_LONG).show();
                Log.e("Profile", userJson);
                UserAuth userAuth = response.body();
                if (userAuth.getResponse().equals("1")){
                    Toast.makeText(UpdateProfileActivity.this, "An error has occured.... Please try again later!", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(UpdateProfileActivity.this, "Profile Successfully updated!", Toast.LENGTH_LONG).show();
                    String id = userAuth.getUser().getId();
                    String name = userAuth.getUser().getFull_name();
                    String phn = userAuth.getUser().getPhone_number();
                    String idno = userAuth.getUser().getId_number();
                    String gender = userAuth.getUser().getGender();
                    String email = userAuth.getUser().getEmail();
                    preference.setUser(id, name, phn, email, idno, gender);

                    Token.setToken(userAuth.getToken());
                    preference.setToken(userAuth.getToken());
                    Intent i = new Intent(UpdateProfileActivity.this, ProfileActivity.class);
                    startActivity(i);
                }
                //confirmPin(user, progressDialog);
                progressDialog.dismiss();
            }

            @Override
            public void onFailure(Call<UserAuth> call, Throwable t) {
                t.printStackTrace();
                //Toast.makeText(RegisterActivity.this, userJson, Toast.LENGTH_LONG).show();
                Log.e("Profile", userJson);
                progressDialog.dismiss();
                Snackbar snackbar = Snackbar.make(_emailText, "Something went wrong", Snackbar.LENGTH_LONG);
                snackbar.setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.show();
                        updateUser(user, progressDialog);
                    }
                });
                snackbar.show();
                Toast.makeText(UpdateProfileActivity.this, "Error", Toast.LENGTH_LONG).show();
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
                        stopService(new Intent(UpdateProfileActivity.this, IService.class));
                        Intent intent = new Intent(UpdateProfileActivity.this, LoginActivity.class);
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

    public boolean validate() {
        boolean valid = true;

        String name = _nameText.getText().toString();
        String email = _emailText.getText().toString();
        String phone = _phoneText.getText().toString();
        String idno = _idnoText.getText().toString();

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

        if (idno.isEmpty()) {
            _idnoText.setError("Please enter your national identity number!");
            valid = false;
        } else {
            _idnoText.setError(null);
        }

        if (phone.isEmpty() || !Patterns.PHONE.matcher(phone).matches()) {
            _phoneText.setError("Please enter a valid phone number!");
            valid = false;
        } else {
            _phoneText.setError(null);
        }

        return valid;
    }

}

