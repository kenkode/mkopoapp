package com.example.lixnet.mkopo.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.lixnet.mkopo.R;
import com.example.lixnet.mkopo.activities.LoanDetailsActivity;
import com.example.lixnet.mkopo.models.MyLoans;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Lixnet on 2017-08-30.
 */

public class LoanAdapter  extends BaseAdapter {
    private final ArrayList<MyLoans> loans;
    private final LayoutInflater inflater;
    private final Context context;

    public LoanAdapter(Context context, ArrayList<MyLoans> loans) {
        this.loans = loans;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return loans.size();
    }

    @Override
    public Object getItem(int position) {
        return loans.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.loanmodel, null);
        }

        final MyLoans loan = loans.get(position);
        /*final Gas gas = new Gas();
        gas.setId(rGas.getId());
        gas.setName(rGas.getName());
        gas.setPrice(rGas.getPrice());*/
        //gas.setSize(rGas.getSize());

        final TextView amount= (TextView) convertView.findViewById(R.id.amount);

        Button viewLoan = (Button) convertView.findViewById(R.id.btn_view);

        viewLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoanDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id",loan.getId());
                intent.putExtra("amount",loan.getAmount());
                intent.putExtra("status",loan.getStatus());
                intent.putExtra("date",loan.getCreated_at());
                context.startActivity(intent);
            }
        });


        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        String amt = formatter.format(loan.getAmount());
        amount.setText("KES "+amt);

        return convertView;

    }
}
