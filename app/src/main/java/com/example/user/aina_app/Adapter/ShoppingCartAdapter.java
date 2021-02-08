package com.example.user.aina_app.Adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.aina_app.R;
import com.example.user.aina_app.activity.ShoppingCartActivity;

import java.util.ArrayList;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ViewHolder> {
    private final String TAG = "ShoppingCartAdapter";
    private ArrayList<String> selectProductName;
    private ArrayList<Integer> selectimage;
    private ArrayList<String> productPrice;
    private ArrayList<Integer> quantity_list = new ArrayList<Integer>();

    View view;
    ShoppingCartActivity mContext;
    //建立checkbox狀態，回傳用
    boolean[] checkedStatus;
    // Integer[] quantity_list;

    int price;
    int quantity;
    EditText etQuantity;
    String numString;

    public ShoppingCartAdapter(ShoppingCartActivity context, ArrayList<String> selectProductName, ArrayList<Integer> selectimage, ArrayList<String> productPrice) {
        this.selectProductName = selectProductName;
        this.selectimage = selectimage;
        this.productPrice = productPrice;
        this.mContext = context;

        checkedStatus = new boolean[productPrice.size()];
        // quantity_list = new Integer[productPrice.size()];

        for (int i = 0; i < productPrice.size(); i++) {
            checkedStatus[i] = true;
            //     quantity_list[i] = 1;
            quantity_list.add(i, 1);
        }

    }

    @Override
    public ShoppingCartAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.shoppingcart_card, viewGroup, false);
        etQuantity = (EditText) view.findViewById(R.id.etQuantity);
//        button_add = (Button) view.findViewById(R.id.num_add);
//        button_reduce = (Button) view.findViewById(R.id.num_reduce);

        etQuantity.setText(String.valueOf(quantity));
       // etQuantity.clearFocus();
        //etQuantity.setFocusable(false);

        return new ShoppingCartAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ShoppingCartAdapter.ViewHolder holder, final int position) {
        holder.productcheck.setChecked(true);
        String producname = selectProductName.get(position);
        price = Integer.valueOf(productPrice.get(position));
        Integer image = selectimage.get(position);
        // Integer a = quantity_list[position];
        Integer a = quantity_list.get(position);
        holder.etQuantity.setText(String.valueOf(a)); //設定顯示初值
     //   holder.etQuantity.clearFocus();
        holder.button_add.setTag("+");
        holder.button_reduce.setTag("-");
        holder.etQuantity.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        holder.producname.setText(producname);
        holder.productPrice.setText("$" + price);
        holder.image.setImageResource(image);
        holder.productcheck.setOnCheckedChangeListener(new CheckBox.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    checkedStatus[position] = true;
                    //  Toast.makeText(buttonView.getContext(), "quantity_list : " + quantity_list.get(position) + "+" + position + "" + quantity, Toast.LENGTH_SHORT).show();
                    // Toast.makeText(buttonView.getContext(), "quantity_list : " + quantity_list[position] + "+" + position + "+" + quantity, Toast.LENGTH_SHORT).show();
                } else {
                    checkedStatus[position] = false;
                    // Toast.makeText(buttonView.getContext(), "quantity_list : " + quantity_list.get(position) + "+" + position + "" + quantity, Toast.LENGTH_SHORT).show();
                    //Toast.makeText(buttonView.getContext(), "quantity_list : " + quantity_list[position] + "+" + position + "+" + quantity, Toast.LENGTH_SHORT).show();
                }
                Log.i(TAG,"1" );
                mContext.updateTotalPrice(); //調用activity 裡的方法用

            }
        });
        holder.productcheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //刪除卡片
                // Toast.makeText(v.getContext(), "getId : " + v.getId(), Toast.LENGTH_SHORT).show();
//                removeProduct(position);
                if (holder.productcheck.isChecked()) {
                    holder.productcheck.setChecked(true);
                    //    Toast.makeText(v.getContext(), "price 1 : " + price, Toast.LENGTH_SHORT).show();
                } else {
                    holder.productcheck.setChecked(false);
                    //   Toast.makeText(v.getContext(), "price 2 : " + price, Toast.LENGTH_SHORT).show();
                    checkedStatus[position] = false;
                }
            }

        });
//        holder.etQuantity.setTag(position);
//        holder.etQuantity.setText(quantity_list.get(position));
        holder.etQuantity.setInputType(android.text.InputType.TYPE_CLASS_NUMBER);
        holder.etQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                numString = s.toString();
                int numInt = 0;
                if (numString == null || numString.equals("")) {
                    quantity = 1;
                } else {
                    numInt = Integer.parseInt(numString);
//                    Log.i(TAG, "numInt : " + numInt);
                    if (numInt < 1) {
//                        Log.i(TAG, "checkedStatus position" + position);
                        Toast.makeText(mContext.getApplicationContext(), "onTextChanged 數量至少要1", Toast.LENGTH_SHORT).show();
                        //   holder.etQuantity.setText(String.valueOf(quantity_list[position]));
                        holder.etQuantity.setText(String.valueOf(quantity_list.get(position)));


                        mContext.updateTotalPrice();

                    } else {
                        //設置EditText光標位置 為文本末端
                        etQuantity.setSelection(etQuantity.getText().toString().length());
                        quantity = numInt;
                        //quantity_list[position] = quantity;
                        quantity_list.set(position, quantity);

                        mContext.updateTotalPrice();
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        // holder.button_reduce.setOnClickListener(new OnButtonClickListener()); 沒有position，就不分到外面做了
        holder.button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numString == null || numString.equals("")) {
                    quantity = 1;
                    //     holder.etQuantity.setText(String.valueOf(quantity_list[position])); //不給空，數量至少要1
                    holder.etQuantity.setText(String.valueOf(quantity_list.get(position)));
                } else {
                    if (v.getTag().equals("+")) {
                        //   quantity = quantity_list[position];
                        quantity = quantity_list.get(position);
                        if (++quantity < 1) //先加，再判斷
                        {
//                            Log.i(TAG, " 加號 quantity" + quantity);
                            quantity++;
                            Toast.makeText(mContext.getApplicationContext(), "button_add 請輸入一個大於0的數字", Toast.LENGTH_SHORT).show();

                            //  holder.etQuantity.setText(String.valueOf(quantity_list[position]));
                            holder.etQuantity.setText(String.valueOf(quantity_list.get(position)));
                        } else {
//                            quantity_list.set(position, quantity);
//                            holder.etQuantity.setText(String.valueOf(quantity_list.get(position)));
                            // quantity_list[position] = quantity;
                            quantity_list.set(position, quantity);
                            //   holder.etQuantity.setText(String.valueOf(quantity_list[position]));
                            holder.etQuantity.setText(String.valueOf(quantity_list.get(position)));

                            mContext.updateTotalPrice();
                        }
                    }
                }
            }
        });
        holder.button_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numString == null || numString.equals("")) {
                    quantity = 1;
                    //holder.etQuantity.setText(String.valueOf(quantity_list[position])); //不給空，數量至少要1
                    holder.etQuantity.setText(String.valueOf(quantity_list.get(position))); //不給空，數量至少要1
                } else {
                    if (v.getTag().equals("-")) {
                        //quantity = quantity_list[position];
                        quantity = quantity_list.get(position);
                        if (--quantity < 1) //先加，再判斷
                        {
                            quantity--;
                            Toast.makeText(v.getContext(), "數量至少要1",
                                    Toast.LENGTH_SHORT).show();
                            //       holder.etQuantity.setText(String.valueOf(quantity_list[position]));
                            holder.etQuantity.setText(String.valueOf(quantity_list.get(position)));

                        } else {
//                            quantity_list.set(position, quantity);
//                            holder.etQuantity.setText(String.valueOf(quantity_list.get(position))); // 更新數量，數量才會+-動
                            // quantity_list[position] = quantity;
                            quantity_list.set(position, quantity);
                            // holder.etQuantity.setText(String.valueOf(quantity_list[position]));
                            holder.etQuantity.setText(String.valueOf(quantity_list.get(position)));

                            mContext.updateTotalPrice();
                        }
                    }
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return productPrice.size();
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

    public void removeProduct(int position) {
        selectProductName.remove(position);
        // Log.i(TAG, "totalprice b : " + totalprice);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, selectProductName.size());
    }

    public String getPrice(int position) {
        return productPrice.get(position);
    }

    public Integer getImage(int position) {
        return selectimage.get(position);
    }

    public String getProductname(int position) {
        return selectProductName.get(position);
    }

    public boolean getCheckedStatus(int position) {
        return checkedStatus[position];
    }

    public int getQuality(int position) {
        return quantity_list.get(position);
        // return quantity_list[position];
    }

//    class OnButtonClickListener implements View.OnClickListener {
//        @Override
//        public void onClick(View v) {
//            String numString = etQuantity.getText().toString();
//            if (numString == null || numString.equals("")) {
//                //  Log.i(TAG,"9512364");
//                quantity = 0;
//                etQuantity.setText("0");
//            } else {
//                Log.i(TAG, "1 - ");
//                if (v.getTag().equals("-")) {
//                    if (++quantity < 1) //先加，再判斷
//                    {
//                        quantity--;
////                        Toast.makeText(StartActivity.this, "請輸入一個大於0的數字",
////                                Toast.LENGTH_SHORT).show();
//                    } else {
//                        quantity_list.set(p);
//                    }
//                } else if (v.getTag().equals("+")) {
//                    Log.i(TAG, "2 +  ");
//                    if (--quantity < 1) //先減，再判斷
//                    {
//                        quantity++;
////                        Toast.makeText(StartActivity.this, "請輸入一個大於0的數字",
////                                Toast.LENGTH_SHORT).show();
//                    } else {
////                       etQuantity.setText(quantity_list.get(0));
//
//                    }
//                }
//            }
//        }
//    }
}
