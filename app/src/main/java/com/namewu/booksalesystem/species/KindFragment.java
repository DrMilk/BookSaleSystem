package com.namewu.booksalesystem.species;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.namewu.booksalesystem.R;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/5/9.
 */

public class KindFragment extends Fragment{
    private ListView listView;
    private GridView gridView;
    private ArrayList<String> list_one;
    private ArrayList<String[]> list_two;
    private ArrayList<Integer[]> list_two_img;
    private SpeciseListAdapter listadapter;
    private SpeciseGridAdapter gridadapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_species,null);
        initview(view);
        return view;
    }

    private void initview(View v) {
        listView= (ListView) v.findViewById(R.id.species_listview);
        gridView= (GridView) v.findViewById(R.id.species_gridview);
        initdataleft();
        listadapter=new SpeciseListAdapter(getActivity(),list_one);
        gridadapter=new SpeciseGridAdapter(getActivity(),list_two.get(0),list_two_img.get(0));
        gridView.setAdapter(gridadapter);
        listView.setAdapter(listadapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                gridadapter.setList_data(list_two.get(position));
                gridadapter.setList_img(list_two_img.get(position));
                gridadapter.notifyDataSetChanged();
            }
        });
    }

    private void initdataleft() {
        list_one=new ArrayList<>();
        list_one.add("第一类图书");
        list_one.add("第二类图书");
        list_one.add("第三类图书");
        list_one.add("第四类图书");
        list_one.add("第五类图书");
        list_one.add("第六类图书");
        list_one.add("第七类图书");
        list_one.add("第八类图书");
        list_one.add("第九类图书");
        list_one.add("第十类图书");
        list_one.add("第十一类图书");
        list_one.add("第十二类图书");
        list_two=new ArrayList<>();
        list_two_img=new ArrayList<>();
        list_two.add(new String[]{"三国演义","红楼梦","西游记","水浒传","三国演义","红楼梦","西游记","水浒传",
                "三国演义","红楼梦","西游记","水浒传","三国演义","红楼梦","西游记","水浒传"});
        list_two_img.add(new Integer[]{R.mipmap.ohd,R.mipmap.ohh,R.mipmap.ohi,R.mipmap.ott,
                R.mipmap.ohd,R.mipmap.ohh,R.mipmap.ohi,R.mipmap.ott,R.mipmap.ohd,R.mipmap.ohh,R.mipmap.ohi,R.mipmap.ott
        ,R.mipmap.ohd,R.mipmap.ohh,R.mipmap.ohi,R.mipmap.ott});
        list_two.add(new String[]{"三国演义1","红楼梦1","西游记1","水浒传1"});
        list_two_img.add(new Integer[]{R.mipmap.ohd,R.mipmap.ohh,R.mipmap.ohi,R.mipmap.ott});
        list_two.add(new String[]{"三国演义2","红楼梦2","西游记2","水浒传2"});
        list_two_img.add(new Integer[]{R.mipmap.ohd,R.mipmap.ohh,R.mipmap.ohi,R.mipmap.ott});
        list_two.add(new String[]{"三国演义3","红楼梦3","西游记3","水浒传3"});
        list_two_img.add(new Integer[]{R.mipmap.ohd,R.mipmap.ohh,R.mipmap.ohi,R.mipmap.ott});
    }
}
