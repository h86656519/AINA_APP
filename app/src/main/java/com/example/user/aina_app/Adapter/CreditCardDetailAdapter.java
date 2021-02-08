package com.example.user.aina_app.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.user.aina_app.R;

import java.util.ArrayList;

public class CreditCardDetailAdapter extends RecyclerView.Adapter<CreditCardDetailAdapter.ViewHolder> {
    private ArrayList<String> bankname_list;
    private ArrayList<String> creditcardnbr_list;

    private final String TAG = "HealthFoodAdapter";

    public CreditCardDetailAdapter(Context context, ArrayList<String> bankname, ArrayList<String> creditcardnbr) {
        this.bankname_list = bankname;
        this.creditcardnbr_list = creditcardnbr;

    }

    @Override
    public CreditCardDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.creditcardlayout, viewGroup, false);
        return new CreditCardDetailAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(CreditCardDetailAdapter.ViewHolder holder, final int position) {
        String name = bankname_list.get(position);
        String nbr = creditcardnbr_list.get(position);
        holder.bankname.setText(name);
        holder.cardnbr.setText(nbr);
//        holder.setOnItemClick(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //設定你點擊每個Item後，要做的事情
//                Log.i(TAG, "positon" + position);
//            }
//        });
        holder.trach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "trach  : " + position);
                removeProduct(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return bankname_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView bankname;
        private TextView cardnbr;
        private ImageView trach;
        private View view;

        ViewHolder(final View itemView) {
            super(itemView);
            view = itemView;
            this.bankname = (TextView) itemView.findViewById(R.id.bankname);
            this.cardnbr = (TextView) itemView.findViewById(R.id.carb_nbr);
            this.trach = (ImageView) itemView.findViewById(R.id.trash_image);
        }

        public void setOnItemClick(View.OnClickListener l) {
            this.view.setOnClickListener(l);
        }
    }

    public void removeProduct(int position) {
        bankname_list.remove(position);
        creditcardnbr_list.remove(position);
        // Log.i(TAG, "totalprice b : " + totalprice);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, bankname_list.size());
        notifyItemRangeChanged(position, creditcardnbr_list.size());
    }
}
