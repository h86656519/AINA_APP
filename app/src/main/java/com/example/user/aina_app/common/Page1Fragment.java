package com.example.user.aina_app.common;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.aina_app.Adapter.BabyAdapter;
import com.example.user.aina_app.Adapter.HealthFoodAdapter;
import com.example.user.aina_app.Adapter.HealthRecommendAdapter;
import com.example.user.aina_app.Adapter.ManAdapter;
import com.example.user.aina_app.Adapter.OldManHealthAdapter;
import com.example.user.aina_app.Adapter.SpecialDiscountAdapter;
import com.example.user.aina_app.Adapter.TopIconAdapter;
import com.example.user.aina_app.Adapter.WomanAdapter;
import com.example.user.aina_app.R;
import com.example.user.aina_app.activity.ShoppingCartActivity;
import com.superluo.textbannerlibrary.ITextBannerItemClickListener;
import com.superluo.textbannerlibrary.TextBannerView;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class Page1Fragment extends Fragment implements OnBannerListener, View.OnClickListener {
    private String TAG = "Page1Fragment";
    View view;
    MovableFloatingActionButton customerFab;

    ArrayList<String> toptitles = new ArrayList<>();
    ArrayList<Integer> topIconImage = new ArrayList<>();

    List<Integer> image1 = new ArrayList<>();
    List<Integer> image2 = new ArrayList<>();
    List<String> bannerlist = new ArrayList<>();
    ArrayList<String> specialproductname = new ArrayList<>();
    ArrayList<Integer> healthRecommendImage = new ArrayList<>();
    private final static float CLICK_DRAG_TOLERANCE = 10; // Often, there will be a slight, unintentional, drag when the user taps the FAB, so we need to account for this.

    private float downRawX, downRawY;
    private float dX, dY;

    public Page1Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        image1.add(R.drawable.slidebar_pic);
        image1.add(R.drawable.pic_error);
        image1.add(R.drawable.no_banner);

        image2.add(R.drawable.slidebar_pic);
        image2.add(R.drawable.pic_error);
        image2.add(R.drawable.no_banner);

        bannerlist.add("國人一天吃多少蔬菜");
        bannerlist.add("國人一天吃多少肉");
        bannerlist.add("國人一天吃多少維他命");

        specialproductname.add(getResources().getString(R.string.specialproductname));
        specialproductname.add(getResources().getString(R.string.specialproductname));
        specialproductname.add(getResources().getString(R.string.specialproductname));

        healthRecommendImage.add(R.drawable.health_ad);
        healthRecommendImage.add(R.drawable.health_ad);

        toptitles.add(getResources().getString(R.string.top_icon1));
        toptitles.add(getResources().getString(R.string.top_icon2));
        toptitles.add(getResources().getString(R.string.top_icon3));
        toptitles.add(getResources().getString(R.string.top_icon4));
        toptitles.add(getResources().getString(R.string.top_icon5));
        toptitles.add(getResources().getString(R.string.top_icon6));
        toptitles.add(getResources().getString(R.string.top_icon7));
        toptitles.add(getResources().getString(R.string.top_icon8));

        topIconImage.add(R.drawable.icon_class);
        topIconImage.add(R.drawable.icon_product_id);
        topIconImage.add(R.drawable.icon_act_id);
        topIconImage.add(R.drawable.icon_everyday);
        topIconImage.add(R.drawable.icon_health_news);
        topIconImage.add(R.drawable.icon_health_teacher);
        topIconImage.add(R.drawable.icon_health_id);
        topIconImage.add(R.drawable.icon_transport);
        topIconImage.add(R.drawable.icon_id);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_page1, container, false);
        initView();

//      廣告1
        Banner adbanner1 = (Banner) view.findViewById(R.id.ad_banner1);
        adbanner1.setBannerStyle(BannerConfig.CIRCLE_INDICATOR).setImages(image1).
                setImageLoader(new GlideImageLoader()).
                setOnBannerListener(this).
                setDelayTime(3000).
                start();

        TopIconAdapter topIconAdapter = new TopIconAdapter(toptitles, topIconImage);
        RecyclerView topiconRecyclerView = (RecyclerView) view.findViewById(R.id.topIcon_recyclerview);
        GridLayoutManager topIconManager = new GridLayoutManager(getActivity(), 2, GridLayoutManager.HORIZONTAL, false);
        topiconRecyclerView.setLayoutManager(topIconManager);
        topiconRecyclerView.setAdapter(topIconAdapter);


//      廣告2
        Banner adbanner2 = (Banner) view.findViewById(R.id.ad_banner2);
        adbanner2.setBannerStyle(BannerConfig.CIRCLE_INDICATOR).setImages(image2).
                setImageLoader(new GlideImageLoader()).
                setOnBannerListener(this).
                setDelayTime(3000).
                start();

//      健康頭條 跑馬燈
        TextBannerView tvBanner = (TextBannerView) view.findViewById(R.id.tv_banner);
        tvBanner.setDatas(bannerlist);
        tvBanner.setItemOnClickListener(new ITextBannerItemClickListener() {
            @Override
            public void onItemClick(String data, int position) {
          //      Log.i("点击了：", String.valueOf(position) + ">>" + data);
            }
        });

//      超值秒殺
        SpecialDiscountAdapter specialDiscountAdapter = new SpecialDiscountAdapter(specialproductname);
        MultiSnapRecyclerView specialDiscountRecyclerView = (MultiSnapRecyclerView) view.findViewById(R.id.specialDiscount_recyclerview);
        LinearLayoutManager specialDiscountManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        specialDiscountRecyclerView.setLayoutManager(specialDiscountManager);
        specialDiscountRecyclerView.setAdapter(specialDiscountAdapter);

//      健康推薦
        HealthRecommendAdapter healthRecommendAdapter = new HealthRecommendAdapter(healthRecommendImage);
        MultiSnapRecyclerView healthRecommendRecyclerView = (MultiSnapRecyclerView) view.findViewById(R.id.healthRecommend_recyclerview);
        LinearLayoutManager healthRecommenManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        healthRecommendRecyclerView.setLayoutManager(healthRecommenManager);
        healthRecommendRecyclerView.setAdapter(healthRecommendAdapter);

//      銀髮族保健
        OldManHealthAdapter oldManHealthAdapter = new OldManHealthAdapter(specialproductname); //暫時先用specialproductname
        MultiSnapRecyclerView OldManHealthRecyclerView = (MultiSnapRecyclerView) view.findViewById(R.id.oldManHealth_recyclerview);
        LinearLayoutManager OldManHealthManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        OldManHealthRecyclerView.setLayoutManager(OldManHealthManager);
        OldManHealthRecyclerView.setAdapter(oldManHealthAdapter);

//      女性護理
        WomanAdapter womanAdapter = new WomanAdapter(specialproductname); //暫時先用specialproductname
        MultiSnapRecyclerView WomanRecyclerView = (MultiSnapRecyclerView) view.findViewById(R.id.woman_recyclerview);
        LinearLayoutManager WomanManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        WomanRecyclerView.setLayoutManager(WomanManager);
        WomanRecyclerView.setAdapter(womanAdapter);
        WomanRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });

//      嬰幼兒保健
        BabyAdapter babyAdapter = new BabyAdapter(specialproductname); //暫時先用specialproductname
        MultiSnapRecyclerView babyRecyclerView = (MultiSnapRecyclerView) view.findViewById(R.id.baby_recyclerview);
        LinearLayoutManager babyManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        babyRecyclerView.setLayoutManager(babyManager);
        babyRecyclerView.setAdapter(babyAdapter);

//      男性中心
        ManAdapter manAdapter = new ManAdapter(specialproductname); //暫時先用specialproductname
        MultiSnapRecyclerView manRecyclerView = (MultiSnapRecyclerView) view.findViewById(R.id.man_recyclerview);
        LinearLayoutManager manManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        manRecyclerView.setLayoutManager(manManager);
        manRecyclerView.setAdapter(manAdapter);

//      一般保健食品
        HealthFoodAdapter healthFoodAdapter = new HealthFoodAdapter(specialproductname); //暫時先用specialproductname
        MultiSnapRecyclerView healthFoodRecyclerView = (MultiSnapRecyclerView) view.findViewById(R.id.healthfood_recyclerview);
        LinearLayoutManager healthFoodManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        healthFoodRecyclerView.setLayoutManager(healthFoodManager);
        healthFoodRecyclerView.setAdapter(healthFoodAdapter);

        customerFab = (MovableFloatingActionButton) view.findViewById(R.id.customer_fab);
        customerFab.setOnTouchListener(new MovableFloatingActionButton(getActivity()));
        customerFab.setOnClickListener(this);

        return view;
    }

    // 點擊輪播圖片
    @Override
    public void OnBannerClick(int position) {
//        Log.i(TAG, "position :" + position);
        Toast.makeText(getActivity().getApplicationContext(), "點擊了", Toast.LENGTH_SHORT).show();
    }

    public static Page1Fragment newInstance() {
        Page1Fragment f = new Page1Fragment();
        Bundle args = new Bundle();
        args.putInt("index", 0);
        f.setArguments(args);
        return f;
    }

    public void initView() {
        //設定左上角的圖示
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.frag1_toolbar);
//        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        //toolbar.setNavigationIcon(R.drawable.icon_arrow_left_white); 之後要改用QRcode
        ImageButton shoppingcart = (ImageButton)view.findViewById(R.id.shoppingcart);
        shoppingcart.setOnClickListener(this);
        SearchView searchView = (SearchView) view.findViewById(R.id.searchView);
        searchView.setIconified(false);      //直接定位到搜尋欄 + 沒有放大鏡
        searchView.setFocusable(false);
        ImageView searchImage = (ImageView) searchView.findViewById(R.id.search_close_btn); //右側xx鍵
        searchImage.setImageDrawable(null);
        searchView.clearFocus();


        Drawable drawable = getResources().getDrawable(R.drawable.icon_arrow_double);
        drawable.setBounds(0, 0, (int) (drawable.getIntrinsicWidth() * 0.5), (int) (drawable.getIntrinsicHeight() * 0.5)); //縮小圖片
        TextView tab1moretextview = (TextView) view.findViewById(R.id.specialDiscount_more);
        tab1moretextview.setCompoundDrawables(null, null, drawable, null); //元件裡出現的位置
//        tab1moretextview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                switch (v.getId()) {
//                    case R.id.tab1_more:
//                        Log.i(TAG, "756126343");
//                        break;
//                }
//            }
//        });
        tab1moretextview.setOnClickListener(this);

        TextView healthRecommendmoretextview = (TextView) view.findViewById(R.id.healthRecommend_more);
        healthRecommendmoretextview.setCompoundDrawables(null, null, drawable, null); //元件裡出現的位置
        healthRecommendmoretextview.setOnClickListener(this);

        TextView oldManHealthmoretextview = (TextView) view.findViewById(R.id.oldManHealth_more);
        oldManHealthmoretextview.setCompoundDrawables(null, null, drawable, null); //元件裡出現的位置
        oldManHealthmoretextview.setOnClickListener(this);

        TextView womanmoretextview = (TextView) view.findViewById(R.id.woman_more);
        womanmoretextview.setCompoundDrawables(null, null, drawable, null); //元件裡出現的位置
        womanmoretextview.setOnClickListener(this);

        TextView babymoretextview = (TextView) view.findViewById(R.id.baby_more);
        babymoretextview.setCompoundDrawables(null, null, drawable, null); //元件裡出現的位置
        babymoretextview.setOnClickListener(this);

        TextView manmoretextview = (TextView) view.findViewById(R.id.man_more);
        manmoretextview.setCompoundDrawables(null, null, drawable, null); //元件裡出現的位置
        manmoretextview.setOnClickListener(this);

        TextView healthfoodmoretextview = (TextView) view.findViewById(R.id.healthfood_more);
        healthfoodmoretextview.setCompoundDrawables(null, null, drawable, null); //元件裡出現的位置
        healthfoodmoretextview.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
      //  Log.i(TAG, ":" + v.getId());
        switch (v.getId()) {
            case R.id.shoppingcart:
                startActivity(new Intent().setClass(getActivity().getApplicationContext(),ShoppingCartActivity.class));
                break;
            case R.id.specialDiscount_more:
             //   Log.i(TAG, "tab1_more");
                break;
            case R.id.healthRecommend_more:
              //  Log.i(TAG, "tab2_more");
                break;
            case R.id.oldManHealth_more:
             //   Log.i(TAG, "tab3_more");
                break;
            case R.id.woman_more:
              //  Log.i(TAG, "tab4_more");
                break;
            case R.id.baby_more:
              //  Log.i(TAG, "tab2_more");
                break;
            case R.id.man_more:
                Log.i(TAG, "tab2_more");
                break;
            case R.id.healthfood_more:
              //  Log.i(TAG, "tab2_more");
                break;
            case R.id.customer_fab:
                Toast.makeText(getActivity().getApplicationContext(), "客服頁面未完成", Toast.LENGTH_SHORT).show();
             //   Log.i(TAG, "customer_fab");
                break;
        }
    }

}
