package com.example.user.aina_app.Adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.user.aina_app.R;

import java.util.ArrayList;

public class HealthRecommendAdapter extends RecyclerView.Adapter<HealthRecommendAdapter.ViewHolder> {
    private ArrayList<Integer> healthRecommendImage;
    private final String TAG = "HealthRecommendAdapter";

    public HealthRecommendAdapter(ArrayList<Integer> healthRecommendImage) {
        this.healthRecommendImage = healthRecommendImage;

    }

    @Override
    public HealthRecommendAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.healthrecommendlayout, viewGroup, false);

        return new HealthRecommendAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HealthRecommendAdapter.ViewHolder holder, final int position) {
        Integer image = healthRecommendImage.get(position);
        holder.image.setImageResource(image);
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
        return healthRecommendImage.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView image;
        private View view;

        ViewHolder(final View itemView) {
            super(itemView);
            view = itemView;
            this.image = (ImageView) itemView.findViewById(R.id.healthRecommend_imageview);
        }
        public void setOnItemClick(View.OnClickListener l) {
            this.view.setOnClickListener(l);
        }
    }
}
