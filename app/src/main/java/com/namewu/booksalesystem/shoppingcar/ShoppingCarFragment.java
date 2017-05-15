package com.namewu.booksalesystem.shoppingcar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.namewu.booksalesystem.R;
import com.namewu.booksalesystem.Utils.L;
import com.namewu.booksalesystem.onlinedata.Bookdata;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/9.
 */

public class ShoppingCarFragment extends Fragment implements View.OnClickListener{
    private String TAG="ShoppingCarFragment";
    private ListView listview;
    private ImageView imgpaycheck;
    private TextView allmoney;
    private TextView textallpay;
    private BuycarListAdapter buycarlistAdapter;
    private ArrayList<Bookdata> listdata;
    private Boolean all_status=true;
    private TextView text_num;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_shop,null);
        initview(view);
        return view;
    }

    private void initview(View view) {
        listview= (ListView) view.findViewById(R.id.listview_buycar);
        imgpaycheck= (ImageView) view.findViewById(R.id.fragment_shop_allcheck);
        allmoney= (TextView) view.findViewById(R.id.fragment_shop_allmoney);
        textallpay= (TextView) view.findViewById(R.id.fragment_shop_pay);
        imgpaycheck= (ImageView) view.findViewById(R.id.fragment_shop_allcheck);
        text_num= (TextView) view.findViewById(R.id.fragment_shop_paynum);
        imgpaycheck.setOnClickListener(this);
        listdata=new ArrayList<>();
        listdata.add(new Bookdata("西游记","传统文化四大名著",true,97,4,new ArrayList<String>()));
        listdata.add(new Bookdata("西游记","传统文化四大名著",true,97,4,new ArrayList<String>()));
        listdata.add(new Bookdata("西游记","传统文化四大名著",true,97,4,new ArrayList<String>()));
        listdata.add(new Bookdata("西游记","传统文化四大名著",true,97,4,new ArrayList<String>()));
        buycarlistAdapter=new BuycarListAdapter(getActivity(),listdata);
        buycarlistAdapter.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position=(int)v.getTag();
                L.i(TAG,"position"+position);
                switch (v.getId()){
                    case R.id.listitem_buycar_add:
                        int i=buycarlistAdapter.getListnum().get(position);
                        i++;
                        buycarlistAdapter.getListnum().set(position,i);
                        buycarlistAdapter.notifyDataSetChanged();break;
                    case R.id.listitem_buycar_reduce:
                        int ii=buycarlistAdapter.getListnum().get(position);
                        if(ii!=1)
                        ii--;
                        buycarlistAdapter.getListnum().set(position,ii);
                        buycarlistAdapter.notifyDataSetChanged();break;
                    case R.id.listitem_buycar_check:
                        boolean iii=buycarlistAdapter.getListcheck().get(position);
                        if(iii) iii=false;else iii=true;
                        buycarlistAdapter.getListcheck().set(position,iii);
                        buycarlistAdapter.notifyDataSetChanged();break;
                }
                int paycount=0;
                int allm=0;
                for(int i=0;i<listdata.size();i++){
                    if(buycarlistAdapter.getListcheck().get(i)){
                        paycount++;
                        allm=allm+buycarlistAdapter.getListnum().get(i)*listdata.get(i).getPrice();
                    }
                    L.i(TAG,"allm"+allm);
                }
                allmoney.setText(allm+"");
                text_num.setText("("+paycount+")");
            }
        });
        listview.setAdapter(buycarlistAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_shop_allcheck:
                if(all_status){
                    all_status=false;
                    imgpaycheck.setImageResource(R.mipmap.pay_and_delivery_unchecked);
                    allmoney.setText("0");
                }else {
                    all_status=true;
                    imgpaycheck.setImageResource(R.mipmap.pay_and_delivery_fast_checked);
                    int allm=0;
                    for(int i=0;i<listdata.size();i++){
                        if(buycarlistAdapter.getListcheck().get(i))
                            allm=allm+buycarlistAdapter.getListnum().get(i)*listdata.get(i).getPrice();
                        L.i(TAG,"allm"+allm);
                    }
                    allmoney.setText(allm+"");
                } ;break;
        }
    }
}
