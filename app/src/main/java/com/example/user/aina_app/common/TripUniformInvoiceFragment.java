package com.example.user.aina_app.common;


import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.user.aina_app.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class TripUniformInvoiceFragment extends Fragment {
    private final String TAG = "TripUniformInvoiceFragment";
    private String[] province = new String[]{"請選擇縣市", "台北市", "新北市"};
    private String[] city = new String[]{"請選擇鄉鎮市區", "100 中正區", "103 大同區", "104 中山區", "105 松山區",
            "106 大安區", "108 萬華區", "110 信義區", "111 士林區",
            "112 北投區", "114 內湖區", "115 南港區", "116 文山區"};
    private String[][] pandc = new String[][]{
            {"請選擇鄉鎮市區"},
            {"100 中正區", "103 大同區", "104 中山區", "105 松山區", "106 大安區",
                    "108 萬華區", "110 信義區", "111 士林區", "112 北投區", "114 內湖區",
                    "115 南港區", "116 文山區"},
            {"207 萬里區", "208 金山區", "220 板橋區", "221 汐止區", "222 深坑區",
                    "223 石碇區", "224 瑞芳區", "226 平溪區", "227 雙溪區", "228 貢寮區",
                    "231 新店區", "232 坪林區", "233 烏來區", "234 永和區", "235 中和區",
                    "236 土城區", "237 三峽區", "238 樹林區", "253 石門區",}};
    private Spinner sp;
    private Spinner sp2;
    ArrayAdapter<String> adapter;
    ArrayAdapter<String> adapter2;
    int pos;

    public TripUniformInvoiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trip_uniform_invoice, container, false);

        adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, province);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp = (Spinner) view.findViewById(R.id.city_spinner);
        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(selectListener);

        adapter2 = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, city);
        // adapter2 = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.productvetifydetail_layout.simple_spinner_item, getResources().getStringArray(R.array.district));
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
        sp2 = (Spinner) view.findViewById(R.id.countryside_spinner);
        sp2.setEnabled(false);
        sp2.setAdapter(adapter2);

        return view;
    }

    private AdapterView.OnItemSelectedListener selectListener = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView parent, View v, int position, long id) {
            pos = sp.getSelectedItemPosition();
            if (pos == 0) {
                adapter2 = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, pandc[pos]);
                sp2.setEnabled(false);

            } else {
                sp2.setEnabled(true);
                adapter2 = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, pandc[pos]);
                sp2.setAdapter(adapter2);
            }

        }

        public void onNothingSelected(AdapterView arg0) {

        }
    };

}
