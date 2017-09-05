package com.example.lixnet.mkopo.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.example.lixnet.mkopo.R;
import com.example.lixnet.mkopo.activities.LoanDetailsActivity;
import com.example.lixnet.mkopo.activities.LoanRepaymentActivity;
import com.example.lixnet.mkopo.activities.PaymentDetailsActivity;
import com.example.lixnet.mkopo.models.Loanhistory;
import com.example.lixnet.mkopo.models.MyLoans;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Created by Kenkode PC on 9/2/2017.
 */

public class PaymentAdapter extends BaseAdapter {
    private final ArrayList<Loanhistory> loans;
    private final LayoutInflater inflater;
    private final Context context;

    public PaymentAdapter(Context context, ArrayList<Loanhistory> loans) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        if(convertView == null) {
            convertView = inflater.inflate(R.layout.loanpayment_model, null);
        }

        final Loanhistory loan = loans.get(position);
        /*final Gas gas = new Gas();
        gas.setId(rGas.getId());
        gas.setName(rGas.getName());
        gas.setPrice(rGas.getPrice());*/
        //gas.setSize(rGas.getSize());

        final TextView amount= (TextView) convertView.findViewById(R.id.amount);
        final TextView instalments= (TextView) convertView.findViewById(R.id.instalments);
        Button viewLoan = (Button) convertView.findViewById(R.id.btn_view);

        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        String amt = formatter.format(loan.getAmount_paid());
        amount.setText("KES "+amt);

        instalments.setText("#"+String.valueOf(position+1));

        viewLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PaymentDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id",loan.getId());
                intent.putExtra("amount",loan.getAmount_paid());
                intent.putExtra("date",loan.getCreated_at());
                intent.putExtra("instalment",position+1);
                context.startActivity(intent);
            }
        });

        return convertView;

    }
}

