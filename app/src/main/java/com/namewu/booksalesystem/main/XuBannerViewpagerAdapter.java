package com.namewu.booksalesystem.main;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.namewu.booksalesystem.Utils.L;

import java.util.List;


/**
 * Created by Administrator on 2017/2/2.
 */

public class XuBannerViewpagerAdapter extends PagerAdapter {
    private String TAG="XuBannerViewpagerAdapter";
    private List<ImageView> list_img;
    public XuBannerViewpagerAdapter(List<ImageView> list_img){
        this.list_img=list_img;
    }
    @Override
    public int getCount() {return 600;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;//官方推荐写法
    }
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        L.i(TAG,"============");
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        position%=4;
        L.i(TAG,position+"");
        container.addView(list_img.get(position));
        return list_img.get(position);
    }

}
