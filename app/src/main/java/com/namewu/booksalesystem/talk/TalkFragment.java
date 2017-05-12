package com.namewu.booksalesystem.talk;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.namewu.booksalesystem.R;
import com.namewu.booksalesystem.Utils.L;
import com.namewu.booksalesystem.main.WangContextRecyclerViewAdapter;
import com.namewu.booksalesystem.onlinedata.Talkdata;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Administrator on 2017/5/9.
 */

public class TalkFragment extends Fragment{
    private String TAG="HotelFragment";
    private RecyclerView listView;
    private ArrayList<Talkdata> list_hotel;
    private Context mcontext;
    private ArrayList<String> list_str;
    private WangContextRecyclerViewAdapter mcontextAdapter;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };
    @Override
    public void onCreate(Bundle savedInstanceState) {
        mcontext=getActivity();
        list_str=getArguments().getStringArrayList("data");
        L.i(TAG,list_str.size()+"长度");
        if(list_str==null)
            list_str=new ArrayList<>();
        if(list_hotel==null){
            list_hotel=new ArrayList<>();
            for(int i=0;i<list_str.size();i++){
                BmobQuery<Talkdata> query = new BmobQuery<Talkdata>();
                query.getObject(list_str.get(i), new QueryListener<Talkdata>() {
                    @Override
                    public void done(Talkdata object, BmobException e) {
                        if(e==null){
                            list_hotel.add(object);
                            L.i(TAG,"all"+"下载成功");
                        }else{
                            L.i(TAG,"all"+"下载失败");
                        }
                        updataview();
                    }


                });
            }
        }
        super.onCreate(savedInstanceState);
    }

    private void updataview() {
        if(list_hotel.size()==list_str.size()){
            handler.post(new Runnable() {
                @Override
                public void run() {
                    L.i(TAG,"Gengxinle");
                    mcontextAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_talk,null);
        mcontext=getActivity();
        listView= (RecyclerView) view.findViewById(R.id.list_context_main);
//        list_hotel.add(new Hoteldata("上海龙之梦大酒店","行政套房(大床)",true,1959));
//        list_hotel.add(new Hoteldata("如家酒店","行政套房(大床)",true,364));
//        list_hotel.add(new Hoteldata("万达酒店","行政套房(大床)，8点人民广场准时出发",true,3626));
//        list_hotel.add(new Hoteldata("如家酒店","泰国市区免费接，8点人民广场准时出发",true,75));
//        list_hotel.add(new Hoteldata("上海龙之梦大酒店","上海市区免费接，8点人民广场准时出发",true,4100));
//        list_hotel.add(new Hoteldata("万达酒店","上海市区免费接，8点人民广场准时出发",true,7151));
//        list_hotel.add(new Hoteldata("如家酒店","上海市区免费接，8点人民广场准时出发",true,297));
//        list_hotel.add(new Hoteldata("如家酒店","上海市区免费接，8点人民广场准时出发",true,2034));
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        listView.setLayoutManager(staggeredGridLayoutManager);
        mcontextAdapter=new WangContextRecyclerViewAdapter(getActivity(),list_hotel);
        mcontextAdapter.setItemContextOnclickListenner(new WangContextRecyclerViewAdapter.ItemContextnclickListenner() {
            @Override
            public void onitemclickcontext(WangContextRecyclerViewAdapter.MyViewHolder viewHolder, int postion) {
//                Intent it=new Intent(getActivity(),DetailContextActivity.class);
//                Bundle bundle=new Bundle();
//                bundle.putSerializable("detailcontext",list_context.get(postion));
//                it.putExtras(bundle);
//                L.i(TAG,"到这步了~");
//                startActivity(it);
            }
        });
        listView.setAdapter(mcontextAdapter);
        return view;
    }
}
