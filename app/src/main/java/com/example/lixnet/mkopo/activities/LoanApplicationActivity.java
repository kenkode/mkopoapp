package com.example.lixnet.mkopo.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.lixnet.mkopo.ApplyLoanActivity;
import com.example.lixnet.mkopo.R;
import com.example.lixnet.mkopo.helpers.GEPreference;
import com.example.lixnet.mkopo.helpers.GsonHelper;
import com.example.lixnet.mkopo.helpers.Token;
import com.example.lixnet.mkopo.models.Loan;
import com.example.lixnet.mkopo.models.Pojodemo;
import com.example.lixnet.mkopo.models.User;
import com.example.lixnet.mkopo.models.UserAuth;
import com.example.lixnet.mkopo.retrofit.LoanAPI;
import com.example.lixnet.mkopo.retrofit.RetrofitApplication;
import com.example.lixnet.mkopo.retrofit.RetrofitInterface;
import com.example.lixnet.mkopo.retrofit.ServiceGenerator;
import com.example.lixnet.mkopo.services.IService;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import okhttp3.ResponseBody;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class LoanApplicationActivity extends AppCompatActivity {
    private GEPreference preference;
    private static final String BASE_URL = "http://45.55.201.219/mkopo/public/";
    private static final String TAG = "LoanApplicationActivity";
    CardView loan500,loan1000,loan2000,loan3000,loan4000,loan5000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_application);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



        preference = new GEPreference(this);

        loan500 = (CardView)findViewById(R.id.loan500);
        loan1000 = (CardView)findViewById(R.id.loan1000);
        loan2000 = (CardView)findViewById(R.id.loan2000);
        loan3000 = (CardView)findViewById(R.id.loan3000);
        loan4000 = (CardView)findViewById(R.id.loan4000);
        loan5000 = (CardView)findViewById(R.id.loan5000);

        final ProgressDialog progressDialog = new ProgressDialog(LoanApplicationActivity.this,
                R.style.AppTheme_Dark_Dialog);

        loan500.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(LoanApplicationActivity.this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to apply this loan?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Loan Application...");
                        progressDialog.show();
                        //Loan loan = new Loan(preference.getUser().get(GEPreference.USER_ID), 500.00);
                        applyLoan(preference.getUser().get(GEPreference.USER_ID), 500.00, progressDialog);

                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();

            }
        });

        loan1000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(LoanApplicationActivity.this,
                        R.style.AppTheme_Dark_Dialog);

                //Loan loan = new Loan(preference.getUser().get(GEPreference.USER_ID), 500.00);


                AlertDialog.Builder builder = new AlertDialog.Builder(LoanApplicationActivity.this);
                builder.setTitle("Confirm");
                builder.setCancelable(false);
                builder.setMessage("Are you sure you want to apply this loan?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Loan Application...");
                        progressDialog.show();
                        //Loan loan = new Loan(preference.getUser().get(GEPreference.USER_ID), 500.00);
                        applyLoan(preference.getUser().get(GEPreference.USER_ID), 1000.00, progressDialog);

                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();

            }
        });

        loan2000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(LoanApplicationActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                AlertDialog.Builder builder = new AlertDialog.Builder(LoanApplicationActivity.this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to apply this loan?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Loan Application...");
                        progressDialog.show();
                        //Loan loan = new Loan(preference.getUser().get(GEPreference.USER_ID), 500.00);
                        applyLoan(preference.getUser().get(GEPreference.USER_ID), 2000.00, progressDialog);

                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();


            }
        });

        loan3000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(LoanApplicationActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                AlertDialog.Builder builder = new AlertDialog.Builder(LoanApplicationActivity.this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to apply this loan?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Loan Application...");
                        progressDialog.show();
                        //Loan loan = new Loan(preference.getUser().get(GEPreference.USER_ID), 500.00);
                        applyLoan(preference.getUser().get(GEPreference.USER_ID), 3000.00, progressDialog);

                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();


            }
        });

        loan4000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(LoanApplicationActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                AlertDialog.Builder builder = new AlertDialog.Builder(LoanApplicationActivity.this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to apply this loan?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Loan Application...");
                        progressDialog.show();
                        //Loan loan = new Loan(preference.getUser().get(GEPreference.USER_ID), 500.00);
                        applyLoan(preference.getUser().get(GEPreference.USER_ID), 4000.00, progressDialog);

                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();


            }
        });

        loan5000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog progressDialog = new ProgressDialog(LoanApplicationActivity.this,
                        R.style.AppTheme_Dark_Dialog);
                AlertDialog.Builder builder = new AlertDialog.Builder(LoanApplicationActivity.this);
                builder.setTitle("Confirm");
                builder.setMessage("Are you sure you want to apply this loan?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        progressDialog.setIndeterminate(true);
                        progressDialog.setMessage("Loan Application...");
                        progressDialog.show();
                        //Loan loan = new Loan(preference.getUser().get(GEPreference.USER_ID), 500.00);
                        applyLoan(preference.getUser().get(GEPreference.USER_ID), 5000.00, progressDialog);

                    }
                })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                builder.show();


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

    public void applyLoan(final String userid, final double amount, final ProgressDialog progressDialog)
    {
        // call InterFace
        LoanAPI registerAPI=((RetrofitApplication) getApplication()).getMshoppinpApis();

        // call Inter face method
        Call<Pojodemo> call=registerAPI.serverCall(userid,amount);
        call.enqueue(new Callback<Pojodemo>() {
            @Override
            public void onResponse(Response<Pojodemo> response, Retrofit retrofit) {
                // postive responce

                // get pojo value using  getSuccess and get Body(Body is all responce)
                if(response.body().getSuccess())
                {

                    AlertDialog.Builder builder = new AlertDialog.Builder(LoanApplicationActivity.this);
                    builder.setMessage("Loan Application Successful!")
                            .setTitle("Success Message")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();


                    Toast.makeText(LoanApplicationActivity.this,"Loan Application Successful!",Toast.LENGTH_LONG).show();
                }
                else
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(LoanApplicationActivity.this);
                    builder.setMessage("Something went wrong!")
                            .setTitle("Error Message")
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                    Toast.makeText(LoanApplicationActivity.this,response.body().getError()+"",Toast.LENGTH_LONG).show();
                }
                progressDialog.dismiss();
            }
            // Error Or Any Failure
            @Override
            public void onFailure(Throwable t) {
                Snackbar snackbar = Snackbar.make(loan500, "Something went wrong", Snackbar.LENGTH_LONG);
                snackbar.setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        progressDialog.show();
                        applyLoan(userid, amount, progressDialog);
                    }
                });
                snackbar.show();
                t.printStackTrace();
                progressDialog.dismiss();
            }
        });
    }


    public void onApplicationSuccess() {
        setResult(RESULT_OK, null);
        finish();
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
                        stopService(new Intent(LoanApplicationActivity.this, IService.class));
                        Intent intent = new Intent(LoanApplicationActivity.this, LoginActivity.class);
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
