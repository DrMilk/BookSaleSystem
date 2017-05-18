package com.namewu.booksalesystem.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;


import com.namewu.booksalesystem.R;
import com.namewu.booksalesystem.Utils.AppUtil;
import com.namewu.booksalesystem.Utils.MySdcard;
import com.namewu.booksalesystem.Utils.SharePreferenceUtil;
import com.namewu.booksalesystem.Utils.T;
import com.namewu.booksalesystem.login.LoginActivity;
import com.namewu.booksalesystem.xustringparsing.MyStringPsrsing;

import java.io.File;

import cn.bmob.v3.BmobUser;

/**
 * Created by Administrator on 2017/3/18.
 */

public class SettingActivity extends Activity implements View.OnClickListener{
    private String TAG="SettingActivity";
    private TextView text_logou;
    private TextView text_checkout;
    private TextView text_share;
    private TextView text_cache;
    private Context mcontext;
    private TextView text_cache_num;
    private MySdcard wuSdcard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mcontext=this;
        setContentView(R.layout.activity_setting);
        initView();
    }

    private void initView() {
        text_logou= (TextView) findViewById(R.id.mine_setting_logout);
        text_checkout= (TextView) findViewById(R.id.mine_setting_check_version);
        text_share= (TextView) findViewById(R.id.mine_setting_share);
        text_cache= (TextView) findViewById(R.id.mine_setting_cache);
        text_cache_num= (TextView) findViewById(R.id.mine_setting_cache_num);
        wuSdcard=new MySdcard();
        text_cache_num.setText("( 共 "+ MyStringPsrsing.getFormatSize(wuSdcard.getFolderSize(new File(MySdcard.pathCache)))+")");
        text_logou.setOnClickListener(this);
        text_share.setOnClickListener(this);
        text_checkout.setOnClickListener(this);
        text_cache.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mine_setting_logout:
                SharePreferenceUtil.putSettingDataBoolean(mcontext,SharePreferenceUtil.AUTOLOGIN,false);
                BmobUser.logOut();
                Intent it=new Intent(SettingActivity.this, LoginActivity.class);startActivity(it);SettingActivity.this.finish();break;
            case R.id.mine_setting_check_version:
                T.showShot(mcontext,"没有发现新版本!");break;
            case R.id.mine_setting_share:
                startActivity(AppUtil.getShareMsgIntentTwo("乐途旅游 说走就走！"));break;
            case R.id.mine_setting_cache:
                if(wuSdcard.deleteFolderFile(MySdcard.pathCache,true))
                T.showShot(this.getApplicationContext(),"清理成功！");
                text_cache_num.setText("( 共 "+MyStringPsrsing.getFormatSize(wuSdcard.getFolderSize(new File(MySdcard.pathCache)))+")");break;
        }
    }
}
