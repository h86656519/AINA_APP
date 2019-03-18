package com.example.user.aina_app.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.user.aina_app.common.TripUniformInvoiceFragment;
import com.example.user.aina_app.common.DonationInvoiceFragment;
import com.example.user.aina_app.common.ElectInvoiceFragment;

import java.util.ArrayList;
import java.util.List;

public class TabAdaper extends FragmentPagerAdapter {

    List<Fragment> fragmentList = new ArrayList<>();
    // 标题数组
    String[] titles = {"電子發票", "發票捐贈", "三聯式發票"};

    public TabAdaper(FragmentManager fm) {
        super(fm);
        fragmentList.add(new ElectInvoiceFragment());
        fragmentList.add(new DonationInvoiceFragment());
        fragmentList.add(new TripUniformInvoiceFragment());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
