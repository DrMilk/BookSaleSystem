package com.namewu.booksalesystem.main;

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

import java.util.List;

/**
 * Created by Administrator on 2017/3/17.
 */

public class BookListAdatapter extends BaseAdapter{
    private List<Bookdata> list_data;
    private MyViewHolder wuViewHolder;
    private LayoutInflater mlayoutinflater;
    private MyUpload myUpload;
    public BookListAdatapter(Context mcontext, List<Bookdata> list_data){
        mlayoutinflater=LayoutInflater.from(mcontext);
        this.list_data=list_data;
        myUpload=new MyUpload(mcontext);
    }

    @Override
    public int getCount() {
        return list_data.size();
    }

    @Override
    public Object getItem(int position) {
        return list_data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            wuViewHolder=new MyViewHolder();
            convertView=mlayoutinflater.inflate(R.layout.listitem_food,null);
            wuViewHolder.text_context= (TextView) convertView.findViewById(R.id.listitem2_context);
            wuViewHolder.text_title= (TextView) convertView.findViewById(R.id.listitem2_title);
            wuViewHolder.img= (ImageView) convertView.findViewById(R.id.listitem2_img);
            wuViewHolder.text_price= (TextView) convertView.findViewById(R.id.listitem2_price);
            convertView.setTag(wuViewHolder);
        }else {
            wuViewHolder= (MyViewHolder) convertView.getTag();
        }
        wuViewHolder.text_title.setText(list_data.get(position).getTitle());
        wuViewHolder.text_context.setText(list_data.get(position).getContext());
        wuViewHolder.text_price.setText(list_data.get(position).getPrice()+"");
        myUpload.download_asynchronous("yuetiantravel","listimg/"+list_data.get(position).getObjectId(),wuViewHolder.img);
        return convertView;
    }

    public List<Bookdata> getList_data() {
        return list_data;
    }

    public void setList_data(List<Bookdata> list_data) {
        this.list_data = list_data;
    }

    private class MyViewHolder{
        private TextView text_title;
        private TextView text_context;
        private ImageView img;
        private TextView text_price;
    }
}
