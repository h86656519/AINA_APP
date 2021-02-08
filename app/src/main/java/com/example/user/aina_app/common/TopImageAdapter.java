package com.example.user.aina_app.common;

import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.user.aina_app.R;

public class TopImageAdapter extends RecyclerView.Adapter<TopImageAdapter.ViewHolder>{


    public TopImageAdapter() {

    }

    @Override
    public TopImageAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_image, viewGroup, false);
        return new TopImageAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(TopImageAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
      return 2;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;

        ViewHolder(final View itemView) {
            super(itemView);
            //this.title = (TextView) itemView.findViewById(R.id.title);
        }
    }
}
