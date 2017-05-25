package com.namewu.booksalesystem.shoppingcar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.namewu.booksalesystem.R;
import com.namewu.booksalesystem.Utils.L;
import com.namewu.booksalesystem.Utils.MySdcard;
import com.namewu.booksalesystem.Utils.StringLegalUtil;
import com.namewu.booksalesystem.Utils.T;
import com.namewu.booksalesystem.login.LoginActivity;
import com.namewu.booksalesystem.onlinedata.Orderdata;
import com.namewu.booksalesystem.onlinedata.WZCLUser;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/5/22.
 */

public class OrderBuy extends Activity implements View.OnClickListener{
    private String TAG="OrderBuy";
    private Context mcontext;
    private ArrayList<String> data_name;
    private ArrayList<Integer> data_preice;
    private ArrayList<Integer> data_count;
    private ArrayList<String> data_address;
    private LayoutInflater inflater;
    private int allmoney;
    private ListView listview;
    private TextView all;
    private OrderListAdapter orderadapter;
    private TextView text_address_change;
    private TextView text_ok;
    private TextView text_name;
    private TextView text_sex;
    private TextView text_phone;
    private TextView text_address;
    private WZCLUser wzclUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderbuy);
        mcontext=this;
        inflater=LayoutInflater.from(mcontext);
        recivedata();
        initView();
    }

    private void initView() {
        listview= (ListView) findViewById(R.id.activity_orderbuy_listview);
        all= (TextView) findViewById(R.id.activity_orderbuy_allmoney);
        text_address_change= (TextView) findViewById(R.id.activity_orderbuy_add);
        text_name= (TextView) findViewById(R.id.activity_orderbuy_oname);
        text_sex= (TextView) findViewById(R.id.activity_orderbuy_osir);
        text_phone= (TextView) findViewById(R.id.activity_orderbuy_ophonenum);
        text_address= (TextView) findViewById(R.id.activity_orderbuy_oaddress);
        text_ok= (TextView) findViewById(R.id.activity_orderbuy_ok);
        orderadapter=new OrderListAdapter(mcontext,data_name,data_preice,data_count);
        listview.setAdapter(orderadapter);
        all.setText("￥ "+allmoney+".00");
        text_address_change.setOnClickListener(this);
        text_ok.setOnClickListener(this);
    }

    private void recivedata() {
        Intent it=getIntent();
        Bundle bundle=it.getExtras();
        data_name=bundle.getStringArrayList("name");
        data_preice=bundle.getIntegerArrayList("price");
        data_count=bundle.getIntegerArrayList("count");
        data_address=bundle.getStringArrayList("address");
        allmoney=bundle.getInt("allmoney");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_orderbuy_add:
                View view=inflater.inflate(R.layout.addressdialog,null);
                final String[] sex = {"先生"};
                final EditText et_name= (EditText) view.findViewById(R.id.addressdialog_name);
                final EditText et_phone= (EditText) view.findViewById(R.id.addressdialog_phone);
                final EditText et_privince= (EditText) view.findViewById(R.id.addressdialog_privince);
                final EditText et_city= (EditText) view.findViewById(R.id.addressdialog_city);
                final EditText et_region= (EditText) view.findViewById(R.id.addressdialog_region);
                final EditText et_detailaddress= (EditText) view.findViewById(R.id.addressdialog_detailaddress);
                RadioGroup rg= (RadioGroup) view.findViewById(R.id.radio_grounp);
                rg.check(R.id.radio_man);
                rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        switch (checkedId){
                            case R.id.radio_man:
                                sex[0] ="先生";break;
                            case R.id.radio_woman:
                                sex[0] ="女士";break;
                        }
                    }
                });
                AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
                builder.setTitle("地址").setIcon(R.mipmap.syncing_2).setView(view)
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                boolean jundge=true;
                                String name=et_name.getText().toString().trim();
                                String phone=et_phone.getText().toString().trim();
                                String privince=et_privince.getText().toString().trim();
                                String city=et_city.getText().toString().trim();
                                String region=et_region.getText().toString().trim();
                                String detailaddress=et_detailaddress.getText().toString().trim();
                                if(!StringLegalUtil.isHaveLength(name)){
                                    et_name.setError("名字不能为空！");
                                    jundge=false;
                                }else if(!StringLegalUtil.isHaveLength(phone)&&!StringLegalUtil.isCorrectPhonenum(phone)){
                                    et_phone.setError("请输入正确的手机号！");
                                    jundge=false;
                                }else if(!StringLegalUtil.isHaveLength(privince)){
                                    et_privince.setError("省份不能为空！");
                                    jundge=false;
                                }else if(!StringLegalUtil.isHaveLength(city)){
                                    et_city.setError("市不能为空！");
                                    jundge=false;
                                }else if(!StringLegalUtil.isHaveLength(region)){
                                    et_region.setError("区不能为空！");
                                    jundge=false;
                                }else if(!StringLegalUtil.isHaveLength(detailaddress)){
                                    et_detailaddress.setError("详细地址不能为空！");
                                    jundge=false;
                                }
                                if(jundge){
                                    text_name.setText(name);
                                    text_phone.setText(phone);
                                    text_sex.setText(sex[0]);
                                    StringBuffer sb=new StringBuffer("地址:  ");
                                    sb.append(privince+"省 ").append(city+"市 ").append(region+"区 ").append(detailaddress+"。");
                                    text_address.setText(sb);
                                    L.i(TAG,"更新了"+text_name+text_phone+text_sex+sb);
                                    File file=new File(MySdcard.pathsearchtxt+File.separator+"address.txt");
                                    FileOutputStream fos= null;
                                    try {
                                        fos = new FileOutputStream(file);
                                        ObjectOutputStream oos=new ObjectOutputStream(fos);
                                        HashMap<String,String> map=new HashMap<String, String>();
                                        map.put("name",name);
                                        map.put("phone",phone);
                                        map.put("sex",sex[0]);
                                        map.put("privince",privince);
                                        map.put("city",city);
                                        map.put("region",region);
                                        map.put("detailaddress",detailaddress);
                                        oos.writeObject(map);
                                        oos.flush();
                                        oos.close();
                                        fos.close();
                                        T.showShot(mcontext,"更改完毕");
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }else {
                                    T.showShot(mcontext,"非法字符输入 请重新输入！");
                                }
                            }
                        });
                builder.show();
                ;break;
            case R.id.activity_orderbuy_ok:
                String name=text_name.getText().toString();
                String address=text_address.getText().toString();
                String _sex=text_sex.getText().toString();
                String phone=text_phone.getText().toString();
                ArrayList<String> order_book=new ArrayList<>();
                for(int i=0;i<data_name.size();i++){
                    StringBuffer sb=new StringBuffer(data_name.get(i));
                    sb.append("*"+data_preice.get(i)).append("*"+data_count.get(i))
                    .append("*"+data_count.get(i)).append("*"+data_address.get(i));
                    order_book.add(sb.toString());
                }
                if(StringLegalUtil.isHaveLength(name)&&StringLegalUtil.isHaveLength(address)
                        &&StringLegalUtil.isHaveLength(_sex)&&StringLegalUtil.isHaveLength(phone)){
                    Orderdata orderdata=new Orderdata(name,_sex,phone,false,address,order_book,all.
                            getText().toString(),"0",wzclUser.getUsername());
                    orderdata.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if(e==null){
                                T.showShot(mcontext,"提交完毕");
                            }else {
                                T.showShot(mcontext,"提交失败");
                            }
                        }
                    });
                }else {
                    T.showShot(mcontext,"信息有误！请重新填写");
                }
               break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        File file=new File(MySdcard.pathsearchtxt+File.separator+"address.txt");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(file.length()!=0){
            FileInputStream fis= null;
            try {
                fis = new FileInputStream(file);
                ObjectInputStream ois=new ObjectInputStream(fis);
                HashMap<String,String> map= (HashMap<String, String>) ois.readObject();
                ois.close();
                fis.close();
                String name= map.get("name");
                String phone=map.get("phone");
                String sex=map.get("sex");
                String privince= map.get("privince");
                String city=map.get("city");
                String region=map.get("region");
                String detailaddress=map.get("detailaddress");
                text_name.setText(name);
                text_phone.setText(phone);
                text_sex.setText(sex);
                StringBuffer sb=new StringBuffer("地址:  ");
                sb.append(privince+"省 ").append(city+"市 ").append(region+"区 ").append(detailaddress+"。");
                text_address.setText(sb);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(checkuser()){

        }
    }
    private boolean checkuser() {
        wzclUser = BmobUser.getCurrentUser(WZCLUser.class);
        if(wzclUser != null){
            // 允许用户使用应用
            //  String name= (String) BmobUser.getObjectByKey("treename");
            //  text_username.setText(name);
            return true;
        }else{
            //缓存用户对象为空时， 可打开用户注册界面…
            Intent it=new Intent(OrderBuy.this, LoginActivity.class);
            startActivity(it);
            return false;
        }
    }
}
