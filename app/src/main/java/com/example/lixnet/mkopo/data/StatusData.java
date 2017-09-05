package com.example.lixnet.mkopo.data;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.lixnet.mkopo.R;
import com.example.lixnet.mkopo.activities.StatusDetailsActivity;
import com.example.lixnet.mkopo.adapters.LoanAdapter;
import com.example.lixnet.mkopo.adapters.StatusAdapter;
import com.example.lixnet.mkopo.models.Loanstatus;
import com.example.lixnet.mkopo.models.MyLoans;
import com.example.lixnet.mkopo.retrofit.RetrofitInterface;
import com.example.lixnet.mkopo.retrofit.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by Kenkode PC on 9/2/2017.
 */

public class StatusData {
    private final Context context;

    public StatusData(Context context) {
        this.context = context;
    }

    public void getLoans(final String userid, final ListView listView, final LinearLayout errorLinear, final ProgressBar loadPrice) {

        final ArrayList<Loanstatus> rLoans = new ArrayList<>();

        RetrofitInterface retrofitInterface = ServiceGenerator.getClient().create(RetrofitInterface.class);

        Call<List<Loanstatus>> retroGases = retrofitInterface.getLoanStatus(userid);

        retroGases.enqueue(new Callback<List<Loanstatus>>() {
            @Override
            public void onResponse(Call<List<Loanstatus>> call, retrofit2.Response<List<Loanstatus>> response) {
                List<Loanstatus> loans = response.body();
                for (Loanstatus rLoan : loans) {
                    rLoans.add(rLoan);
                }
                if(rLoans.size() <= 0) {
                    errorLinear.setVisibility(View.VISIBLE);
                }else {
                    errorLinear.setVisibility(View.GONE);
                }
                loadPrice.setVisibility(View.GONE);
                StatusAdapter adapter = new StatusAdapter(context, rLoans);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        TextView amttv = (TextView)view.findViewById(R.id.amount);
                        String amount= amttv.getText().toString();
                        TextView statustv = (TextView)view.findViewById(R.id.status);
                        String status= statustv.getText().toString();
                        TextView datetv = (TextView)view.findViewById(R.id.date);
                        String date= datetv.getText().toString();
                        TextView interesttv = (TextView)view.findViewById(R.id.date);
                        String rate= interesttv.getText().toString();

                        Intent intent = new Intent(context, StatusDetailsActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("amount",amount);
                        intent.putExtra("status",status);
                        intent.putExtra("date",date);
                        intent.putExtra("rate",rate);
                        context.startActivity(intent);
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Loanstatus>> call, Throwable t) {
                t.printStackTrace();
                errorLinear.setVisibility(View.VISIBLE);
                loadPrice.setVisibility(View.GONE);
                t.printStackTrace();
                final Snackbar snackbar = Snackbar.make(listView, "Something went wrong!", Snackbar.LENGTH_INDEFINITE);
                snackbar.setAction("Retry", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackbar.dismiss();
                        errorLinear.setVisibility(View.GONE);
                        loadPrice.setVisibility(View.VISIBLE);
                        getLoans(userid, listView, errorLinear, loadPrice);
                    }
                });
                snackbar.show();
            }
        });


    }

}

