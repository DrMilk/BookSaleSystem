package com.namewu.booksalesystem.mine;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.namewu.booksalesystem.R;
import com.namewu.booksalesystem.Utils.L;
import com.namewu.booksalesystem.Utils.T;
import com.namewu.booksalesystem.customView.XuProcessDialog;
import com.namewu.booksalesystem.login.LoginActivity;
import com.namewu.booksalesystem.onlinedata.WZCLUser;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by Administrator on 2017/3/18.
 */

public class ChangePasswordActivity extends Activity implements View.OnClickListener{
    private XuProcessDialog xuProcessDialog;
    private String TAG="ChangePasswordActivity";
    private TextView textView_user;
    private EditText editText_password;
    private EditText editText_passwordnew;
    private EditText editText_passwordnewagain;
    private BmobUser xuuser;
    private Button button_back;
    private Button button_on;
    private Context mcontext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changemima);
        mcontext=this;
        initView();
    }

    private void initView() {
        textView_user= (TextView) findViewById(R.id.changemima_user);
        editText_password= (EditText) findViewById(R.id.changemima_nowpassword);
        editText_passwordnew= (EditText) findViewById(R.id.changemima_newpassword);
        editText_passwordnewagain= (EditText) findViewById(R.id.changemima_newpassword_again);
        button_on= (Button) findViewById(R.id.button_ok);
        button_back= (Button) findViewById(R.id.button_back);
        button_on.setOnClickListener(this);
        button_back.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        if(checkuser()){
            L.i(TAG,"更新了吗");
        textView_user.setText(xuuser.getUsername());
        }
        super.onResume();
    }
    private boolean checkuser() {
        WZCLUser bmobUser = BmobUser.getCurrentUser(WZCLUser.class);
        if(bmobUser != null){
            // 允许用户使用应用
              xuuser= BmobUser.getCurrentUser();

            //  text_username.setText(name);
            return true;
        }else{
            //缓存用户对象为空时， 可打开用户注册界面…
            Intent it=new Intent(ChangePasswordActivity.this, LoginActivity.class);
            startActivity(it);
            ChangePasswordActivity.this.finish();
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_ok:goupload();break;
            case R.id.button_back:ChangePasswordActivity.this.finish();break;
        }
    }

    private void goupload() {
        boolean jundge=true;
        String passwordold=editText_password.getText().toString().trim();
        String password=editText_passwordnew.getText().toString().trim();
        String passwordagain=editText_passwordnewagain.getText().toString().trim();
        if(password!=passwordagain&&password==null){
            editText_passwordnew.setError("密码输入不一致！");
            jundge=false;
        }
        if(password==null){
            editText_passwordnew.setError("请输入更改密码！");
            jundge=false;
        }
        if(passwordold==null){
            editText_password.setError("请输入密码！");
            jundge=false;
        }
        xuProcessDialog=new XuProcessDialog(mcontext);
        xuProcessDialog.show();
        if(jundge){
            BmobUser.updateCurrentUserPassword(passwordold,password, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        T.showShot(mcontext,"更改成功！");
                    }else{
                        T.showShot(mcontext,"更改失败！");
                    }
                    xuProcessDialog.dismiss();
                }

            });
        }
    }
}
