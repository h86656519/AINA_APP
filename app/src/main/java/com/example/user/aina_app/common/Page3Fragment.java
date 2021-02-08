package com.example.user.aina_app.common;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.user.aina_app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Page3Fragment extends Fragment {
    View view, topview;

    public Page3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the productvetifydetail_layout for this fragment
        view = inflater.inflate(R.layout.fragment_page3, container, false);
        topview = inflater.inflate(R.layout.toolbar_main, container, false);
        //   Log.i("suvini","topview :" + topview);
        initView();
        return view;
    }


    public static Page3Fragment newInstance() {
        Page3Fragment f = new Page3Fragment();
        Bundle args = new Bundle();
        args.putInt("index", 2);
        f.setArguments(args);
        return f;
    }

    public void initView() {
        //設定左上角的圖示
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.frag3_toolbar);
        TextView textView = toolbar.findViewById(R.id.toolbar_title);
        textView.setText(R.string.product_verify);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).setTitle(null); //隱藏 app name
    }
}
