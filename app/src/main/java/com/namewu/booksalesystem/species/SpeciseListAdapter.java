package com.namewu.booksalesystem.species;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.namewu.booksalesystem.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/14.
 */

public class SpeciseListAdapter extends BaseAdapter{
    private LayoutInflater inflater;
    private ArrayList<String> list_data;
    private MyViewHolder viewHolder;
    public SpeciseListAdapter(Context context, ArrayList<String> list_data) {
        inflater=LayoutInflater.from(context);
        this.list_data=list_data;
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
            convertView=inflater.inflate(R.layout.listitem_spieces,null);
            viewHolder=new MyViewHolder();
            viewHolder.textView= (TextView) convertView.findViewById(R.id.listitem_spieces_text);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (MyViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(list_data.get(position));
        convertView.setBackgroundResource(R.color.black_alpha2);
        return convertView;
    }
    private class MyViewHolder{
        private TextView textView;
    }
}
