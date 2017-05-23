package com.namewu.booksalesystem.shoppingcar;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import com.namewu.booksalesystem.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/22.
 */

public class OrderBuy extends Activity{
    private Context mcontext;
    private ArrayList<String> data_name;
    private ArrayList<Integer> data_preice;
    private ArrayList<Integer> data_count;
    private ArrayList<String> data_address;
    private int allmoney;
    private ListView listview;
    private TextView all;
    private OrderListAdapter orderadapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderbuy);
        mcontext=this;
        recivedata();
        initView();
    }

    private void initView() {
        listview= (ListView) findViewById(R.id.activity_orderbuy_listview);
        all= (TextView) findViewById(R.id.activity_orderbuy_allmoney);
        orderadapter=new OrderListAdapter(mcontext,data_name,data_preice,data_count);
        listview.setAdapter(orderadapter);
        all.setText(all+"");
    }

    private void recivedata() {
        Intent it=getIntent();
        Bundle bundle=it.getExtras();
        data_name=bundle.getStringArrayList("name");
        data_preice=bundle.getIntegerArrayList("price");
        data_count=bundle.getIntegerArrayList("count");
        data_address=bundle.getStringArrayList("address");
        allmoney=bundle.getInt("allmoney");
    }

}
