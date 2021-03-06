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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.lixnet.mkopo.R;
import com.example.lixnet.mkopo.data.StatusData;
import com.example.lixnet.mkopo.helpers.GEPreference;
import com.example.lixnet.mkopo.data.LoanData;
import com.example.lixnet.mkopo.services.IService;

public class LoanStatusActivity extends AppCompatActivity {

    private GEPreference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_status);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preference = new GEPreference(this);

        LinearLayout errorLayout = (LinearLayout) findViewById(R.id.error_layout_price);
        errorLayout.setVisibility(View.GONE);
        ProgressBar progressBar = (ProgressBar) findViewById(R.id.load_prices);

        Intent intent = getIntent();
        //int size = intent.getIntExtra(Constants.NAME, 0);

        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Sisi");
        }

        ListView listView = (ListView) findViewById(R.id.price_list);
        StatusData loan = new StatusData(this);
        loan.getLoans(preference.getUser().get(GEPreference.USER_ID), listView, errorLayout, progressBar);

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
                        stopService(new Intent(LoanStatusActivity.this, IService.class));
                        Intent intent = new Intent(LoanStatusActivity.this, LoginActivity.class);
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
