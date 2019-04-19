package com.example.user.aina_app.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.aina_app.Adapter.ShoppingCartAdapter2;
import com.example.user.aina_app.R;

import java.util.ArrayList;

public class ShoppingCartActivity extends AppCompatActivity {
    private final String TAG = "ShoppingCartActivity";
    ArrayList<Integer> selectImage = new ArrayList<>();
    ArrayList<String> selectProductname = new ArrayList<>();
    ArrayList<String> productPrice = new ArrayList<>();
    ArrayList<Integer> pickUpImage = new ArrayList<>();
    ArrayList<String> pickUpProductname = new ArrayList<>();
    ArrayList<String> pickUpproductPrice = new ArrayList<>();
    ArrayList<Integer> pickUpQuantity = new ArrayList<>();
    private ArrayList<Good> goods = new ArrayList<>();
    ShoppingCartActivity mContext;
    ShoppingCartAdapter2 shoppingCartAdapter;
    RecyclerView shoppingCartRecyclerView;
    TextView price1, price2, shippingfee;
    Button continueShopping, shoppingComfirm;
    int finalprice; // 總計
    int quantity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart); // 新建avtivity時 R 檔產生不好，解決: alt + enter
        mContext = this;
        price1 = (TextView) findViewById(R.id.price1);
        price2 = (TextView) findViewById(R.id.price2);
        shippingfee = (TextView) findViewById(R.id.shippingfee);
        continueShopping = (Button) findViewById(R.id.continueShopping);
        shoppingComfirm = (Button) findViewById(R.id.shoppingComfirm);

        initView();

        shoppingCartAdapter = new ShoppingCartAdapter2(mContext);
        for (int i = 0; i < 6; i++) {
            Good good = new Good();
            good.setGoodImage(R.drawable.product);
            good.setGoodName(getResources().getString(R.string.specialproductname_det));
            good.setGoodPrice(String.valueOf(1000 + i * 100));
            good.setGoodQuantity(1);
            good.setCheckedGoodStatus(true);
            goods.add(good);
        }
        shoppingCartAdapter.setGoods(goods);

        ShoppingCartAdapter2.OnItemClickListener listener = new ShoppingCartAdapter2.OnItemClickListener() {
            @Override
            public void onAddClicked(int position) {
//                int quantity = goods.get(position).goodQuantity + 1;
//                goods.get(position).goodQuantity = quantity;

                goods.get(position).goodQuantity++;
                shoppingCartAdapter.notifyItemChanged(position); //個別刷新
                Log.i(TAG, "add : " + position);
                updateTotalPrice();
            }

            @Override
            public void onMinusClicked(int position) {
                Log.i(TAG, " goods.get(position).goodQuantity : " + goods.get(position).goodQuantity);
                if (goods.get(position).goodQuantity == 1) {
                    ++goods.get(position).goodQuantity;
                    Log.i(TAG, "數量不能小於1");
                    return;
                }

                goods.get(position).goodQuantity--;

                shoppingCartAdapter.notifyItemChanged(position);
                updateTotalPrice();
                Log.i(TAG, "min : " + position);
            }

//            private boolean isMinQuantity(Good good) {
//                return good.goodQuantity == 1;
//            }

            @Override
            public void onGoodStatusChecked(int position) {
//                Log.i(TAG, "onCheckedGoodStatus : ");
//                Log.i(TAG, "position : " + position);
                boolean state = goods.get(position).checkedGoodStatus;
                if (state == true) {
                    goods.get(position).setCheckedGoodStatus(false);
                } else {
                    goods.get(position).setCheckedGoodStatus(true);
                }
                updateTotalPrice();
            }

            @Override
            public void onQuantityChanged(CharSequence c, int position) {
                Log.i(TAG, "onQuantityChangeddddd ");
                int currentQuantity = goods.get(position).goodQuantity;
                int newQuantity;
                if (c.toString().equals("")) {
                    Log.i(TAG, "onQuantityChanged : 1  ");
//                    newQuantity = goods.get(position).goodQuantity;
                    Toast.makeText(ShoppingCartActivity.this, "請填入購買數量 ", Toast.LENGTH_SHORT).show();
                    return;
                }

                newQuantity = Integer.parseInt(c.toString());
                Log.i(TAG, "newQuantity " + newQuantity);
                Log.i(TAG, "currentQuantity " + currentQuantity);
                if (newQuantity != currentQuantity) {
                    Log.i(TAG, "newQuantity != currentQuantity ");
                    goods.get(position).setGoodQuantity(newQuantity);
                }
                updateTotalPrice();
            }
        };
        shoppingCartAdapter.notifyDataSetChanged();
        shoppingCartAdapter.setOnItemClickListener(listener);
        shoppingCartRecyclerView = (RecyclerView) findViewById(R.id.shoppingcart_recycleview);
//        MultiSnapRecyclerView shoppingCartRecyclerView = (MultiSnapRecyclerView) findViewById(R.id.shoppingcart_recycleview);

        LinearLayoutManager shoppingCartManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        shoppingCartRecyclerView.setLayoutManager(shoppingCartManager);
        shoppingCartRecyclerView.setAdapter(shoppingCartAdapter);


        updateTotalPrice();
    }

    public void initView() {
        //設定左上角的圖示
        Toolbar toolbar = (Toolbar) findViewById(R.id.shoppingcart_toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left_white); //setNavigationIcon需要放在 setSupportActionBar之后才会生效

        textView.setText(R.string.shoppingCart);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null); //隱藏 app name，要放在 setSupportActionBar 後面
        //要在setSupportActionBar 生效後在做
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    // 更新總價
    public void updateTotalPrice() {
        int price = 0;
        finalprice = 0;
        price1.setText("$" + 0);
        // Log.i(TAG, "updateTotalPrice : ");
        for (Good good : goods) {
            //   Log.i(TAG, "good.checkedGoodStatus : " + good.checkedGoodStatus);
            if (!good.checkedGoodStatus) {
                continue;
            }
            price = Integer.valueOf(good.getGoodPrice());
            finalprice = finalprice + price * good.getGoodQuantity();
            // Log.i(TAG, "finalprice : " + finalprice);
            price1.setText("$" + finalprice);
            //caculate
        }

//        Log.i(TAG, "getItemCount : " + shoppingCartRecyclerView.getAdapter().getItemCount());
        for (int i = 0; i < shoppingCartRecyclerView.getAdapter().getItemCount(); i++) {
//            Log.i(TAG, "pickUpImage.size() : " + pickUpImage.size());
            int varprice = 0;
//            Log.i(TAG, "getCheckedStatus : " + shoppingCartAdapter.getCheckedStatus(i));
//            if (shoppingCartAdapter.getCheckedStatus(i) == true) {
////                Log.i(TAG, "getPrice : " + i + " " + shoppingCartAdapter.getPrice(i));
//                varprice = Integer.valueOf(shoppingCartAdapter.getPrice(i)); //每一筆
//                int quantity = shoppingCartAdapter.getQuality(i);
//                price = price + varprice * quantity;
//                price1.setText("$" + price);
//
//                pickUpImage.add(shoppingCartAdapter.getImage(i));
//                pickUpProductname.add(shoppingCartAdapter.getProductname(i));
//                pickUpproductPrice.add(shoppingCartAdapter.getPrice(i));
//                pickUpQuantity.add(shoppingCartAdapter.getQuality(i));
        }
    }
    //有price 才有運費
//        if (price != 0) {
//            shippingfee.setText("50");
//            finalprice = price + 50; //假設運費50
//            price2.setText("$" + finalprice);
//            shoppingComfirm.setBackground(getResources().getDrawable(R.drawable.shoppingcart_round_enable));
//
//        } else {
//            shoppingComfirm.setBackground(getResources().getDrawable(R.drawable.shoppingcart_round_disable));
//            shippingfee.setText("0");
//            price2.setText("$" + 0);
//
//        }


    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.continueShopping:
                break;
            case R.id.shoppingComfirm:
                if (finalprice == 0) {
                    Toast.makeText(ShoppingCartActivity.this, "請至少勾選一項商品 ", Toast.LENGTH_SHORT).show();
                } else {
                    // Log.i(TAG, "pickUpImageeeeeeeeeeee" + pickUpImage.size());
                    Intent intent = new Intent();
                    intent.setClass(ShoppingCartActivity.this, OrderDetailActivity.class);
                    intent.putIntegerArrayListExtra("pickUpImage", pickUpImage);
                    intent.putStringArrayListExtra("pickUpProductname", pickUpProductname);
                    intent.putStringArrayListExtra("pickUpproductPrice", pickUpproductPrice);
                    intent.putIntegerArrayListExtra("pickUpQuantity", pickUpQuantity);
                    startActivity(intent);
                }
                //連到結帳頁面
                break;
        }
    }

    public void removepickup() {
        pickUpImage.clear();
        pickUpProductname.clear();
        pickUpproductPrice.clear();
        pickUpQuantity.clear();
    }
}
