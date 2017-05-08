package com.namewu.booksalesystem.customView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.namewu.booksalesystem.R;
import com.namewu.booksalesystem.Utils.T;


/**
 * Created by Administrator on 2017/2/22.
 */

public class PopupWindowShare extends PopupWindow implements View.OnClickListener{
    private String TAG="PopupWindowShare";
    private View more_menu;
    private View.OnClickListener onClickListener=this;
    private Context context;
    public PopupWindowShare(Context context, View.OnClickListener onClickListener, int width, int height){
        this.onClickListener=onClickListener;
        this.context=context;
        LayoutInflater inflater= LayoutInflater.from(context);
        more_menu=inflater.inflate(R.layout.popopwindow_share,null);
        // 设置弹出窗体可点击
        this.setTouchable(true);
        this.setFocusable(true);
        this.setContentView(more_menu);
        // 设置点击是否消失
        this.setOutsideTouchable(true);
        //设置弹出窗体动画效果
        this.setAnimationStyle(R.style.my_popup_anim);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
//        Log.i("Wu",mMenu.getHeight()+"");
        this.setHeight(height/2);
//        //实例化一个ColorDrawable颜色为半透明
//        ColorDrawable background = new ColorDrawable(0x4f000000);
//        //设置弹出窗体的背景
//        this.setBackgroundDrawable(background);
        intiViewClick();
    }
    public PopupWindowShare(Context context,int height) {
        this.onClickListener=onClickListener;
        this.context=context;
        LayoutInflater inflater= LayoutInflater.from(context);
        more_menu=inflater.inflate(R.layout.popopwindow_share,null);
        // 设置弹出窗体可点击
        this.setTouchable(true);
        this.setFocusable(true);
        this.setContentView(more_menu);
        // 设置点击是否消失
        this.setOutsideTouchable(true);
        //设置弹出窗体动画效果
        this.setAnimationStyle(R.style.my_popup_anim);
        this.setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
        //设置SelectPicPopupWindow弹出窗体的高
//        Log.i("Wu",mMenu.getHeight()+"");
        this.setHeight(height/2);
//        //实例化一个ColorDrawable颜色为半透明
//        ColorDrawable background = new ColorDrawable(0x4f000000);
//        //设置弹出窗体的背景
//        this.setBackgroundDrawable(background);
        intiViewClick();
    }
    private void intiViewClick(){
        ImageView imageView1= (ImageView) more_menu.findViewById(R.id.popupwindow_mms);
        ImageView imageView2= (ImageView) more_menu.findViewById(R.id.popupwindow_frinend_circle);
        ImageView imageView3= (ImageView) more_menu.findViewById(R.id.popupwindow_emial);
        ImageView imageView5= (ImageView) more_menu.findViewById(R.id.popupwindow_qq);
        ImageView imageView6= (ImageView) more_menu.findViewById(R.id.popupwindow_qzone);
        ImageView imageView9= (ImageView) more_menu.findViewById(R.id.popupwindow_weibo);
        ImageView imageView10= (ImageView) more_menu.findViewById(R.id.popupwindow_weixin);
        ImageView imageView11= (ImageView) more_menu.findViewById(R.id.popupwindow_zhifubao);
        ImageView imageView12= (ImageView) more_menu.findViewById(R.id.popupwindow_weixin_friend);
        LinearLayout linear= (LinearLayout) more_menu.findViewById(R.id.popopwindow_lineartop);
        imageView1.setOnClickListener(onClickListener);
        imageView2.setOnClickListener(onClickListener);
        imageView3.setOnClickListener(onClickListener);
        imageView5.setOnClickListener(onClickListener);
        imageView6.setOnClickListener(onClickListener);
        imageView9.setOnClickListener(onClickListener);
        imageView10.setOnClickListener(onClickListener);
        imageView11.setOnClickListener(onClickListener);
        imageView12.setOnClickListener(onClickListener);
        linear.setOnClickListener(onClickListener);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.popupwindow_frinend_circle:T.showShot(context,"- 暂未实现此接口 -");break;//生活圈
            case R.id.popupwindow_qq:T.showShot(context,"- 暂未实现此接口 -");break;//QQ
            case R.id.popupwindow_qzone:T.showShot(context,"- 暂未实现此接口 -");break;//QQ空间
            case R.id.popupwindow_weibo:T.showShot(context,"- 暂未实现此接口 -");break;//微博
            case R.id.popupwindow_weixin:T.showShot(context,"- 暂未实现此接口 -");break;//微信
            case R.id.popupwindow_weixin_friend:T.showShot(context,"- 暂未实现此接口 -");break;//朋友圈
            case R.id.popupwindow_zhifubao:T.showShot(context,"- 暂未实现此接口 -");break;//支付宝
            case R.id.popupwindow_emial:T.showShot(context,"- 暂未实现此接口 -");break;//短信
            case R.id.popupwindow_mms:T.showShot(context,"- 暂未实现此接口 -");break;//邮箱
            case R.id.popopwindow_lineartop:PopupWindowShare.this.dismiss();break;
        }
    }
}
