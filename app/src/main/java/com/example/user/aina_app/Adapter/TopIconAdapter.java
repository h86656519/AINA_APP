package com.example.user.aina_app.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.aina_app.R;
import com.example.user.aina_app.activity.MenstruationActivity;

import java.util.ArrayList;

public class TopIconAdapter extends RecyclerView.Adapter<TopIconAdapter.ViewHolder> {
    private ArrayList<String> toptitles1;
    private ArrayList<Integer> topIconImage;
    private final String TAG = "TopIconAdapter";

    public TopIconAdapter(ArrayList<String> toptitles1, ArrayList<Integer> topIconImage) {
        this.toptitles1 = toptitles1;
        this.topIconImage = topIconImage;
    }

    @Override
    public TopIconAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_icon, viewGroup, false);
        return new TopIconAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final TopIconAdapter.ViewHolder holder, final int position) {
        String name = toptitles1.get(position);
        holder.topIconName.setText(name);
        int image = topIconImage.get(position);
        holder.imageButton.setImageResource(image);
        holder.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "position : " + position, Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 2: //月月好朋友
                        Intent intent = new Intent();
                        intent.setClass(v.getContext(), MenstruationActivity.class);
                        v.getContext().startActivity(intent);
                        break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return toptitles1.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView topIconName;
        private ImageButton imageButton;
        private View view;

        ViewHolder(final View itemView) {
            super(itemView);
            view = itemView;
            this.topIconName = (TextView) itemView.findViewById(R.id.topIcon_name);
            this.imageButton = (ImageButton) itemView.findViewById(R.id.topImageButton);
        }

        public void setOnItemClick(View.OnClickListener l) {
            this.view.setOnClickListener(l);
        }
    }
}
