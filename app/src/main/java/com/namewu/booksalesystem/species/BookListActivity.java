package com.namewu.booksalesystem.species;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import com.namewu.booksalesystem.R;
import com.namewu.booksalesystem.Utils.L;
import com.namewu.booksalesystem.Utils.T;
import com.namewu.booksalesystem.main.BookDetailActivity;
import com.namewu.booksalesystem.main.BookListAdatapter;
import com.namewu.booksalesystem.onlinedata.Bookdata;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by Administrator on 2017/5/30.
 */

public class BookListActivity extends Activity {
    private SearchView searchView;
    private ArrayList<Bookdata> listdata=new ArrayList<>();
    private BookListAdatapter adapter;
    private String TAG="BookListActivity";
    private ListView listview;
    private int specise_count;
    private Context mcontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        mcontext=this;
        recivedata();
        initView();

    }

    private void recivedata() {
        Intent it=getIntent();
        specise_count=it.getIntExtra("spiecse",11);
    }

    private void initView() {
        searchView = (SearchView) findViewById(R.id.search_view);
        listview= (ListView) findViewById(R.id.activity_spot_listview);
        adapter=new BookListAdatapter(mcontext,listdata);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it=new Intent(BookListActivity.this,BookDetailActivity.class);
                Bundle bundle=new Bundle();
                bundle.putString("title",listdata.get(position).getTitle());
                bundle.putString("context",listdata.get(position).getContext());
                bundle.putString("price",listdata.get(position).getPrice()+"");
                bundle.putString("id",listdata.get(position).getObjectId());
                bundle.putStringArrayList("remarklist",listdata.get(0).getList_remarkd());
                it.putExtras(bundle);
                startActivity(it);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query.length()!=0){
                    L.i(TAG,"我要搜索"+query);
                    BmobQuery<Bookdata> query1 = new BmobQuery<Bookdata>();
                    query1.addWhereEqualTo("title",query);
                    query1.setLimit(10);
                    query1.findObjects(new FindListener<Bookdata>() {
                        @Override
                        public void done(List<Bookdata> list, BmobException e) {
                            Intent it=new Intent(BookListActivity.this,BookDetailActivity.class);
                            Bundle bundle=new Bundle();
                            bundle.putString("title",list.get(0).getTitle());
                            bundle.putString("context",list.get(0).getContext());
                            bundle.putString("price",list.get(0).getPrice()+"");
                            bundle.putString("id",list.get(0).getObjectId());
                            bundle.putStringArrayList("remarklist",list.get(0).getList_remarkd());
                            it.putExtras(bundle);
                            startActivity(it);
                        }
                    });
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(listdata.size()==0){
            BmobQuery<Bookdata> query=new BmobQuery();
            query.addWhereEqualTo("specise",specise_count);
            query.setLimit(50);
            query.findObjects(new FindListener<Bookdata>() {
                @Override
                public void done(List<Bookdata> list, BmobException e) {
                    if(e==null){
                        listdata= (ArrayList<Bookdata>) list;
                        adapter.setList_data(listdata);
                        adapter.notifyDataSetChanged();
                    }else {
                        T.showShot(mcontext,"数据加载失败！");
                    }
                }
            });
        }
    }
}