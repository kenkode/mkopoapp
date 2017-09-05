package com.example.lixnet.mkopo.data;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.example.lixnet.mkopo.adapters.HistoryAdapter;
import com.example.lixnet.mkopo.adapters.PaymentAdapter;
import com.example.lixnet.mkopo.models.Loanhistory;
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

public class PaymentData {
    private final Context context;

    public PaymentData(Context context) {
        this.context = context;
    }

    public void getLoans(final int loanid, final ListView listView, final LinearLayout errorLinear, final ProgressBar loadPrice) {

        final ArrayList<Loanhistory> rLoans = new ArrayList<>();

        RetrofitInterface retrofitInterface = ServiceGenerator.getClient().create(RetrofitInterface.class);

        Call<List<Loanhistory>> retroGases = retrofitInterface.getLoanHistory(loanid);

        retroGases.enqueue(new Callback<List<Loanhistory>>() {
            @Override
            public void onResponse(Call<List<Loanhistory>> call, retrofit2.Response<List<Loanhistory>> response) {
                List<Loanhistory> loans = response.body();
                for (Loanhistory rLoan : loans) {
                    rLoans.add(rLoan);
                }
                if(rLoans.size() <= 0) {
                    errorLinear.setVisibility(View.VISIBLE);
                }else {
                    errorLinear.setVisibility(View.GONE);
                }
                loadPrice.setVisibility(View.GONE);
                PaymentAdapter adapter = new PaymentAdapter(context, rLoans);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Loanhistory>> call, Throwable t) {
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
                        getLoans(loanid, listView, errorLinear, loadPrice);
                    }
                });
                snackbar.show();
            }
        });


    }

}

