package com.example.lixnet.mkopo.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.lixnet.mkopo.R;

/**
 * Created by Lixnet on 2017-08-30.
 */

public class PaymentProcessAdapter extends RecyclerView.Adapter<PaymentProcessAdapter.ViewHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private String[] process;

    public PaymentProcessAdapter(Context context, String[] process) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.process = process;
    }

    @Override
    public void onBindViewHolder(PaymentProcessAdapter.ViewHolder holder, int position) {
        holder.textView.setText(process[position]);
    }

    @Override
    public PaymentProcessAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.payment_process, null);
        return new ViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return process.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.process_step);
        }
    }

}
