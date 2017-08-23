package com.example.lixnet.mkopo.adapters;

/**
 * Created by Lixnet on 2017-08-07.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.lixnet.mkopo.R;
import com.example.lixnet.mkopo.models.Sms;

import java.util.List;



/**
 * Created by Elisha on 7/24/2017.
 */

public class CustomListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Sms> smsItems;
    //ImageLoader imageLoader = AppController.getInstance().getImageLoader();

    public CustomListAdapter(Activity activity, List<Sms> smsItems) {
        this.activity = activity;
        this.smsItems = smsItems;
    }

    @Override
    public int getCount() {
        return smsItems.size();
    }

    @Override
    public Object getItem(int location) {
        return smsItems.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null)
            convertView = inflater.inflate(R.layout.sms, null);

        /*if (imageLoader == null)
            imageLoader = AppController.getInstance().getImageLoader();*/
        /*NetworkImageView thumbNail = (NetworkImageView) convertView
                .findViewById(R.id.thumbnail);*/
        TextView name = (TextView) convertView.findViewById(R.id.name);
        TextView transactionid = (TextView) convertView.findViewById(R.id.transactionid);
        TextView phone = (TextView) convertView.findViewById(R.id.phone);
        TextView date = (TextView) convertView.findViewById(R.id.date);
        TextView amount = (TextView) convertView.findViewById(R.id.amount);
        TextView balance = (TextView) convertView.findViewById(R.id.balance);
        TextView transactiontype = (TextView) convertView.findViewById(R.id.transactiontype);
        TextView type = (TextView) convertView.findViewById(R.id.type);

        // getting movie data for the row
        Sms s = smsItems.get(position);

        // thumbnail image
        //thumbNail.setImageUrl(p.getThumbnailUrl(), imageLoader);

        // title

        // rating
        transactionid.setText("Transaction ID: " + String.valueOf(s.getTransactionID()));

        name.setText("Name: " +s.getName());

        phone.setText("Phone: " +s.getPhone());

        date.setText("Date: " +s.getTimestamp());

        amount.setText("Amount: " +s.getAmount());

        type.setText("Type: " +s.getType());

        balance.setText("Balance: " +s.getBalance());

        transactiontype.setText("Transaction Type: " +s.getTransactiontype());

        return convertView;
    }

}
