package com.example.user.aina_app.Adapter;

import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.aina_app.R;

import java.util.ArrayList;

public class BabyAdapter extends RecyclerView.Adapter<BabyAdapter.ViewHolder> {
    private ArrayList<String> productname;
    private final String TAG = "BabyAdapter";

    public BabyAdapter(ArrayList<String> productname) {
        this.productname = productname;

    }

    @Override
    public BabyAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardlayout, viewGroup, false);
        return new BabyAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(BabyAdapter.ViewHolder holder, final int position) {
        String name = productname.get(position);
        holder.producname.setText(name);
        holder.setOnItemClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //設定你點擊每個Item後，要做的事情
                Log.i(TAG, "positon" + position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return productname.size();
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
