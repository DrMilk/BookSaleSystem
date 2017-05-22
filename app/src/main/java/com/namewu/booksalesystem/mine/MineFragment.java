package com.namewu.booksalesystem.mine;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.namewu.booksalesystem.R;
import com.namewu.booksalesystem.Utils.L;
import com.namewu.booksalesystem.Utils.MyUpload;
import com.namewu.booksalesystem.onlinedata.WZCLUser;

import cn.bmob.v3.BmobUser;

import static android.R.attr.name;
import static com.namewu.booksalesystem.R.array.sex;


/**
 * Created by Administrator on 2017/3/17.
 */

public class MineFragment extends Fragment implements View.OnClickListener{
    private String TAG="MineFragment";
    private TextView text_setting;
    private TextView text_personal;
    private TextView text_changepassword;
    private TextView text_collect;
    private TextView text_weather;
    private TextView text_location;
    private TextView text_about;
    private TextView text_name;
    private TextView text_id;
    private ImageView img_head;
    private MyUpload myUpload;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.activity_mine,null);
        text_changepassword= (TextView) view.findViewById(R.id.mine_change);
        text_setting= (TextView) view.findViewById(R.id.mine_setting);
        text_personal= (TextView) view.findViewById(R.id.mine_personal);
        text_collect= (TextView) view.findViewById(R.id.mine_collect);
        text_about= (TextView) view.findViewById(R.id.mine_about);
        text_name= (TextView) view.findViewById(R.id.maintab_name);
        text_id= (TextView) view.findViewById(R.id.maintab_id);
        img_head= (ImageView) view.findViewById(R.id.maintab_imghead);
        text_changepassword.setOnClickListener(this);
        text_collect.setOnClickListener(this);
        text_personal.setOnClickListener(this);
        text_setting.setOnClickListener(this);
        text_about.setOnClickListener(this);
        myUpload=new MyUpload(getActivity());
        return view;
    }

    @Override
    public void onResume() {
        checkuser();
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.mine_change:Intent it=new Intent(getActivity(),ChangePasswordActivity.class);startActivity(it);break;
//            case R.id.mine_collect:Intent it1=new Intent(getActivity(),CollectActivity.class);startActivity(it1);break;
            case R.id.mine_personal:Intent it2=new Intent(getActivity(),PersonaldActivity.class);startActivity(it2);break;
            case R.id.mine_setting:Intent it3=new Intent(getActivity(),SettingActivity.class);startActivity(it3);break;
//            case R.id.mine_weather:Intent it4=new Intent(getActivity(),WeatherActivity.class);startActivity(it4);break;
//            case R.id.mine_location:Intent it5=new Intent(getActivity(),DingweiMap.class);startActivity(it5);break;
//            case R.id.mine_about:Intent it6=new Intent(getActivity(),AboutActivity.class);startActivity(it6);break;
        }
    }
    private boolean checkuser() {
        WZCLUser bmobUser = BmobUser.getCurrentUser(WZCLUser.class);
        L.i(TAG, "到这步了吗1");
        if (bmobUser != null) {
            // 允许用户使用应用
            text_name.setText(bmobUser.getName());
            text_id.setText(bmobUser.getUsername());
            myUpload.download_asynchronous_head("booksalesystem", "headimg/" + bmobUser.getUsername(),img_head);
            return true;
        }
            return false;
    }
}
