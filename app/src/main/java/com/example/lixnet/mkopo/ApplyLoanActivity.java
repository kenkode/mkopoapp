package com.example.lixnet.mkopo;

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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lixnet.mkopo.activities.LoginActivity;
import com.example.lixnet.mkopo.activities.SummaryActivity;
import com.example.lixnet.mkopo.helpers.GEPreference;
import com.example.lixnet.mkopo.services.IService;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ApplyLoanActivity extends AppCompatActivity {

    TextView amount;

    EditText means;

    private GEPreference preference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_loan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ButterKnife.inject(this);

        preference = new GEPreference(this);

        Bundle bundle = getIntent().getExtras();
        final String amt = bundle.getString("amount");

        amount = (TextView)findViewById(R.id.amount);
        means = (EditText)findViewById(R.id.input_means);
        Button apply = (Button)findViewById(R.id.applybtn);

        amount.setText("Apply Loan of KES "+amt);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = means.getText().toString();
                if(type.isEmpty()){
                    Toast.makeText(ApplyLoanActivity.this,"Please insert phone number (safaricom) or bank account number to send money!",Toast.LENGTH_LONG);
                }else {
                    Toast.makeText(ApplyLoanActivity.this, "Loan Successfully applied!", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    public void apply(View v){
        String type = means.getText().toString();
        if(type.isEmpty()){
            Toast.makeText(ApplyLoanActivity.this,"Please insert phone number (safaricom) or bank account number to send money!",Toast.LENGTH_LONG);
        }else {
            Toast.makeText(ApplyLoanActivity.this, "Loan Successfully applied!", Toast.LENGTH_SHORT);
        }
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
                        stopService(new Intent(ApplyLoanActivity.this, IService.class));
                        Intent intent = new Intent(ApplyLoanActivity.this, LoginActivity.class);
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
