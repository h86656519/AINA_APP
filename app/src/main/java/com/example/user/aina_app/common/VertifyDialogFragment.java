package com.example.user.aina_app.common;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.user.aina_app.R;
import com.example.user.aina_app.activity.ProductvetifyDetailActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class VertifyDialogFragment extends DialogFragment {
    View view;
    private String result;
    TextView vertifyrusult,vertifyQuestion;
    ImageView imageView;

    public VertifyDialogFragment() {
        // Required empty public constructor
    }

    public void setResult(String result) {
        this.result = result;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // 設置背景透明
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        view = inflater.inflate(R.layout.fragment_vertifyresult_dialog, container, false);

        vertifyrusult = (TextView) view.findViewById(R.id.vertifyrusult_textview);
        vertifyQuestion = (TextView) view.findViewById(R.id.vertifyQuestion_textview);
        imageView = (ImageView) view.findViewById(R.id.vertifyrusult_imageview);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (result.equals("success")) {
            vertifyrusult.setText(R.string.business_verify_message11);
            vertifyQuestion.setText(R.string.business_verify_message12);
            imageView.setImageResource(R.drawable.pic_sucess);
            //取消
            view.findViewById(R.id.vertifyresult_no).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
            view.findViewById(R.id.vertifyresult_yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getContext().getApplicationContext(), ProductvetifyDetailActivity .class));
                    dismiss();
                }
            });

        } else if (result.equals("fail")) {
            vertifyrusult.setText(R.string.business_verify_message9);
            vertifyQuestion.setText(R.string.business_verify_message13);
            imageView.setImageResource(R.drawable.pic_error);
            //確定
            view.findViewById(R.id.vertifyresult_yes).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //到客服頁
                }
            });
            //取消
            view.findViewById(R.id.vertifyresult_no).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }
}
