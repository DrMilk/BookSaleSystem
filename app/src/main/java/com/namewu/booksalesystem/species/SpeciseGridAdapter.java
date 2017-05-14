package com.namewu.booksalesystem.species;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.namewu.booksalesystem.R;
import com.namewu.booksalesystem.Utils.L;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/14.
 */

public class SpeciseGridAdapter extends BaseAdapter{
    private String TAG="SpeciseGridAdapter";
    private LayoutInflater inflater;
    private String[] list_data;
    private Integer[] list_img;
    private MyViewHolder viewHolder;
    public SpeciseGridAdapter(Context context, String[] list_data,Integer[] list_img) {
        inflater=LayoutInflater.from(context);
        this.list_data=list_data;
        this.list_img=list_img;
    }

    @Override
    public int getCount() {
        return list_data.length;
    }

    @Override
    public Object getItem(int position) {
        return list_data[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView=inflater.inflate(R.layout.listitem_spieces_two,null);
            viewHolder=new MyViewHolder();
            viewHolder.textView= (TextView) convertView.findViewById(R.id.listitem_spieces_texttwo);
            viewHolder.imageView= (ImageView) convertView.findViewById(R.id.listitem_spieces_imgtwo);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (MyViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(list_data[position]);
        viewHolder.imageView.setImageResource(list_img[position]);
        return convertView;
    }

    public void setList_data(String[] list_data) {
        this.list_data = list_data;
    }

    public void setList_img(Integer[] list_img) {
        this.list_img = list_img;
    }

    private class MyViewHolder{
        private ImageView imageView;
        private TextView textView;
    }
}
