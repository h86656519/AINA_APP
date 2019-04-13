package com.example.user.aina_app.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.aina_app.R;
import com.example.user.aina_app.activity.Good;
import com.example.user.aina_app.activity.ShoppingCartActivity;

import java.util.ArrayList;

public class ShoppingCartAdapter2 extends RecyclerView.Adapter<ShoppingCartAdapter2.ViewHolder>{
    View view;
    EditText etQuantity;

    public void setGoods(ArrayList<Good> goods) {
        this.goods = goods;
    }

    private ArrayList<Good> goods = new ArrayList<>();
    private Context context;

    public ShoppingCartAdapter2(Context mContext) {
        this.context = mContext;
    }

    @NonNull
    @Override
    public ShoppingCartAdapter2.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shoppingcart_card, viewGroup, false);
        etQuantity = (EditText) view.findViewById(R.id.etQuantity);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartAdapter2.ViewHolder holder, final int i) {
        Good good = goods.get(i);
        holder.productcheck.setChecked(true);

        // Integer a = quantity_list[position];
        //   holder.etQuantity.clearFocus();
        holder.button_add.setTag("+");
        holder.button_reduce.setTag("-");
        holder.producname.setText(good.getGoodName());
        holder.etQuantity.setText(good.getGoodQuantity() + "");
        holder.productPrice.setText(good.getGoodPrice());
        holder.etQuantity.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);

        if(onItemClickListener != null) {
            holder.button_add.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onAddClicked(i);
                }
            });

            holder.button_reduce.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onMinusClicked(i);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return goods.size();
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

    public interface OnItemClickListener{
        public void onAddClicked(int position) ;
        public void onMinusClicked(int position) ;
        public void onChecked(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public OnItemClickListener onItemClickListener;
}


