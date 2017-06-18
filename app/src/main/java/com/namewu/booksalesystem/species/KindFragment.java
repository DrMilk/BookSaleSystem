package com.namewu.booksalesystem.species;

import android.app.Fragment;
import android.content.Intent;
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
    private String TAG="KindFragment";
    private ListView listView;
    private GridView gridView;
    private ArrayList<String> list_one;
    private ArrayList<String[]> list_two;
    private ArrayList<Integer[]> list_two_img;
    private SpeciseListAdapter listadapter;
    private SpeciseGridAdapter gridadapter;
    private int listview_status_count=1;
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
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it=new Intent(getActivity(),BookListActivity.class);
                it.putExtra("spiecse",listview_status_count*10+position+1);
                startActivity(it);
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                listview_status_count=position+1;
                gridadapter.setList_data(list_two.get(position));
                gridadapter.setList_img(list_two_img.get(position));
                gridadapter.notifyDataSetChanged();
            }
        });
    }

    private void initdataleft() {
        list_one=new ArrayList<>();
        list_one.add("教材教辅");
        list_one.add("少儿");
        list_one.add("文学");
        list_one.add("小说");
        list_one.add("历史");
        list_one.add("经管");
        list_one.add("励志");
        list_one.add("人文社科");
        list_one.add("生活");
        list_one.add("科普");
        list_one.add("进口图书");
        list_one.add("促销");
        list_two=new ArrayList<>();
        list_two_img=new ArrayList<>();
        // 教材教辅
        list_two.add(new String[]{"考试图书","外语学习","中小学教辅","大中专教材","词典与工具书","教师教育"});
        list_two_img.add(new Integer[]{R.mipmap.p1_1,R.mipmap.p1_2,R.mipmap.p1_3,R.mipmap.p1_4,
                R.mipmap.p1_5,R.mipmap.p1_6});
        // 少儿
        list_two.add(new String[]{"儿童文学","科普百科","少儿英语","幼儿启蒙","儿童手工"});
        list_two_img.add(new Integer[]{R.mipmap.p2_1,R.mipmap.p2_2,R.mipmap.p2_3,R.mipmap.p2_4,
                R.mipmap.p2_5});
        // 文学
        list_two.add(new String[]{"散文随笔","名家作品及欣赏","作品集","文学史","诗歌词曲","文学理论"});
        list_two_img.add(new Integer[]{R.mipmap.p3_1,R.mipmap.p3_2,R.mipmap.p3_3,R.mipmap.p3_4,
                R.mipmap.p3_5,R.mipmap.p3_6});
        // 小说
        list_two.add(new String[]{"推理小说","言情小说","外国现当代小说","世界名著","历史小说","武侠小说"});
        list_two_img.add(new Integer[]{R.mipmap.p4_1,R.mipmap.p4_2,R.mipmap.p4_3,R.mipmap.p4_4,
                R.mipmap.p4_5,R.mipmap.p4_6});
        // 历史
        list_two.add(new String[]{"中国史","文物考古","经典著作","地方史志","普及读物","世界总史"});
        list_two_img.add(new Integer[]{R.mipmap.p5_1,R.mipmap.p5_2,R.mipmap.p5_3,R.mipmap.p5_4,
                R.mipmap.p5_5,R.mipmap.p5_6});
        // 经管
        list_two.add(new String[]{"企业经营与经管","投资理财","经济学理论与读物","市场营销","管理学理论与方法论","会计审计"});
        list_two_img.add(new Integer[]{R.mipmap.p6_1,R.mipmap.p6_2,R.mipmap.p6_3,R.mipmap.p6_4,
                R.mipmap.p6_5,R.mipmap.p6_6});
        // 励志
        list_two.add(new String[]{"心灵读物","人机与社交","情商与情绪","性格与习惯","人在职场","成功学"});
        list_two_img.add(new Integer[]{R.mipmap.p7_1,R.mipmap.p7_2,R.mipmap.p7_3,R.mipmap.p7_4,
                R.mipmap.p7_5,R.mipmap.p7_6});
        // 人文社科
        list_two.add(new String[]{"历史","哲学与宗教","社会科学","文化","国学","法律","政治与军事"});
        list_two_img.add(new Integer[]{R.mipmap.p8_1,R.mipmap.p8_2,R.mipmap.p8_3,R.mipmap.p8_4,
                R.mipmap.p8_5,R.mipmap.p8_6,R.mipmap.p8_7});
        // 生活
        list_two.add(new String[]{"运动健身","烹饪饮食","旅游地图","健康与养生","家居手工","恋爱与婚姻","生活杂志"});
        list_two_img.add(new Integer[]{R.mipmap.p9_1,R.mipmap.p9_2,R.mipmap.p9_3,R.mipmap.p9_4,
                R.mipmap.p9_5,R.mipmap.p9_6,R.mipmap.p9_7});
        // 科普
        list_two.add(new String[]{"数学","天文学","地球科学","物理学","科普读物","航空与航天","力学"});
        list_two_img.add(new Integer[]{R.mipmap.p10_1,R.mipmap.p10_2,R.mipmap.p10_3,R.mipmap.p10_4,
                R.mipmap.p10_5,R.mipmap.p10_6,R.mipmap.p10_7});
        // 进口图书
        list_two.add(new String[]{"电影同名图书","美国亚马逊编辑推荐","纽约时报畅销图书榜"});
        list_two_img.add(new Integer[]{R.mipmap.p11_1,R.mipmap.p11_2,R.mipmap.p11_3});
        // 促销
        list_two.add(new String[]{"少儿","文学","进口原版","小说","历史","经济管理"});
        list_two_img.add(new Integer[]{R.mipmap.p12_1,R.mipmap.p12_2,R.mipmap.p12_3,R.mipmap.p12_4,
                R.mipmap.p12_5,R.mipmap.p12_6});
    }
}
