package com.example.user.aina_app.common;


import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.user.aina_app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Page2Fragment extends Fragment {
    View view;

    public Page2Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the productvetifydetail_layout for this fragment
        view = inflater.inflate(R.layout.fragment_page2, container, false);
        initView();
        return view;
    }

    public static Page2Fragment newInstance() {
        Page2Fragment f = new Page2Fragment();
        Bundle args = new Bundle();
        args.putInt("index", 1);
        f.setArguments(args);
        return f;
    }

    public void initView() {
        //設定左上角的圖示
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.frag2_toolbar);
        SearchView searchView = (SearchView) view.findViewById(R.id.searchView);
        searchView.setIconified(false);      //直接定位到搜尋欄 + 沒有放大鏡
        searchView.setFocusable(false);
        searchView.clearFocus();
        ImageView searchImage = (ImageView) searchView.findViewById(R.id.search_close_btn); //右側xx鍵
        searchImage.setImageDrawable(null);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
    }
}
