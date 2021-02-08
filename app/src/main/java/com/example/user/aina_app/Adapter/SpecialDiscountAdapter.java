package com.example.user.aina_app.Adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.aina_app.R;

import java.util.ArrayList;

public class SpecialDiscountAdapter extends RecyclerView.Adapter<SpecialDiscountAdapter.ViewHolder> {
    private ArrayList<String> specialproductname;
    private final String TAG = "SpecialDiscountAdapter";

    public SpecialDiscountAdapter(ArrayList<String> specialproductname) {
        this.specialproductname = specialproductname;

    }

    @Override
    public SpecialDiscountAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardlayout, viewGroup, false);
        return new SpecialDiscountAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SpecialDiscountAdapter.ViewHolder holder, final int position) {
        String producname = specialproductname.get(position);
        holder.producname.setText(producname);
        holder.setOnItemClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //設定你點擊每個Item後，要做的事情
                Log.i(TAG,"positon" + position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return specialproductname.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView producname;
        private View view;

        ViewHolder(final View itemView) {
            super(itemView);
            view = itemView;
            this.producname = (TextView) itemView.findViewById(R.id.producname_textview);
        }
        public void setOnItemClick(View.OnClickListener l) {
            this.view.setOnClickListener(l);
        }
    }
}
