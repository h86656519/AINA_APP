package com.example.user.aina_app.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.aina_app.R;

import java.util.ArrayList;

public class ManAdapter  extends RecyclerView.Adapter<ManAdapter.ViewHolder> {
    private ArrayList<String> productname;
    private final String TAG = "ManAdapter";

    public ManAdapter(ArrayList<String> productname) {
        this.productname = productname;

    }

    @Override
    public ManAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardlayout, viewGroup, false);
        return new ManAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ManAdapter.ViewHolder holder, final int position) {
        String name = productname.get(position);
        Log.i(TAG,"price " + name);
        holder.productname.setText(name);
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
        return productname.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView productname;
        private View view;

        ViewHolder(final View itemView) {
            super(itemView);
            view = itemView;
            this.productname = (TextView) itemView.findViewById(R.id.producname_textview);

        }
        public void setOnItemClick(View.OnClickListener l) {
            this.view.setOnClickListener(l);
        }
    }
}
