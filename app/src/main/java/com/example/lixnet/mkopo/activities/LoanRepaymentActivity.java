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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lixnet.mkopo.R;
import com.example.lixnet.mkopo.adapters.HistoryAdapter;
import com.example.lixnet.mkopo.data.HistoryData;
import com.example.lixnet.mkopo.data.PaymentData;
import com.example.lixnet.mkopo.helpers.GEPreference;
import com.example.lixnet.mkopo.helpers.Token;
import com.example.lixnet.mkopo.models.Loanhistory;
import com.example.lixnet.mkopo.models.MyLoans;
import com.example.lixnet.mkopo.models.UserAuth;
import com.example.lixnet.mkopo.retrofit.RetrofitInterface;
import com.example.lixnet.mkopo.retrofit.ServiceGenerator;
import com.example.lixnet.mkopo.services.IService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class LoanRepaymentActivity extends AppCompatActivity {

    private GEPreference preference;

    TextView balance;
    double amount = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_repayment);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preference = new GEPreference(this);

        LinearLayout errorLayout = (LinearLayout) findViewById(R.id.error_layout_price);
        errorLayout.setVisibility(View.GONE);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.load_prices);
        balance = (TextView)findViewById(R.id.amount);

        Bundle bundle = getIntent().getExtras();
        final int id = bundle.getInt("id");
        amount = bundle.getDouble("amount");


        getBalance(id);

        Loanhistory loanhistory = new Loanhistory();
        loanhistory.setTotal(amount);

        Intent intent = getIntent();
        //int size = intent.getIntExtra(Constants.NAME, 0);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Sisi");
        }

        ListView listView = (ListView) findViewById(R.id.price_list);
        PaymentData loan = new PaymentData(this);
        loan.getLoans(id, listView, errorLayout, progressBar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void getBalance(final int id) {

        RetrofitInterface retrofitInterface = ServiceGenerator.getClient().create(RetrofitInterface.class);
        Call<Loanhistory> balanceCall = retrofitInterface.getBalance(id);

        balanceCall.enqueue(new Callback<Loanhistory>() {
            @Override
            public void onResponse(Call<Loanhistory> call, retrofit2.Response<Loanhistory> response) {
                //dialog.dismiss();
                Loanhistory bal = response.body();
                DecimalFormat formatter = new DecimalFormat("#,##0.00");
                String amt = formatter.format(bal.getTotal());
                balance.setText("KES "+amt);
                //confirmPin(userAuth, dialog);
            }

            @Override
            public void onFailure(Call<Loanhistory> call, Throwable t) {
                Toast.makeText(LoanRepaymentActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                //Toast.makeText(LoginActivity.this, id+"\n"+name+"\n"+phn+"\n"+email+"\n", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoanRepaymentActivity.this, LoginActivity.class);
                startActivity(intent);
                t.printStackTrace();
                //dialog.dismiss();
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
                        stopService(new Intent(LoanRepaymentActivity.this, IService.class));
                        Intent intent = new Intent(LoanRepaymentActivity.this, LoginActivity.class);
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
