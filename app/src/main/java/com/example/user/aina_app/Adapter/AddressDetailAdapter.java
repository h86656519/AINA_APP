package com.example.user.aina_app.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.aina_app.R;

import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

public class AddressDetailAdapter extends RecyclerView.Adapter<AddressDetailAdapter.ViewHolder> {
    private ArrayList<String> name_list;
    private ArrayList<String> phone_list;
    private ArrayList<String> address_list;

    public AddressDetailAdapter(Context context, ArrayList<String> name, ArrayList<String> phone, ArrayList<String> address) {
        this.name_list = name;
        this.phone_list = phone;
        this.address_list = address;

    }

    @NonNull
    @Override
    public AddressDetailAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.addresslayout, viewGroup, false);
        return new AddressDetailAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(AddressDetailAdapter.ViewHolder viewHolder, final int position) {
        String name = name_list.get(position);
        String phone = phone_list.get(position);
        String address = address_list.get(position);
        viewHolder.name.setText(name);
        viewHolder.phone.setText(phone);
        viewHolder.address.setText(address);

        viewHolder.trach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Log.i(TAG, "trach  : " + position);
                removeList(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return name_list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView name;
        private TextView phone;
        private TextView address;
        private ImageView trach;
        private View view;

        ViewHolder(final View itemView) {
            super(itemView);
            view = itemView;
            this.name = (TextView) itemView.findViewById(R.id.name_texview);
            this.phone = (TextView) itemView.findViewById(R.id.phone_textview);
            this.address = (TextView) itemView.findViewById(R.id.address_textview);
            this.trach = (ImageView) itemView.findViewById(R.id.trash_image);
        }

        public void setOnItemClick(View.OnClickListener l) {
            this.view.setOnClickListener(l);
        }
    }

    public void removeList(int position) {
        name_list.remove(position);
        phone_list.remove(position);
        address_list.remove(position);
        // Log.i(TAG, "totalprice b : " + totalprice);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, name_list.size());
        notifyItemRangeChanged(position, phone_list.size());
        notifyItemRangeChanged(position, address_list.size());
    }
}
