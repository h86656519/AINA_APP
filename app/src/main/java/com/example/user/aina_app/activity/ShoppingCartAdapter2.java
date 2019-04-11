package com.example.user.aina_app.activity;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.aina_app.Adapter.ShoppingCartAdapter;
import com.example.user.aina_app.R;

public class ShoppingCartAdapter2 extends RecyclerView.Adapter<ShoppingCartAdapter2.ViewHolder>{
    View view;
    EditText etQuantity;
    @NonNull
    @Override
    public ShoppingCartAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shoppingcart_card, viewGroup, false);
        etQuantity = (EditText) view.findViewById(R.id.etQuantity);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartAdapter2.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView producname, productPrice;
        private ImageView image;
        CheckBox productcheck;
        EditText etQuantity;
        Button button_add, button_reduce;
        private View view;

        ViewHolder(final View itemView) {
            super(itemView);
            view = itemView;
            this.producname = (TextView) itemView.findViewById(R.id.producname_textview);
            this.productPrice = (TextView) itemView.findViewById(R.id.productPrice);
            this.image = (ImageView) itemView.findViewById(R.id.shoppingcart_image);
            this.productcheck = (CheckBox) itemView.findViewById(R.id.productcheck);
            this.etQuantity = (EditText) itemView.findViewById(R.id.etQuantity);
            this.button_add = (Button) itemView.findViewById(R.id.num_add);
            this.button_reduce = (Button) itemView.findViewById(R.id.num_reduce);
        }
    }
}
