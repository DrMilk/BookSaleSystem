package com.namewu.booksalesystem.shoppingcar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.iflytek.thridparty.A;
import com.namewu.booksalesystem.R;
import com.namewu.booksalesystem.Utils.MyUpload;
import com.namewu.booksalesystem.onlinedata.Bookdata;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/15.
 */

public class BuycarListAdapter extends BaseAdapter {
    private ArrayList<Bookdata> listdata;
    private ArrayList<Boolean> listcheck;
    private ArrayList<Integer> listnum;
    private LayoutInflater inflater;
    private MyUpload myUpload;
    private View.OnClickListener listener;
    public BuycarListAdapter(Context context, ArrayList<Bookdata> listdata){
        this.listdata=listdata;
        inflater=LayoutInflater.from(context);
        myUpload=new MyUpload(context);
        listcheck=new ArrayList<>();
        listnum=new ArrayList<>();
        for(int i=0;i<listdata.size();i++){
            listcheck.add(true);
            listnum.add(1);
        }
    }
    @Override
    public int getCount() {
        return listdata.size();
    }

    @Override
    public Object getItem(int position) {
        return listdata.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder;
        if(convertView==null){
            convertView=inflater.inflate(R.layout.listitem_buycar,null);
            myViewHolder=new MyViewHolder();
            myViewHolder.text_title= (TextView) convertView.findViewById(R.id.listitem_buycar_title);
            myViewHolder.text_price= (TextView) convertView.findViewById(R.id.listitem_buycar_price);
            myViewHolder.text_num= (TextView) convertView.findViewById(R.id.listitem_buycar_num);
            myViewHolder.text_smallmoney= (TextView) convertView.findViewById(R.id.listitem_buycar_smallmoney);
            myViewHolder.img_show= (ImageView) convertView.findViewById(R.id.listitem_buycar_img);
            myViewHolder.img_check= (ImageView) convertView.findViewById(R.id.listitem_buycar_check);
            myViewHolder.img_add= (ImageView) convertView.findViewById(R.id.listitem_buycar_add);
            myViewHolder.img_reduce= (ImageView) convertView.findViewById(R.id.listitem_buycar_reduce);
            convertView.setTag(myViewHolder);
        }else {
            myViewHolder= (MyViewHolder) convertView.getTag();
        }
        myViewHolder.text_title.setText(listdata.get(position).getTitle());
        myViewHolder.text_price.setText("￥ "+listdata.get(position).getPrice()+"");
        myViewHolder.text_num.setText(listnum.get(position)+"");
        if(listcheck.get(position)){
           int price=listdata.get(position).getPrice()*listnum.get(position);
            myViewHolder.text_smallmoney.setText("￥ "+price+"");
        }else {
            myViewHolder.text_smallmoney.setText("￥ "+0);
        }

        myViewHolder.num=position;
        myUpload.download_asynchronousSys("booksalesystem","listimg/"+listdata.get(position).getObjectId(),myViewHolder.img_show);
        if(listcheck.get(position)){
            myViewHolder.img_check.setImageResource(R.mipmap.pay_and_delivery_fast_checked);
        }else {
            myViewHolder.img_check.setImageResource(R.mipmap.pay_and_delivery_unchecked);
        }
        if(listnum.get(position)==1){
            myViewHolder.img_reduce.setImageResource(R.mipmap.shopping_cart_product_num_reduce_disable);
        }else {
            myViewHolder.img_reduce.setImageResource(R.mipmap.shopping_cart_product_num_reduce);
        }
        myViewHolder.img_add.setTag(position);
        myViewHolder.img_reduce.setTag(position);
        myViewHolder.img_check.setTag(position);
        myViewHolder.img_add.setOnClickListener(listener);
        myViewHolder.img_reduce.setOnClickListener(listener);
        myViewHolder.img_check.setOnClickListener(listener);
//        myViewHolder.img_check.setText(listdata.get(position).getTitle());
//        myViewHolder.img_add.setText(listdata.get(position).getTitle());
//        myViewHolder.img_reduce.setText(listdata.get(position).getTitle());
        return convertView;
    }

    public void setListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    public ArrayList<Integer> getListnum() {
        return listnum;
    }

    public void setListnum(ArrayList<Integer> listnum) {
        this.listnum = listnum;
    }

    public ArrayList<Boolean> getListcheck() {
        return listcheck;
    }

    public void setListcheck(ArrayList<Boolean> listcheck) {
        this.listcheck = listcheck;
    }

    public ArrayList<Bookdata> getListdata() {
        return listdata;
    }

    public void setListdata(ArrayList<Bookdata> listdata) {
        this.listdata = listdata;
    }

     class MyViewHolder{
        private ImageView img_check;
        private ImageView img_show;
        private ImageView img_add;
        private ImageView img_reduce;
        private TextView text_num;
        private TextView text_title;
        private TextView text_price;
        private TextView text_smallmoney;
        private Integer num;
     }
}
