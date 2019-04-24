package com.example.user.aina_app.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.user.aina_app.R;
import com.example.user.aina_app.activity.Good;

import java.util.ArrayList;
import java.util.List;

public class ProductExpandableListAdapter2 extends BaseExpandableListAdapter {
    private final String TAG = "ProductExpandableListAdapter";
    private Context _context;
    private List<String> _listDataHeader; // header titles
    private ArrayList<Good> goods;

    public ProductExpandableListAdapter2(Context context, ArrayList<Good> goods, List<String> listDataHeader) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.goods = goods;

        //    Log.i(TAG, "productImage" + productImage);

    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
//        return this._listDataChild.get(this._listDataHeader.get(groupPosition))
//                .get(childPosititon);

        return goods.get(groupPosition).getGoodImage();
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.orderlist_item, null);
        }
        // setListViewHeightBasedOnChildren(convertView,groupPosition,childPosition,isLastChild,convertView,parent);
        TextView producNameListChild = (TextView) convertView.findViewById(R.id.producname_textview);
        TextView productPriceListChild = (TextView) convertView.findViewById(R.id.productPrice);
        TextView productQuantityListChild = (TextView) convertView.findViewById(R.id.quantity);
        ImageView productImageListChild = (ImageView) convertView.findViewById(R.id.productImage);

        producNameListChild.setText(goods.get(childPosition).getGoodName());
        // productPriceListChild.setText(productPrice.get(childPosition));  單品單價
        String Price = String.valueOf(goods.get(childPosition).getGoodPrice() * goods.get(childPosition).getGoodQuantity()); // 單品總價
        productPriceListChild.setText(Price);
        productImageListChild.setImageResource(goods.get(childPosition).getGoodImage());

        productQuantityListChild.setText(String.valueOf(goods.get(childPosition).getGoodQuantity()));

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return goods.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.list_group, null);
        }

        TextView lblListHeader = (TextView) convertView.findViewById(R.id.lblListHeader);
        // lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
        //回傳false 會每次刷新(呼叫notifyDataSetChanged())
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
