package com.namewu.booksalesystem.shoppingcar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.namewu.booksalesystem.R;
import com.namewu.booksalesystem.Utils.MyUpload;
import com.namewu.booksalesystem.onlinedata.Bookdata;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/15.
 */

public class OrderListAdapter extends BaseAdapter {
    private ArrayList<String> data_name;
    private ArrayList<Integer> data_preice;
    private ArrayList<Integer> data_count;
    private ArrayList<Integer> data_smallall;
    private LayoutInflater inflater;
    private MyUpload myUpload;
    private View.OnClickListener listener;
    private MyViewHolder myViewHolder;

    public OrderListAdapter(Context mcontext, ArrayList<String> data_name, ArrayList<Integer> data_preice,
                            ArrayList<Integer> data_count) {
        this.data_name=data_name;
        this.data_preice=data_preice;
        this.data_count=data_count;
        data_smallall=new ArrayList<>();
        inflater=LayoutInflater.from(mcontext);
        for(int i=0;i<data_name.size();i++){
            data_smallall.add(data_preice.get(i)*data_count.get(i));
        }
    }

    @Override
    public int getCount() {
        return data_name.size();
    }

    @Override
    public Object getItem(int position) {
        return data_name.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=inflater.inflate(R.layout.listitem_order,null);
            myViewHolder=new MyViewHolder();
            myViewHolder.text_name= (TextView) convertView.findViewById(R.id.listitem_order_name);
            myViewHolder.text_count= (TextView) convertView.findViewById(R.id.listitem_order_count);
            myViewHolder.text_money= (TextView) convertView.findViewById(R.id.listitem_order_money);
            myViewHolder.text_price= (TextView) convertView.findViewById(R.id.listitem_order_price);
            convertView.setTag(myViewHolder);
        }else {
            myViewHolder= (MyViewHolder) convertView.getTag();
        }
        myViewHolder.text_name.setText(data_name.get(position));
        myViewHolder.text_price.setText(data_preice.get(position)+"");
        myViewHolder.text_count.setText(data_count.get(position).toString());
        myViewHolder.text_money.setText(data_smallall.get(position).toString());
        return convertView;
    }

     class MyViewHolder{
         private TextView text_name;
         private TextView text_price;
         private TextView text_count;
         private TextView text_money;
     }
}
