package com.example.user.aina_app.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.aina_app.R;

import java.util.ArrayList;

public class OldManHealthAdapter extends RecyclerView.Adapter<OldManHealthAdapter.ViewHolder>{
    private ArrayList<String> oldproductname;
    private final String TAG = "OldManHealthAdapter";

    public OldManHealthAdapter(ArrayList<String> oldproductname) {
        this.oldproductname = oldproductname;

    }

    @Override
    public OldManHealthAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cardlayout, viewGroup, false);
        return new OldManHealthAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(OldManHealthAdapter.ViewHolder holder, final int position) {
        String producname = oldproductname.get(position);
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
        return oldproductname.size();
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
