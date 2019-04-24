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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class ShoppingCartActivity extends AppCompatActivity {
    private final String TAG = "ShoppingCartActivity";
    private ArrayList<Good> goods = new ArrayList<>();
    private ShoppingCartActivity mContext;
    private ShoppingCartAdapter2 shoppingCartAdapter;
    private RecyclerView shoppingCartRecyclerView;
    private TextView totalPrice_tv, finalprice_tv, shippingfee;
    private Button continueShopping, shoppingComfirm;
    private int totalPrice, finalprice; // 總計
    private int quantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart); // 新建avtivity時 R 檔產生不好，解決: alt + enter
        mContext = this;
        totalPrice_tv = (TextView) findViewById(R.id.price1);
        finalprice_tv = (TextView) findViewById(R.id.price2);
        shippingfee = (TextView) findViewById(R.id.shippingfee);
        continueShopping = (Button) findViewById(R.id.continueShopping);
        shoppingComfirm = (Button) findViewById(R.id.shoppingComfirm);

        initView();
        initData();
        shoppingCartAdapter = new ShoppingCartAdapter2(mContext);

        shoppingCartAdapter.setGoods(goods);

        ShoppingCartAdapter2.OnItemClickListener listener = new ShoppingCartAdapter2.OnItemClickListener() {
            @Override
            public void onAddClicked(int position) {
                int quantity = goods.get(position).getGoodQuantity() + 1;
                goods.get(position).setGoodQuantity(quantity);

//                goods.get(position).goodQuantity++;
                shoppingCartAdapter.notifyItemChanged(position); //個別刷新
                Log.i(TAG, "add : " + position);
                updateTotalPrice();
            }

            @Override
            public void onMinusClicked(int position) {
                Log.i(TAG, " goods.get(position).goodQuantity : " + goods.get(position).getGoodQuantity());
                if (goods.get(position).getGoodQuantity() == 1) {
                    int q = goods.get(position).getGoodQuantity() + 1;
                    goods.get(position).setGoodQuantity(q);
                    Log.i(TAG, "數量不能小於1");
                    return;
                }

                int q = goods.get(position).getGoodQuantity() - 1;
                goods.get(position).setGoodQuantity(q);
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
                boolean state = goods.get(position).isCheckedGoodStatus();
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
                int currentQuantity = goods.get(position).getGoodQuantity();
                int newQuantity;
                if (c.toString().equals("")) {
                    Log.i(TAG, "c.toString().equals");

//                    newQuantity = goods.get(position).goodQuantity;
                    Toast.makeText(ShoppingCartActivity.this, "請填入購買數量 ", Toast.LENGTH_SHORT).show();
                    return;
                }

                newQuantity = Integer.parseInt(c.toString());
                Log.i(TAG, "newQuantity " + newQuantity);
                Log.i(TAG, "currentQuantity " + currentQuantity);
                if (newQuantity != currentQuantity) {
//                    Log.i(TAG, "newQuantity != currentQuantity ");
                    goods.get(position).setGoodQuantity(newQuantity);
                }
                updateTotalPrice();
            }
        };
        shoppingCartAdapter.notifyDataSetChanged();
        shoppingCartAdapter.setOnItemClickListener(listener);
        shoppingCartRecyclerView = (RecyclerView) findViewById(R.id.shoppingcart_recycleview);

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


    public void initData() {
        GoodFactory goodFactory = new GoodFactory();
        goods.addAll(goodFactory.createdGood(getResources()));
    }

    // 更新價格
    public void updateTotalPrice() {
        int price = 0;
        totalPrice = 0; //總價
        totalPrice_tv.setText("$" + 0);
        // Log.i(TAG, "updateTotalPrice : ");
        for (Good good : goods) {
            //   Log.i(TAG, "good.checkedGoodStatus : " + good.checkedGoodStatus);
            if (!good.isCheckedGoodStatus()) {
                continue;
            }

            price = (int) good.getGoodPrice();
            totalPrice = totalPrice + price * good.getGoodQuantity();
            // Log.i(TAG, "finalprice : " + finalprice);
            totalPrice_tv.setText("$" + totalPrice);
            //caculate
        }

        //有price 才有運費
        if (totalPrice != 0) {
            shippingfee.setText("50");
            finalprice = totalPrice + 50; //假設運費50
            finalprice_tv.setText("$" + finalprice);
            shoppingComfirm.setBackground(getResources().getDrawable(R.drawable.shoppingcart_round_enable));

        } else {
            shoppingComfirm.setBackground(getResources().getDrawable(R.drawable.shoppingcart_round_disable));
            shippingfee.setText("0");
            finalprice = 0;
            finalprice_tv.setText("$" + 0);

        }
    }

    public void onclick(View view) {
        switch (view.getId()) {
            case R.id.continueShopping:
                break;
            case R.id.shoppingComfirm:
                if (finalprice == 0) {
                    Toast.makeText(ShoppingCartActivity.this, "請至少勾選一項商品 ", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent();
                    intent.setClass(ShoppingCartActivity.this, OrderDetailActivity2.class);
                    ArrayList<Good> newGoods = getCheckedGoods();
                    intent.putExtra("goods", (Serializable) newGoods);
                    startActivity(intent);
                }
                //連到結帳頁面
                break;
        }
    }

    //只需要 goods 裡面為true 的商品
    public ArrayList<Good> getCheckedGoods() {
        ArrayList<Good> newGoods = new ArrayList<>();
        for (int i = 0; i < goods.size(); i++) {
            if (goods.get(i).isCheckedGoodStatus()) {
                newGoods.add(goods.get(i));
            }
        }
        return newGoods;
    }
}
