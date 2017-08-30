package com.example.lixnet.mkopo.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.lixnet.mkopo.R;
import com.example.lixnet.mkopo.adapters.PaymentProcessAdapter;
import com.example.lixnet.mkopo.helpers.GEPreference;
import com.example.lixnet.mkopo.services.IService;

public class RepaymentActivity extends AppCompatActivity {
    private GEPreference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repayment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preference = new GEPreference(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.saf_listview);

        String[] process = getResources().getStringArray(R.array.mpesa_payment);

        PaymentProcessAdapter adapter = new PaymentProcessAdapter(this, process);

        recyclerView.setAdapter(adapter);

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
                        stopService(new Intent(RepaymentActivity.this, IService.class));
                        Intent intent = new Intent(RepaymentActivity.this, LoginActivity.class);
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
