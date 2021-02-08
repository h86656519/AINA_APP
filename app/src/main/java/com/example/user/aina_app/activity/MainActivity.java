package com.example.user.aina_app.activity;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.user.aina_app.R;
import com.example.user.aina_app.common.MyFragment;
import com.example.user.aina_app.common.Page1Fragment;
import com.example.user.aina_app.common.Page2Fragment;
import com.example.user.aina_app.common.Page3Fragment;
import com.example.user.aina_app.common.Page4Fragment;
import com.example.user.aina_app.common.ViewPagerAdapter;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    BottomNavigationView bottomNavigationView;
    private MenuItem menuItem;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top);
        initView();
        setupViewPager(viewPager);
    }



//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//        //点击BottomNavigationView的Item项，切换ViewPager页面
//        //menu/navigation.xml里加的android:orderInCategory属性就是下面item.getOrder()取的值
//        viewPager.setCurrentItem(menuItem.getOrder());
//        return true;
//    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.my_search, menu);
//        return true;
//    }


    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int position) {
        //页面滑动的时候，改变BottomNavigationView的Item高亮
        //  bottomNavigationView.getMenu().getItem(position).setChecked(true);
        if (menuItem != null) {
            menuItem.setChecked(false);
        } else {
            bottomNavigationView.getMenu().getItem(0).setChecked(false);
        }
        menuItem = bottomNavigationView.getMenu().getItem(position);
        menuItem.setChecked(true);
    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }

    public void initView() {
        //設定左上角的圖示
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
//        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        SearchView searchView = (SearchView) findViewById(R.id.searchView);
//        //   searchView.setIconifiedByDefault(false); //直接定位到搜尋欄 + 右側放大鏡
//        searchView.setIconified(false);      //直接定位到搜尋欄 + 沒有放大鏡
//        //  searchView.onActionViewExpanded();
//        searchView.setFocusable(false);
////        searchView.setFocusableInTouchMode(true);
//        searchView.clearFocus();
//
//        setSupportActionBar(toolbar);

        // ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open_drewer, R.string.nav_close_drewer);
        // 設定圖示的監聽器
        //  drawerLayout.addDrawerListener(toggle);
        //同步圖示的狀態
        // toggle.syncState();

        //設定側攔 Navigation 的監聽器
//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.addOnPageChangeListener(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(Page1Fragment.newInstance());
        adapter.addFragment(Page2Fragment.newInstance());
        adapter.addFragment(Page3Fragment.newInstance());
        adapter.addFragment(Page4Fragment.newInstance());
        adapter.addFragment(MyFragment.newInstance());
        viewPager.setAdapter(adapter);
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.page1:
                    viewPager.setCurrentItem(0);
                    break;
                case R.id.page2:
                    viewPager.setCurrentItem(1);
                    break;
                case R.id.page3:
//                    Intent intent = new Intent();
//                    intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//                    intent.setClass(MainActivity.this, ProductVerifyActivity.class);
//                    startActivity(intent);
                    viewPager.setCurrentItem(2);
                    break;
                case R.id.page4:
                    viewPager.setCurrentItem(3);
                    break;
                case R.id.page5:
                    viewPager.setCurrentItem(4);
                    break;
            }
            return false;
        }
    };

}
