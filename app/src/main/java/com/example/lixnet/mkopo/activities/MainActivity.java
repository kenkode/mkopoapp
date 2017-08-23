package com.example.lixnet.mkopo.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.lixnet.mkopo.adapters.CustomListAdapter;
import com.example.lixnet.mkopo.R;
import com.example.lixnet.mkopo.helpers.GEPreference;
import com.example.lixnet.mkopo.models.Sms;

import android.support.v7.widget.Toolbar;

/**
 * @author Paresh N. Mayani
 * (w): http://www.technotalkative.com/
 */
public class MainActivity extends AppCompatActivity {
    /**
     * Called when the activity is first created.
     */
    private CustomListAdapter adapter;
    private List<Sms> smsList = new ArrayList<Sms>();

    private GEPreference preference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        preference = new GEPreference(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int GET_MY_PERMISSION = 1;
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_SMS)
                    != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                        Manifest.permission.READ_SMS)) {
            /* do nothing*/
                } else {

                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_SMS}, GET_MY_PERMISSION);
                }
            }
        }


        final ListView lViewSMS = (ListView) findViewById(R.id.listViewSMS);

        if (getAllSms() != null) {
            //ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.READ_SMS"}, 1);
            //adapter = new CustomListAdapter(this, smsList);
            lViewSMS.setAdapter(getAllSms());

            SearchView searchView = (SearchView) findViewById(R.id.searchBar);

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    int textlength = newText.length();
                    ArrayList<Sms> tempArrayList = new ArrayList<Sms>();
                    for (Sms c : smsList) {
                        if (textlength <= c.getName().length()) {
                            if (c.getName().toLowerCase().contains(newText.toString().toLowerCase())) {
                                tempArrayList.add(c);
                            }
                        }
                    }
                    adapter = new CustomListAdapter(MainActivity.this, tempArrayList);
                    lViewSMS.setAdapter(adapter);
                    //adapter.getFilter().filter(newText);
                    return false;
                }
            });

            //ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, getAllSms());
            //lViewSMS.setAdapter(adapter);
        }
    }


    public CustomListAdapter getAllSms() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{"android.permission.READ_SMS"}, 1);
        List<Sms> lstSms = new ArrayList<Sms>();
        List<String> sms = new ArrayList<String>();
        Sms objSms = new Sms();
        Uri message = Uri.parse("content://sms/inbox");
        ContentResolver cr = getContentResolver();

        Cursor c = cr.query(message, null, null, null, null);
        /*startManagingCursor(c);
        int totalSMS = c.getCount();

        if (c.moveToFirst()) {
            for (int i = 0; i < totalSMS; i++) {

                objSms = new Sms();
                objSms.setId(c.getString(c.getColumnIndexOrThrow("_id")));
                objSms.setAddress(c.getString(c
                        .getColumnIndexOrThrow("address")));
                objSms.setMsg(c.getString(c.getColumnIndexOrThrow("body")));
                objSms.setReadState(c.getString(c.getColumnIndex("read")));
                objSms.setTime(c.getString(c.getColumnIndexOrThrow("date")));
                if (c.getString(c.getColumnIndexOrThrow("type")).contains("1")) {
                    objSms.setFolderName("inbox");
                } else {
                    objSms.setFolderName("sent");
                }

                lstSms.add(objSms);
                c.moveToNext();
            }
        }*/
        while (c.moveToNext()) {

            String body = c.getString(c.getColumnIndexOrThrow("body"));
            String address = c.getString(c.getColumnIndexOrThrow("address"));
            /*if(address.matches("[+-]?\\d*(\\.\\d+)?") == false) {
                String msg = address + "\n" + body;
                sms.add(msg);
            }*/
            if (address.equals("MPESA")) {
                String msg = address + "\n" + body;
                String[] data = body.split("\\s+");
                Sms s = new Sms();


                if (body.contains("bought")) {
                    s.setTransactiontype("MPESA");
                    s.setTransactionID(data[0]);
                    //s.setName(data[4]+" "+data[5]);
                    //s.setPhone(data[6]);
                    if (data[6].equals("for")) {
                        s.setPhone(data[7]);
                    }

                    for (int l = 0; l < data.length; l++) {
                        if (data[l].startsWith("on")) {
                            s.setTimestamp(data[l + 1] + " - " + data[l + 3] + data[l + 4].replace(".New", ""));
                            break;
                        }
                    }

                    for (int j = 0; j < data.length; j++) {
                        if (data[j].startsWith("Ksh")) {
                            s.setAmount(data[j]);
                            break;

                        }

                    }
                    //Toast.makeText(MainActivity.this,j.toString(),Toast.LENGTH_SHORT).show();
                    for (int k = 0; k < data.length; k++) {
                        if (data[k].startsWith("balance")) {
                            s.setBalance(data[k + 2]);
                            break;
                        }
                    }

                    s.setType("Bought");
                } else if (body.contains("sent")) {
                    s.setTransactiontype("MPESA");
                    s.setTransactionID(data[0]);

                    if (!data[7].matches("[0-9]+") && !data[7].equals("for")) {
                        s.setName(data[5] + " " + data[6] + " " + data[7]);

                    } else {
                        s.setName(data[5] + " " + data[6]);
                    }

                    for (int l = 0; l < data.length; l++) {
                        if (data[l].startsWith("on")) {
                            s.setTimestamp(data[l + 1] + " - " + data[l + 3] + data[l + 4].replace(".", ""));
                            break;
                        }
                    }

                    for (int i = 0; i < data.length; i++) {
                        if (data[i].matches("[0-9]+")) {
                            s.setPhone(data[i]);
                        }
                    }

                    int x = 0;
                    int j;

                    for (j = 0; j < data.length; j++) {
                        if (data[j].startsWith("Ksh")) {
                            x = j;
                            s.setAmount(data[j]);
                            break;

                        }

                    }
                    s.setType("Sent");
                    //Toast.makeText(MainActivity.this,j.toString(),Toast.LENGTH_SHORT).show();
                    for (int k = 0; k < data.length; k++) {
                        if (data[k].startsWith("balance")) {
                            s.setBalance(data[k + 2]);
                            break;
                        }
                    }
                } else if (body.contains("received")) {
                    s.setTransactiontype("MPESA");
                    s.setTransactionID(data[0]);

                    if (!data[8].matches("[0-9]+")) {
                        s.setName(data[6] + " " + data[7] + " " + data[8]);
                    } else {
                        s.setName(data[6] + " " + data[7]);
                    }

                    for (int l = 0; l < data.length; l++) {
                        if (data[l].startsWith("on")) {
                            s.setTimestamp(data[l + 1] + " - " + data[l + 3] + data[l + 4].replace(".", ""));
                            break;
                        }
                    }

                    for (int i = 0; i < data.length; i++) {
                        if (data[i].matches("[0-9]+")) {
                            s.setPhone(data[i]);
                        }
                    }

                    for (int j = 0; j < data.length; j++) {
                        if (data[j].startsWith("Ksh")) {
                            s.setAmount(data[j]);
                            break;

                        }

                    }
                    //Toast.makeText(MainActivity.this,j.toString(),Toast.LENGTH_SHORT).show();
                    for (int k = 0; k < data.length; k++) {
                        if (data[k].startsWith("balance")) {
                            s.setBalance(data[k + 2]);
                            break;
                        }
                    }

                    s.setType("Received");
                }
                //Toast.makeText(MainActivity.this,data[5],Toast.LENGTH_LONG).show();

                else if (body.contains("PMWithdraw") || body.contains("Withdraw")) {
                    s.setTransactionID(data[0]);
                    s.setTransactiontype("MPESA");

                    //s.setName(data[6] + " " + data[7]);
                    //s.setPhone(data[8]);
                    s.setTimestamp(data[2] + " - " + data[4] + data[5].replace("Withdraw", ""));

                    for (int j = 0; j < data.length; j++) {
                        if (data[j].startsWith("Ksh")) {
                            s.setAmount(data[j]);
                            break;

                        }

                    }
                    //Toast.makeText(MainActivity.this,j.toString(),Toast.LENGTH_SHORT).show();
                    for (int k = 0; k < data.length; k++) {
                        if (data[k].startsWith("balance")) {
                            s.setBalance(data[k + 2]);
                            break;
                        }
                    }

                    //s.setAmount(data[6]);
                    s.setType("Withdraw");
                    //s.setBalance(data[22]);
                } else if (body.contains("Give")) {
                    s.setTransactionID(data[0]);
                    s.setTransactiontype("MPESA");

                    //s.setName(data[6] + " " + data[7]);
                    //s.setPhone(data[8]);
                    s.setTimestamp(data[3] + " - " + data[5] + data[6]);

                    for (int j = 0; j < data.length; j++) {
                        if (data[j].startsWith("Ksh")) {
                            s.setAmount(data[j]);
                            break;

                        }

                    }
                    //Toast.makeText(MainActivity.this,j.toString(),Toast.LENGTH_SHORT).show();
                    for (int k = 0; k < data.length; k++) {
                        if (data[k].startsWith("balance")) {
                            s.setBalance(data[k + 2]);
                            break;
                        }
                    }

                    //s.setAmount(data[6]);
                    s.setType("Deposit");
                    //s.setBalance(data[22]);
                }

                smsList.add(s);
                adapter = new CustomListAdapter(this, smsList);
                //Toast.makeText(MainActivity.this,data[0],Toast.LENGTH_LONG).show();
                //lstSms.add(objSms);
            }
        }
        // else {
        // throw new RuntimeException("You have no SMS");
        // }
        //c.close();

        return adapter;
    }

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");  //match a number with optional '-' and decimal.
    }

    public ArrayList fetchInbox() {
        ArrayList sms = new ArrayList();

        Uri uriSms = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(uriSms, new String[]{"_id", "address", "date", "body"}, null, null, null);

        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            String address = cursor.getString(1);
            String body = cursor.getString(3);

            System.out.println("======&gt; Mobile number =&gt; " + address);
            System.out.println("=====&gt; SMS Text =&gt; " + body);

            sms.add("Address=&gt; " + address + "n SMS =&gt; " + body);
        }
        return sms;

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
                        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
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