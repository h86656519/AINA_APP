package com.example.user.aina_app.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.user.aina_app.Adapter.ProductExpandableListAdapter;
import com.example.user.aina_app.Adapter.TabAdaper;
import com.example.user.aina_app.R;
import com.example.user.aina_app.layout.InvoiceViewPager;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderDetailActivity extends AppCompatActivity {
    private final String TAG = "OrderDetailActivity";
    int count;
    String productName, productPrice;
    ProductExpandableListAdapter listAdapter;
    ExpandableListView expListView;
    List<String> listDataHeader;
    HashMap<String, List<String>> listDataChild;
    ArrayList<String> productNamelist;
    ArrayList<String> productPricelist;
    ArrayList<Integer> productImage;
    private ArrayList<Integer> productQuantity;
    Context context;
    TabAdaper tabAdaper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        context = this;


        //接資料
        productImage = (ArrayList<Integer>) getIntent().getIntegerArrayListExtra("pickUpImage");
        productNamelist = (ArrayList<String>) getIntent().getStringArrayListExtra("pickUpProductname");
        productPricelist = (ArrayList<String>) getIntent().getStringArrayListExtra("pickUpproductPrice");
        productQuantity = (ArrayList<Integer>) getIntent().getIntegerArrayListExtra("pickUpQuantity");
//        Log.i(TAG, "OrderDetailActivity productImage : " + productImage);
//        Log.i(TAG, "OrderDetailActivity productNamelist : " + productNamelist);
//        Log.i(TAG, "OrderDetailActivity productPricelist : " + productPricelist);
//        Log.i(TAG, "OrderDetailActivity productQuantity : " + productQuantity);
        // get the listview
        expListView = (ExpandableListView) findViewById(R.id.lvExp);
        initView();
        // preparing list data
        prepareListData();

        listAdapter = new ProductExpandableListAdapter(context, listDataHeader, productImage, productNamelist, productPricelist, productQuantity);

        // setting list spCityselectListeneradapter
        expListView.setAdapter(listAdapter);
        // setListViewHeight(expListView);
        // 解決 ExpandableListView 在scollview 顯示問題
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id) {
                setListViewHeight(parent, groupPosition);
                return false;
            }
        });

    }

    public void initView() {
        //設定左上角的圖示
        Toolbar toolbar = (Toolbar) findViewById(R.id.orderDetail_toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        toolbar.setNavigationIcon(R.drawable.icon_arrow_left_white); //setNavigationIcon需要放在 setSupportActionBar之后才会生效

        textView.setText(R.string.orderDetail);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null); //隱藏 app name
        //要在setSupportActionBar 生效後在做
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setInvoice();
        setOrderDetail();
    }

    /*
     * Preparing the list data
     */
    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        listDataChild = new HashMap<String, List<String>>();

        // Adding child data
        listDataHeader.add("商品詳情");

        // Adding child data
        listDataChild.put(listDataHeader.get(0), productNamelist); // Header, Child data

    }

    // 暫時保留
    private void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
        listView.requestLayout();
    }

    private void setListViewHeight(ExpandableListView listView, int group) {
        ProductExpandableListAdapter listAdapter = (ProductExpandableListAdapter) listView.getExpandableListAdapter();
        int totalHeight = 0;
        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(),
                View.MeasureSpec.EXACTLY);
        for (int i = 0; i < listAdapter.getGroupCount(); i++) {
            View groupItem = listAdapter.getGroupView(i, false, null, listView);
            groupItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

            totalHeight += groupItem.getMeasuredHeight();

            if (((listView.isGroupExpanded(i)) && (i != group))
                    || ((!listView.isGroupExpanded(i)) && (i == group))) {
                for (int j = 0; j < listAdapter.getChildrenCount(i); j++) {
                    View listItem = listAdapter.getChildView(i, j, false, null,
                            listView);
                    listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);

                    totalHeight += listItem.getMeasuredHeight();

                }
            }
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        int height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getGroupCount() - 1));
        if (height < 10)
            height = 200;
        params.height = height;
        listView.setLayoutParams(params);
        listView.requestLayout();

    }

    public void setInvoice() {
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        TabLayout.Tab tab1 = tabLayout.newTab().setText(getResources().getString(R.string.elecInvoice));
        tabLayout.addTab(tab1);
        TabLayout.Tab tab2 = tabLayout.newTab().setText(getResources().getString(R.string.donationInvoice));
        tabLayout.addTab(tab2);
        TabLayout.Tab tab3 = tabLayout.newTab().setText(getResources().getString(R.string.tripUniformInvoice));
        tabLayout.addTab(tab3);
        tabAdaper = new TabAdaper(getSupportFragmentManager());
        InvoiceViewPager viewPager = (InvoiceViewPager) findViewById(R.id.invoice_viewpager);
        viewPager.setAdapter(tabAdaper);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setOrderDetail() {
        int price = 0;
        for (int i = 0; i < productPricelist.size(); i++) {
            int quantity = 0;
            int varprice = 0;
            TextView paymentAmountNumber = (TextView) findViewById(R.id.paymentAmount_number);
            TextView delieverAmounNnumber = (TextView) findViewById(R.id.delieverAmount_number);
            TextView goodAmountNumber = (TextView) findViewById(R.id.goodAmount_number);
            varprice = Integer.valueOf(productPricelist.get(i)); //每一筆
            quantity = productQuantity.get(i);
            price = price + varprice * quantity;
            goodAmountNumber.setText("$" + String.valueOf(price));
            delieverAmounNnumber.setText("$50");
            paymentAmountNumber.setText("$" + String.valueOf(price + 50));
        }
    }

    public void onclick(View view) {
        switch (view.getId()){
            case R.id.discount_cardview:
                startActivity(new Intent(this,CouponSelectionActivity.class));
                break;
            case R.id.addcreditcard_button:
                startActivity(new Intent(this,AddSelectCreditcardActivity.class));
                break;
            case R.id.deliverByHome:
                startActivity(new Intent(this,DeliverByHomeActivity.class));
                break;
            case R.id.deliverByMarket:
                startActivity(new Intent(this,DeliverByMarketActivity.class));
                break;

        }
    }
}
