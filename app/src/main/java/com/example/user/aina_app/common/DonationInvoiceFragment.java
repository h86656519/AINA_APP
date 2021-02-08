package com.example.user.aina_app.common;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.user.aina_app.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class DonationInvoiceFragment extends Fragment {


    public DonationInvoiceFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the productvetifydetail_layout for this fragment
        return inflater.inflate(R.layout.fragment_donation_invoice, container, false);
    }

}
