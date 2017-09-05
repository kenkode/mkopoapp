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
import com.example.lixnet.mkopo.models.Loanstatus;
import com.example.lixnet.mkopo.models.MyLoans;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Kenkode PC on 9/2/2017.
 */

public class StatusAdapter extends BaseAdapter {
    private final ArrayList<Loanstatus> loans;
    private final LayoutInflater inflater;
    private final Context context;

    public StatusAdapter(Context context, ArrayList<Loanstatus> loans) {
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
            convertView = inflater.inflate(R.layout.loanstatus_model, null);
        }

        final Loanstatus loan = loans.get(position);
        /*final Gas gas = new Gas();
        gas.setId(rGas.getId());
        gas.setName(rGas.getName());
        gas.setPrice(rGas.getPrice());*/
        //gas.setSize(rGas.getSize());

        final TextView amount= (TextView) convertView.findViewById(R.id.amount);

        final TextView status= (TextView) convertView.findViewById(R.id.status);

        final TextView date= (TextView) convertView.findViewById(R.id.date);


        /*viewLoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, LoanDetailsActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("id",loan.getId());
                intent.putExtra("amount",loan.getLoan_amount());
                intent.putExtra("status",loan.getStatus());
                intent.putExtra("date",loan.getCreated_at());
                context.startActivity(intent);
            }
        });*/


        DecimalFormat formatter = new DecimalFormat("#,##0.00");
        String amt = formatter.format(loan.getLoan_amount());
        amount.setText("KES "+amt);
        if(loan.getStatus().equals("active")){
            status.setText("Active");
        }else if(loan.getStatus().equals("rejected")){
            status.setText("Rejected");
        }else if(loan.getStatus().equals("pending")){
            status.setText("Pending");
        }else if(loan.getStatus().equals("inactive")){
            status.setText("Paid");
        }

        try {
            DateFormat outputFormat = new SimpleDateFormat("dd/MMM/yyyy");
            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            String inputText = loan.getCreated_at();
            Date d = inputFormat.parse(inputText);
            String outputText = outputFormat.format(d);
            date.setText(outputText);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convertView;

    }
}
