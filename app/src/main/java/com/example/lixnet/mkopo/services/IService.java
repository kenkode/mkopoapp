package com.example.lixnet.mkopo.services;

import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.example.lixnet.mkopo.activities.LoginActivity;
import com.example.lixnet.mkopo.activities.MainActivity;
import com.example.lixnet.mkopo.adapters.CustomListAdapter;
import com.example.lixnet.mkopo.helpers.GEPreference;
import com.example.lixnet.mkopo.helpers.GsonHelper;
import com.example.lixnet.mkopo.models.Sms;
import com.example.lixnet.mkopo.retrofit.RetrofitInterface;
import com.example.lixnet.mkopo.retrofit.ServiceGenerator;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

import static android.content.ContentValues.TAG;

/**
 * Created by Belal on 12/30/2016.
 */

public class IService extends Service {
    //creating a mediaplayer object
  //  private MediaPlayer player;
    private GEPreference preference;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //getting systems default ringtone
        Toast.makeText(getApplicationContext(),"Start Sms activity",Toast.LENGTH_SHORT).show();
        getAllSms();
        //we have some options for service
        //start sticky means service will be explicity started and stopped
        return START_STICKY;
    }

    public void getAllSms() {
       // ActivityCompat.requestPermissions(getApplicationContext(), new String[]{"android.permission.READ_SMS"}, 1);
        List<Sms> lstSms = new ArrayList<Sms>();
        List<String> sms = new ArrayList<String>();

        preference = new GEPreference(this);

        //Sms objSms = new Sms();
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
                String name = "";
                String phone = "";
                String transactId = "";
                String type = "";
                String transtype = "";
                String balance = "";
                String amount = "";
                String timestamp = "";

                // s = new Sms();


                if (body.contains("bought")) {
                    //s.setTransactiontype("MPESA");
                    //s.setTransactionID(data[0]);
                    //s.setName(data[4]+" "+data[5]);
                    //s.setPhone(data[6]);
                    transtype = "MPESA";
                    transactId = data[0];
                    //name = data[4]+" "+data[5];
                    //phone = data[6];

                    if (data[6].equals("for")) {
                        //s.setPhone(data[7]);
                        phone = data[7];
                    }

                    for (int l = 0; l < data.length; l++) {
                        if (data[l].startsWith("on")) {
                            //s.setTimestamp(data[l + 1] + " - " + data[l + 3] + data[l + 4].replace(".New", ""));
                            timestamp = data[l + 1] + " - " + data[l + 3] + data[l + 4].replace(".New", "");
                            break;
                        }
                    }

                    for (int j = 0; j < data.length; j++) {
                        if (data[j].startsWith("Ksh")) {
                            //s.setAmount(data[j]);
                            amount = data[j];
                            break;

                        }

                    }
                    //Toast.makeText(MainActivity.this,j.toString(),Toast.LENGTH_SHORT).show();
                    for (int k = 0; k < data.length; k++) {
                        if (data[k].startsWith("balance")) {
                           // s.setBalance(data[k + 2]);
                            balance = data[k + 2];
                            break;
                        }
                    }

                    //s.setType("Airtime");

                    type = "Airtime";

                    Sms s = new Sms(preference.getUser().get(GEPreference.USER_ID),name, transactId, phone, amount, timestamp, balance, type, transtype);
                    saveSms(s);

                } else if (body.contains("sent")) {
                    //s.setTransactiontype("MPESA");
                    //s.setTransactionID(data[0]);

                    transtype = "MPESA";
                    transactId = data[0];

                    if (!data[7].matches("[0-9]+") && !data[7].equals("for")) {
                        //s.setName(data[5] + " " + data[6] + " " + data[7]);
                        name = data[5] + " " + data[6] + " " + data[7];

                    } else {
                       // s.setName(data[5] + " " + data[6]);
                        name = data[5] + " " + data[6];
                    }

                    for (int l = 0; l < data.length; l++) {
                        if (data[l].startsWith("on")) {
                           // s.setTimestamp(data[l + 1] + " - " + data[l + 3] + data[l + 4].replace(".", ""));
                            timestamp = data[l + 1] + " - " + data[l + 3] + data[l + 4].replace(".", "");
                            break;
                        }
                    }

                    for (int i = 0; i < data.length; i++) {
                        if (data[i].matches("[0-9]+")) {
                            //s.setPhone(data[i]);
                            phone = data[i];
                        }
                    }

                    int x = 0;
                    int j;

                    for (j = 0; j < data.length; j++) {
                        if (data[j].startsWith("Ksh")) {
                            x = j;
                            //s.setAmount(data[j]);
                            amount = data[j];
                            break;

                        }

                    }
                    //s.setType("Sent");
                    type = "Sent";
                    //Toast.makeText(MainActivity.this,j.toString(),Toast.LENGTH_SHORT).show();
                    for (int k = 0; k < data.length; k++) {
                        if (data[k].startsWith("balance")) {
                            //s.setBalance(data[k + 2]);
                            balance = data[k + 2];
                            break;
                        }
                    }

                    Sms s = new Sms(preference.getUser().get(GEPreference.USER_ID),name, transactId, phone, amount, timestamp, balance, type, transtype);
                    saveSms(s);
                } else if (body.contains("received")) {
                    //s.setTransactiontype("MPESA");
                   // s.setTransactionID(data[0]);

                    transtype = "MPESA";
                    transactId = data[0];

                    if (!data[8].matches("[0-9]+")) {
                        //s.setName(data[6] + " " + data[7] + " " + data[8]);
                        name = data[6] + " " + data[7] + " " + data[8];
                    } else {
                       // s.setName(data[6] + " " + data[7]);
                        name = data[6] + " " + data[7];
                    }

                    for (int l = 0; l < data.length; l++) {
                        if (data[l].startsWith("on")) {
                            //s.setTimestamp(data[l + 1] + " - " + data[l + 3] + data[l + 4].replace(".", ""));
                            timestamp = data[l + 1] + " - " + data[l + 3] + data[l + 4].replace(".", "");
                            break;
                        }
                    }

                    for (int i = 0; i < data.length; i++) {
                        if (data[i].matches("[0-9]+")) {
                           // s.setPhone(data[i]);
                            phone = data[i];
                        }
                    }

                    for (int j = 0; j < data.length; j++) {
                        if (data[j].startsWith("Ksh")) {
                           // s.setAmount(data[j]);
                            amount = data[j];
                            break;

                        }

                    }
                    //Toast.makeText(MainActivity.this,j.toString(),Toast.LENGTH_SHORT).show();
                    for (int k = 0; k < data.length; k++) {
                        if (data[k].startsWith("balance")) {
                            //s.setBalance(data[k + 2]);
                            balance = data[k + 2];
                            break;
                        }
                    }

                   // s.setType("Received");
                    type = "Received";
                    Sms s = new Sms(preference.getUser().get(GEPreference.USER_ID),name, transactId, phone, amount, timestamp, balance, type, transtype);
                    saveSms(s);
                }
                //Toast.makeText(MainActivity.this,data[5],Toast.LENGTH_LONG).show();

                else if (body.contains("PMWithdraw") || body.contains("Withdraw")) {
                    //s.setTransactionID(data[0]);
                    //s.setTransactiontype("MPESA");

                    transactId = data[0];
                    transtype = "MPESA";

                    //s.setName(data[6] + " " + data[7]);
                    //s.setPhone(data[8]);
                    //s.setTimestamp(data[2] + " - " + data[4] + data[5].replace("Withdraw", ""));

                    timestamp = data[2] + " - " + data[4] + data[5].replace("Withdraw", "");


                    for (int j = 0; j < data.length; j++) {
                        if (data[j].startsWith("Ksh")) {
                            //s.setAmount(data[j]);
                            amount = data[j];
                            break;

                        }

                    }
                    //Toast.makeText(MainActivity.this,j.toString(),Toast.LENGTH_SHORT).show();
                    for (int k = 0; k < data.length; k++) {
                        if (data[k].startsWith("balance")) {
                            //s.setBalance(data[k + 2]);
                            balance = data[k + 2];
                            break;
                        }
                    }

                    //s.setAmount(data[6]);
                    //s.setType("Withdraw");

                    type = "Withdraw";

                    Sms s = new Sms(preference.getUser().get(GEPreference.USER_ID),name, transactId, phone, amount, timestamp, balance, type, transtype);
                    saveSms(s);

                    //s.setBalance(data[22]);
                } else if (body.contains("Give")) {
                    //s.setTransactionID(data[0]);
                    //s.setTransactiontype("MPESA");

                    transactId = data[0];
                    transtype = "MPESA";

                    //s.setName(data[6] + " " + data[7]);
                    //s.setPhone(data[8]);
                    //s.setTimestamp(data[3] + " - " + data[5] + data[6]);

                    timestamp = data[3] + " - " + data[5] + data[6];

                    for (int j = 0; j < data.length; j++) {
                        if (data[j].startsWith("Ksh")) {
                           // s.setAmount(data[j]);
                            amount = data[j];
                            break;

                        }

                    }
                    //Toast.makeText(MainActivity.this,j.toString(),Toast.LENGTH_SHORT).show();
                    for (int k = 0; k < data.length; k++) {
                        if (data[k].startsWith("balance")) {
                            //s.setBalance(data[k + 2]);
                            balance = data[k + 2];
                            break;
                        }
                    }

                    //s.setAmount(data[6]);
                    //s.setType("Deposit");
                    type = "Deposit";
                    Sms s = new Sms(preference.getUser().get(GEPreference.USER_ID),name, transactId, phone, amount, timestamp, balance, type, transtype);
                    saveSms(s);
                    //s.setBalance(data[22]);
                }

                //smsList.add(s);
                //adapter = new CustomListAdapter(this, smsList);
                //Toast.makeText(MainActivity.this,data[0],Toast.LENGTH_LONG).show();
                //lstSms.add(objSms);
            }
        }
        // else {
        // throw new RuntimeException("You have no SMS");
        // }
        //c.close();

        //return adapter;
    }

    private void saveSms(final Sms sms) {

        RetrofitInterface retrofitInterface = ServiceGenerator.getClient().create(RetrofitInterface.class);
        Gson gson = GsonHelper.getBuilder().create();
        final String smsJson = gson.toJson(sms);
        //Toast.makeText(RegisterActivity.this, userJson, Toast.LENGTH_LONG).show();
        Call<Sms> addSms = retrofitInterface.saveSMS(smsJson);

        addSms.enqueue(new Callback<Sms>() {
            @Override
            public void onResponse(Call<Sms> call, retrofit2.Response<Sms> response) {
                //Toast.makeText(RegisterActivity.this, userJson, Toast.LENGTH_LONG).show();
                Log.e(TAG, smsJson);
                Sms sms = response.body();

            }

            @Override
            public void onFailure(Call<Sms> call, Throwable t) {
                t.printStackTrace();
                //Toast.makeText(RegisterActivity.this, userJson, Toast.LENGTH_LONG).show();
                Log.e(TAG, smsJson);

            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //stopping the player when service is destroyed
      //  player.stop();
    }
}