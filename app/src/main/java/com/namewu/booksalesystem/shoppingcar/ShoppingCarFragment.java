package com.namewu.booksalesystem.shoppingcar;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.namewu.booksalesystem.R;
import com.namewu.booksalesystem.Utils.L;
import com.namewu.booksalesystem.Utils.MySdcard;
import com.namewu.booksalesystem.onlinedata.Bookdata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

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
    private ArrayList<String> list_address;
    private Handler handle=new Handler(){
    };
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_shop_allcheck:
                int paycount=0;
                ArrayList<Boolean> listcheck=buycarlistAdapter.getListcheck();
                if(all_status){
                    all_status=false;
                    imgpaycheck.setImageResource(R.mipmap.pay_and_delivery_unchecked);
                    for (int i=0;i<listcheck.size();i++){
                        listcheck.set(i,false);
                    }
                    text_num.setText("("+paycount+")");
                    allmoney.setText("0");
                }else {
                    all_status=true;
                    imgpaycheck.setImageResource(R.mipmap.pay_and_delivery_fast_checked);
                    for (int i=0;i<listcheck.size();i++){
                        listcheck.set(i,true);
                        paycount++;
                    }
                    int allm=0;
                    for(int i=0;i<listdata.size();i++){
                        if(buycarlistAdapter.getListcheck().get(i))
                            allm=allm+buycarlistAdapter.getListnum().get(i)*listdata.get(i).getPrice();
                        L.i(TAG,"allm"+allm);
                    }
                    allmoney.setText(allm+"");
                    text_num.setText("("+paycount+")");
                }
                buycarlistAdapter.notifyDataSetChanged();break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        File file=new File(MySdcard.pathsearchtxt+File.separator+"shop.txt");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(file.length()!=0){
            FileInputStream fis = null;
            try {
                fis = new FileInputStream(file);
            } catch (FileNotFoundException e) {
                L.i(TAG,"fis");
                e.printStackTrace();
            }
            ObjectInputStream ois = null;
            try {
                ois = new ObjectInputStream(fis);
            } catch (IOException e) {
                L.i(TAG,"ois");
                e.printStackTrace();
            }
            try {
                list_address = (ArrayList<String>) ois.readObject();
            } catch (ClassNotFoundException e) {
                L.i(TAG,"oois");
                e.printStackTrace();
            } catch (IOException e) {
                L.i(TAG,"ooois");
                e.printStackTrace();
            }
            try {
                ois.close();
            } catch (IOException e) {
                L.i(TAG,"ois");
                e.printStackTrace();
            }
            try {
                fis.close();
            } catch (IOException e) {
                L.i(TAG,"ois");
                e.printStackTrace();
            }
        }
        if(list_address==null||list_address.size()==0){
            list_address=new ArrayList<>();
            L.i(TAG,"what");
        }
        for(int i=0;i<list_address.size();i++){
            L.i(TAG,"循环");
            BmobQuery<Bookdata> bookquery=new BmobQuery<>();
            bookquery.getObject(list_address.get(i), new QueryListener<Bookdata>() {
                @Override
                public void done(Bookdata bookdata, BmobException e) {
                    if(e==null){
                        listdata.add(bookdata);
                        L.i(TAG,"下载了");
                        if(listdata.size()==list_address.size()){
                            updataview();
                        }
                    }else {
                        L.i(TAG,"失败了");
                        updataview();
                    }
                }
            });
        }
    }

    private void updataview() {
        handle.post(new Runnable() {
            @Override
            public void run() {
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
                int paycount=0;
                if(all_status){
                    int allm=0;
                    for(int i=0;i<listdata.size();i++){
                        if(buycarlistAdapter.getListcheck().get(i))
                            allm=allm+buycarlistAdapter.getListnum().get(i)*listdata.get(i).getPrice();
                        paycount++;
                        L.i(TAG,"allm"+allm);
                    }
                    text_num.setText("("+paycount+")");
                    allmoney.setText(allm+"");
                }else {
                    allmoney.setText("0");
                    text_num.setText("("+paycount+")");
                }
            }
        });
    }
}
