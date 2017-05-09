package com.namewu.booksalesystem.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.namewu.booksalesystem.R;
import com.namewu.booksalesystem.onlinedata.Remakdata;

import java.util.List;

/**
 * Created by Administrator on 2017/3/19.
 */

public class RemarkAdapter extends BaseAdapter{
    private List<Remakdata> list_data;
    private RemarkAdapter.MyViewHolder wuViewHolder;
    private LayoutInflater mlayoutinflater;
    public RemarkAdapter(Context mcontext, List<Remakdata> list_data){
        mlayoutinflater=LayoutInflater.from(mcontext);
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
            wuViewHolder=new RemarkAdapter.MyViewHolder();
            convertView=mlayoutinflater.inflate(R.layout.listitem_food_remark,null);
            wuViewHolder.text_context= (TextView) convertView.findViewById(R.id.list_food_text);
            wuViewHolder.text_id= (TextView) convertView.findViewById(R.id.list_food_id);
            wuViewHolder.text_time= (TextView) convertView.findViewById(R.id.list_food_time);
            convertView.setTag(wuViewHolder);
        }else {
            wuViewHolder= (RemarkAdapter.MyViewHolder) convertView.getTag();
        }
        wuViewHolder.text_context.setText(list_data.get(position).getContext());
        wuViewHolder.text_id.setText(list_data.get(position).getId());
        wuViewHolder.text_time.setText(list_data.get(position).getCreatedAt());
        return convertView;
    }
    private class MyViewHolder{
        private TextView text_time;
        private TextView text_context;
        private TextView text_id;
    }
}
