package com.example.lixnet.mkopo.activities;

import android.app.AlertDialog;
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
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lixnet.mkopo.R;
import com.example.lixnet.mkopo.adapters.LoanAdapter;
import com.example.lixnet.mkopo.helpers.GEPreference;
import com.example.lixnet.mkopo.models.MyLoans;
import com.example.lixnet.mkopo.retrofit.RetrofitInterface;
import com.example.lixnet.mkopo.retrofit.ServiceGenerator;
import com.example.lixnet.mkopo.services.IService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

public class LoanDetailsActivity extends AppCompatActivity {

    private GEPreference preference;

    TextView amounttxt,datetxt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Bundle bundle = getIntent().getExtras();
        final int id = bundle.getInt("id");
        final double amount = bundle.getDouble("amount");
        final String date = bundle.getString("date");

        amounttxt = (TextView)findViewById(R.id.amounttxt);
        datetxt = (TextView)findViewById(R.id.datetxt);

        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        String amt = formatter.format(amount);

        amounttxt.setText("KES "+amt);
        datetxt.setText(date);

        preference = new GEPreference(this);

        Button viewLoan = (Button)findViewById(R.id.btn_repay);

        viewLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoanDetailsActivity.this, RepaymentActivity.class);
                startActivity(intent);
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
                        stopService(new Intent(LoanDetailsActivity.this, IService.class));
                        Intent intent = new Intent(LoanDetailsActivity.this, LoginActivity.class);
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
