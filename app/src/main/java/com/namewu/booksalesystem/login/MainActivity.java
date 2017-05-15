package com.namewu.booksalesystem.login;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.sunflower.FlowerCollector;
import com.namewu.booksalesystem.R;
import com.namewu.booksalesystem.Utils.L;
import com.namewu.booksalesystem.customView.XuVoiceDialog;
import com.namewu.booksalesystem.main.FirstTabFragment;
import com.namewu.booksalesystem.main.FirstTabFragmentProcess;
import com.namewu.booksalesystem.mine.MineFragment;
import com.namewu.booksalesystem.onlinedata.Alldata;
import com.namewu.booksalesystem.shoppingcar.ShoppingCarFragment;
import com.namewu.booksalesystem.species.KindFragment;
import com.namewu.booksalesystem.talk.TalkFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;

/**
 * Created by Administrator on 2017/5/9.
 */

public class MainActivity extends Activity implements View.OnClickListener {
    private String TAG = "MainActivity";
    private Context mcontext;
//    public Alldata alldata;
    private LinearLayout linearLayout_firstab;
    private LinearLayout linearLayout_travel;
    private LinearLayout linearLayout_food;
    private LinearLayout linearLayout_hotel;
    private LinearLayout linearLayout_mine;
    private Fragment fragment_firstab;
    private Fragment fragment_species;
    private Fragment fragment_talk;
    private Fragment fragment_shop;
    private Fragment fragment_mine;
    private FirstTabFragmentProcess fragment_process;
    private FragmentManager fm;
    private FragmentTransaction ft;
    private int tab_num = 0;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private ImageView img5;
    private SpeechRecognizer mIat;
    private SoundPool mtinksong;
    private HashMap<Integer, Integer> soundPoolMap;
    private XuVoiceDialog xuVoiceDialog;
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();
    private int count_speech = 0;
    private String key;
    private Alldata alldata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mcontext = this;
        initView();
        initFragment();
    }

    private void initView() {
        linearLayout_firstab = (LinearLayout) findViewById(R.id.activity_main_firsttab);
        linearLayout_travel = (LinearLayout) findViewById(R.id.activity_main_travel);
        linearLayout_food = (LinearLayout) findViewById(R.id.activity_main_food);
        linearLayout_hotel = (LinearLayout) findViewById(R.id.activity_main_hotel);
        linearLayout_mine = (LinearLayout) findViewById(R.id.activity_main_mine);
        linearLayout_firstab.setOnClickListener(this);
        linearLayout_food.setOnClickListener(this);
        linearLayout_hotel.setOnClickListener(this);
        linearLayout_mine.setOnClickListener(this);
        linearLayout_travel.setOnClickListener(this);
        img1 = (ImageView) findViewById(R.id.bottom_img1);
        img2 = (ImageView) findViewById(R.id.bottom_img2);
        img3 = (ImageView) findViewById(R.id.bottom_img3);
        img4 = (ImageView) findViewById(R.id.bottom_img4);
        img5 = (ImageView) findViewById(R.id.bottom_img5);
//        FloatingActionButton yuyin = (FloatingActionButton) findViewById(R.id.yuyin);
//        yuyin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        });
//        yuyin.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (event.getAction() == MotionEvent.ACTION_DOWN) {
//                    mIat.cancel();
////                    mtinksong.play(soundPoolMap.get(0),     //声音资源
////                            volumnRatio,         //左声道
////                            volumnRatio,         //右声道
////                            1,             //优先级，0最低
////                            0,         //循环次数，0是不循环，-1是永远循环
////                            1);            //回放速度，0.5-2.0之间。1为正常速度
////                };
//                    xuVoiceDialog = new XuVoiceDialog(mcontext);
//                    xuVoiceDialog.show();
//                    mtinksong.play(soundPoolMap.get(0), 1, 1, 1, 0, 1);
//                    //  img_yuyin.setVisibility(View.VISIBLE);
//                    Log.i("Wu", mIat.startListening(mRecognizerListener) + "");
//                    mIat.startListening(mRecognizerListener);
//                    Log.i("Wu", "按了");
//                }
//                if (event.getAction() == MotionEvent.ACTION_UP) {
//                    mIat.stopListening();
//                    //        char_list_baseadapter.notify();
//                    Log.i("Wu", "松开");
//                    xuVoiceDialog.dismiss();
//                    //anim_draw.stop();
//                    //    img_yuyin.setVisibility(View.INVISIBLE);
//                    mtinksong.play(soundPoolMap.get(4), 1, 1, 1, 0, 1);
//                }
//                return false;
//            }
//        });
    }

//    private void initFragment() {
//        fragment_process=new FirstTabFragmentProcess();
//        fragment_mine=new MineFragment();
//        fm=getFragmentManager();
//        ft=fm.beginTransaction();
//        if(fragment_firstab!=null)
//            ft.replace(R.id.activity_main_main,fragment_firstab);
//        else
//            ft.replace(R.id.activity_main_main,fragment_process);
//        ft.commit();
//        fragment_firstab = new FirstTabFragment();
//        fragment_species = new KindFragment();
//        fragment_talk = new TalkFragment();
//        fragment_shop = new ShoppingCarFragment();
//    }
    private void initFragment() {
        fragment_process = new FirstTabFragmentProcess();
        BmobQuery<Alldata> query = new BmobQuery<Alldata>();
        query.getObject("1dc6db1b23", new QueryListener<Alldata>() {

            @Override
            public void done(Alldata object, BmobException e) {
                if (e == null) {
                    alldata = object;
                    img1.setImageResource(R.mipmap.host_index_cate_icon_s);
                    fragment_firstab = new FirstTabFragment();
                    Bundle bundle = new Bundle();
                    bundle.putStringArrayList("data", alldata.getList_book());
                    fragment_firstab.setArguments(bundle);
                    fragment_talk=new TalkFragment();
                    bundle = new Bundle();
                    bundle.putStringArrayList("data", alldata.getList_talk());
                    fragment_talk.setArguments(bundle);
                    fragment_species=new KindFragment();
                    fragment_shop=new ShoppingCarFragment();
//                    fragment_food = new FoodFragment();
//                    bundle = new Bundle();
//                    bundle.putStringArrayList("data", alldata.getList_food());
//                    fragment_food.setArguments(bundle);
//                    fragment_travel = new TravelFragment();
//                    bundle = new Bundle();
//                    bundle.putStringArrayList("data", alldata.getList_travel());
//                    fragment_travel.setArguments(bundle);
                    updataChangeFragment();
                    L.i(TAG, "all" + "下载成功");
                } else {
                    L.i(TAG, "all" + "下载失败");
                }
            }

        });
        fragment_mine = new MineFragment();
        fm = getFragmentManager();
        ft = fm.beginTransaction();
        if (fragment_firstab != null)
            ft.replace(R.id.activity_main_main, fragment_firstab);
        else
            ft.replace(R.id.activity_main_main, fragment_process);
        ft.commit();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_main_firsttab:
                initBottomView();
                ft = fm.beginTransaction();
                tab_num = 0;
                if (fragment_firstab != null) {
                    L.i(TAG, "fragment_firstab");
                    ft.replace(R.id.activity_main_main, fragment_firstab);
                    L.i(TAG, "fragment_firstab");
                } else {
                    ft.replace(R.id.activity_main_main, fragment_process);
                    fragment_process.updataTitle("首页");
                }

                ft.commit();
                break;
            case R.id.activity_main_travel:
                initBottomView();
                img2.setImageResource(R.mipmap.host_index_cart_icon_s);
                ft = fm.beginTransaction();
                tab_num = 1;
                if (fragment_species != null) {
                    L.i(TAG, "fragment_travel");
                    ft.replace(R.id.activity_main_main, fragment_species);
                    L.i(TAG, "fragment_travel");
                } else {
                    ft.replace(R.id.activity_main_main, fragment_process);
                    fragment_process.updataTitle("分类");
                }
                ft.commit();
                break;
            case R.id.activity_main_food:
                initBottomView();
                img3.setImageResource(R.mipmap.host_index_cart_icon_s_food5);
                ft = fm.beginTransaction();
                tab_num = 2;
                L.i(TAG, "美食1");
                if (fragment_talk != null) {
                    L.i(TAG, "fragment_food");
                    ft.replace(R.id.activity_main_main, fragment_talk);
                    L.i(TAG, "fragment_food");
                } else {
                    ft.replace(R.id.activity_main_main, fragment_process);
                    L.i(TAG, "美食");
                    fragment_process.updataTitle("发现");
                }
                ft.commit();
                break;
            case R.id.activity_main_hotel:
                initBottomView();
                img4.setImageResource(R.mipmap.host_index_home_icon_s);
                ft = fm.beginTransaction();
                tab_num = 3;
                if (fragment_shop != null) {
                    L.i(TAG, "fragment_hotel");
                    ft.replace(R.id.activity_main_main, fragment_shop);
                    L.i(TAG, "fragment_hotel");
                } else {
                    ft.replace(R.id.activity_main_main, fragment_process);
                    fragment_process.updataTitle("购物车");
                }
                ft.commit();
                break;
            case R.id.activity_main_mine:
                initBottomView();
                img5.setImageResource(R.mipmap.host_index_mine_icon_s);
                ft = fm.beginTransaction();
                tab_num = 4;
                if (fragment_mine != null) {
                    L.i(TAG, "fragment_mine");
                    ft.replace(R.id.activity_main_main, fragment_mine);
                    L.i(TAG, "fragment_mine");
                } else
                    ft.replace(R.id.activity_main_main, fragment_process);
                ft.commit();
                break;
        }
    }

    private void initBottomView() {
        img2.setImageResource(R.mipmap.host_index_cart_icon);
        img1.setImageResource(R.mipmap.host_index_cate_icon);
        img3.setImageResource(R.mipmap.host_index_cart_icon_s_food5);
        img4.setImageResource(R.mipmap.host_index_home_icon);
        img5.setImageResource(R.mipmap.host_index_mine_icon);
    }

    public void updataChangeFragment() {
        switch (tab_num) {
            case 0:
                ft = fm.beginTransaction();
                tab_num = 0;
                if (fragment_firstab != null)
                    ft.replace(R.id.activity_main_main, fragment_firstab);
                else {
                    ft.replace(R.id.activity_main_main, fragment_process);
                    fragment_process.updataTitle("首页");
                }
                ft.commit();
                break;
            case 1:
                ft = fm.beginTransaction();
                tab_num = 1;
                if (fragment_species != null)
                    ft.replace(R.id.activity_main_main, fragment_species);
                else {
                    ft.replace(R.id.activity_main_main, fragment_process);
                    fragment_process.updataTitle("分类");
                }
                ft.commit();
                break;
            case 2:
                tab_num = 2;
                if (fragment_talk != null)
                    ft.replace(R.id.activity_main_main, fragment_talk);
                else {
                    ft.replace(R.id.activity_main_main, fragment_process);
                    fragment_process.updataTitle("发现");
                }
                ft.commit();
                break;
            case 3:
                ft = fm.beginTransaction();
                tab_num = 3;
                if (fragment_shop != null)
                    ft.replace(R.id.activity_main_main, fragment_shop);
                else {
                    ft.replace(R.id.activity_main_main, fragment_process);
                    fragment_process.updataTitle("购物车");
                }
                ft.commit();
                break;
            case 4:
                ft = fm.beginTransaction();
                tab_num = 4;
                if (fragment_mine != null)
                    ft.replace(R.id.activity_main_main, fragment_mine);
                else
                    ft.replace(R.id.activity_main_main, fragment_process);
                ft.commit();
                break;
        }
    }

}