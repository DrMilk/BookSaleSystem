package com.namewu.booksalesystem.talk;


import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.namewu.booksalesystem.R;
import com.namewu.booksalesystem.Utils.L;
import com.namewu.booksalesystem.Utils.MySdcard;
import com.namewu.booksalesystem.Utils.MyUpload;
import com.namewu.booksalesystem.Utils.T;
import com.namewu.booksalesystem.customView.WuProcessDialogSecond;
import com.namewu.booksalesystem.login.LoginActivity;
import com.namewu.booksalesystem.login.MainActivity;
import com.namewu.booksalesystem.onlinedata.Talkdata;
import com.namewu.booksalesystem.onlinedata.WZCLUser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;


/**
 * Created by Administrator on 2016/11/29.
 */

public class Writetreememory extends Activity {
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    private AMapLocationClientOption mLocationOption=null;
    private String id;
    private String MyTAG="Writetreememory";
    private Context mcontext;
    private String TAG="Writetreememory////";
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView deleteimage1;
    private ImageView deleteimage2;
    private ImageView deleteimage3;
    private EditText edit_context;
    private Button button_ok;
    private String tempUri;
    private String imgstr;
    private MyUpload myUpload;
    private MySdcard mysdcard= MySdcard.getMysdcard();
    private String imgurl1=MySdcard.pathwriteimg+ File.separator+"img1.jpg";
    private String imgurl2=MySdcard.pathwriteimg+ File.separator+"img2.jpg";
    private String imgurl3=MySdcard.pathwriteimg+ File.separator+"img3.jpg";
    private int coutnimg=0;
    private int other_jundge=0;
    private int other_jundge1=0;
    private int mine_jundge=0;
    private int mine_jundge1=0;
    private int context_id=0;
    private int howpic_i=0;
    private Spinner mspinner;
    private int level=0;
    private ImageView img_level;
    private WuProcessDialogSecond wuProcessDialogSecond;
    private boolean isSuccessUploadOhter=false;
    private boolean isSuccessUploadMine=true;
    private ArrayList<String> list_context;
    private String objecid;
    private TextView text_address;
    //可以通过类implement方式实现AMapLocationListener接口，也可以通过创造接口类对象的方法实现
//以下为后者的举例：
    AMapLocationListener mLocationListener = new AMapLocationListener(){
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
//可在其中解析amapLocation获取相应内容。
//                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                    amapLocation.getLatitude();//获取纬度
//                    amapLocation.getLongitude();//获取经度
//                    amapLocation.getAccuracy();//获取精度信息
//                    amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                    amapLocation.getCountry();//国家信息
//                    amapLocation.getProvince();//省信息
//                    amapLocation.getCity();//城市信息
//                    amapLocation.getDistrict();//城区信息
//                    amapLocation.getStreet();//街道信息
//                    amapLocation.getStreetNum();//街道门牌号信息
//                    amapLocation.getCityCode();//城市编码
//                    amapLocation.getAdCode();//地区编码
//                    amapLocation.getAoiName();//获取当前定位点的AOI信息
//                    amapLocation.getBuildingId();//获取当前室内定位的建筑物Id
//                    amapLocation.getFloor();//获取当前室内定位的楼层
                    text_address.setText(amapLocation.getCity()+"."+amapLocation.getDistrict());
                 //   amapLocation.getGpsStatus();//获取GPS的当前状态
//获取定位时间
//                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    Date date = new Date(amapLocation.getTime());
//                    df.format(date);
                }else {
                    //定位失败时，可通过ErrCode（错误码）信息来确定失败的原因，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError","location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }

        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        mcontext=this;
        myUpload=new MyUpload(mcontext);
        restore();
        initview();
        initdatalocation();
    }

    private void restore() {
        File filepic1=new File(mysdcard.pathwriteimg+ File.separator+"img1.jpg");
        File filepic2=new File(mysdcard.pathwriteimg+ File.separator+"img2.jpg");
        File filepic3=new File(mysdcard.pathwriteimg+ File.separator+"img3.jpg");
        if(filepic1.exists()){
            filepic1.delete();
        }
        if(filepic2.exists()){
            filepic2.delete();
        }
        if(filepic3.exists()){
            filepic3.delete();
        }
    }

    private void initview() {
        mspinner= (Spinner) findViewById(R.id.write_spin);
        img_level= (ImageView) findViewById(R.id.write_level_pic);
        image1= (ImageView) findViewById(R.id.activity_write_img1);
        image2= (ImageView) findViewById(R.id.activity_write_img2);
        image3= (ImageView) findViewById(R.id.activity_write_img3);
        deleteimage1= (ImageView) findViewById(R.id.activity_write_deleteimg1);
        deleteimage2= (ImageView) findViewById(R.id.activity_write_deleteimg2);
        deleteimage3= (ImageView) findViewById(R.id.activity_write_deleteimg3);
        edit_context= (EditText) findViewById(R.id.activity_write_contextedit);
        text_address= (TextView) findViewById(R.id.activity_write_address);
        button_ok= (Button) findViewById(R.id.activity_write_ok);
        deleteimage1.setVisibility(View.INVISIBLE);
        deleteimage2.setVisibility(View.INVISIBLE);
        deleteimage3.setVisibility(View.INVISIBLE);
        image2.setVisibility(View.INVISIBLE);
        image3.setVisibility(View.INVISIBLE);
        mspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                level=position;
                TextView tv = (TextView)view;
                switch (position){
                    case 0:img_level.setImageResource(R.mipmap.ic_alert_purple);tv.setTextColor(getResources().getColor(R.color.purple_level));break;   //设置颜色break;
                    case 1:img_level.setImageResource(R.mipmap.ic_alert_red);tv.setTextColor(getResources().getColor(R.color.red_level));break;
                    case 2:img_level.setImageResource(R.mipmap.ic_alert_yellow);tv.setTextColor(getResources().getColor(R.color.yello_level));break;
                    case 3:img_level.setImageResource(R.mipmap.ic_alert_bule);tv.setTextColor(getResources().getColor(R.color.blue_level));break;
                    case 4:img_level.setImageResource(R.mipmap.ic_alert_green);tv.setTextColor(getResources().getColor(R.color.green_level));break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        image1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coutnimg=1;
                openpicture();
            }
        });
        image2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coutnimg=2;
                openpicture();
            }
        });
        image3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                coutnimg=3;
                openpicture();
            }
        });
        deleteimage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteimage(1);
            }
        });
        deleteimage2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteimage(2);
            }
        });
        deleteimage3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteimage(3);
            }
        });
        button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  myUpload.createbucket("<keymanword1>");
                wuProcessDialogSecond=new WuProcessDialogSecond(mcontext);
                wuProcessDialogSecond.setCanceledOnTouchOutside(false);
                wuProcessDialogSecond.show();
                goOk();
            }
        });
    }
    private boolean checkuser() {
        WZCLUser bmobUser = BmobUser.getCurrentUser(WZCLUser.class);
        if(bmobUser != null){
            // 允许用户使用应用
            id= bmobUser.getUsername();
            list_context=bmobUser.getList_mine();
            objecid= (String)bmobUser.getObjectId();
            //  text_username.setText(name);
            return true;
        }else{
            //缓存用户对象为空时， 可打开用户注册界面…
            Intent it=new Intent(Writetreememory.this, LoginActivity.class);
            startActivity(it);
            Writetreememory.this.finish();
            return false;
        }
    }
    private void goOk() {
        if(checkuser()){
            final String[] context_id_str = {"没有"};
            final String context=edit_context.getText().toString();
            myPicUpload_before();
            Talkdata wucontext=new Talkdata(id,context,howpic_i,level,text_address.getText().toString());
            wucontext.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if(e==null){
                        context_id_str[0] =s.toString();
                        other_jundge=1;
                        other_jundge1=1;
                        myPicUpload(s.toString());
                        otherupdata(id,context_id_str[0]);
                        Log.i(MyTAG,"上传文章成功"+s.toString());
                    }else{
                        other_jundge=2;
                        mine_jundge=2;
                        otherupdata(id,context_id_str[0]);
                    }
                }
            });
//            BmobQuery<Other> query = new BmobQuery<Other>();
////查询playerName叫“比目”的数据
//            query.addWhereEqualTo("id",id);
////返回50条数据，如果不加上这条语句，默认返回10条数据
//            // query.setLimit(1);
////执行查询方法
//            query.findObjects(new FindListener<Other>() {
//                @Override
//                public void done(List<Other> object, BmobException e) {
//                    if(e==null){
//                        // toast("查询成功：共"+object.size()+"条数据。");
//                        other_jundge1=1;
//                        mine_jundge1=1;
//                        other_get=object.get(0);
//                        otherupdata(id,context_id_str[0]);
//                        Log.i(MyTAG,"成功："+other_get.getContext_id());
////                    for (Other gameScore : object) {
////                        //获得playerName的信息
////                        //获得数据的objectId信息
////                       // gameScore.getObjectId();
////                        //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
////                        //gameScore.getCreatedAt();
////                        Log.i(MyTAG,"成功："+other_get.getContext_id());
////                    }
//                    }else{
//                        other_jundge1=2;
//                        mine_jundge1=2;
//                        otherupdata(id,context_id_str[0]);
//                        Log.i(MyTAG,"失败："+e.getMessage()+","+e.getErrorCode());
//                    }
//                }
//            });
//            BmobQuery<Mine> querymine = new BmobQuery<Mine>();
////查询playerName叫“比目”的数据
//            querymine.addWhereEqualTo("id",mineid);
////返回50条数据，如果不加上这条语句，默认返回10条数据
//            // query.setLimit(1);
////执行查询方法
//            querymine.findObjects(new FindListener<Mine>() {
//                @Override
//                public void done(List<Mine> object, BmobException e) {
//                    if(e==null){
//                        // toast("查询成功：共"+object.size()+"条数据。");
//                        mine_jundge1=1;
//                        mine_get=object.get(0);
//                        mineupdata(mineid,context_id_str[0]);
//                        Log.i(MyTAG,"成功："+mine_get.getContext_id());
////                    for (Other gameScore : object) {
////                        //获得playerName的信息
////                        //获得数据的objectId信息
////                       // gameScore.getObjectId();
////                        //获得createdAt数据创建时间（注意是：createdAt，不是createAt）
////                        //gameScore.getCreatedAt();
////                        Log.i(MyTAG,"成功："+other_get.getContext_id());
////                    }
//                    }else{
//                        mine_jundge1=2;
//                        mineupdata(mineid,context_id_str[0]);
//                        Log.i(MyTAG,"失败："+e.getMessage()+","+e.getErrorCode());
//                    }
//                }
//            });
        }
    }

    private void myPicUpload(String s) {
        switch (howpic_i){
            case 1:myUpload.goUpload("booksalesystem","context/"+s+"/"+"img1",mysdcard.pathwriteimg+ File.separator+"img1.jpg");break;
            case 2:myUpload.goUpload("booksalesystem","context/"+s+"/"+"img1",mysdcard.pathwriteimg+ File.separator+"img1.jpg");
                   myUpload.goUpload("booksalesystem","context/"+s+"/"+"img2",mysdcard.pathwriteimg+ File.separator+"img2.jpg");break;
            case 3:myUpload.goUpload("booksalesystem","context/"+s+"/"+"img1",mysdcard.pathwriteimg+ File.separator+"img1.jpg");
                   myUpload.goUpload("booksalesystem","context/"+s+"/"+"img2",mysdcard.pathwriteimg+ File.separator+"img2.jpg");
                   myUpload.goUpload("booksalesystem","context/"+s+"/"+"img3",mysdcard.pathwriteimg+ File.separator+"img3.jpg");;break;
            case 0:;break;
        }
    }
    private void myPicUpload_before(){
        boolean lock_jundge=true;
        File filepic1=new File(mysdcard.pathwriteimg+ File.separator+"img1.jpg");
        File filepic2=new File(mysdcard.pathwriteimg+ File.separator+"img2.jpg");
        File filepic3=new File(mysdcard.pathwriteimg+ File.separator+"img3.jpg");
        if(filepic3.exists()){
            howpic_i=3;
            lock_jundge=false;
        }
        if(lock_jundge==true&&filepic2.exists()){
            howpic_i=2;
            lock_jundge=false;
        }
        if(lock_jundge==true&&filepic1.exists()){
            howpic_i=1;
            lock_jundge=false;
        }
    }
    private void otherupdata(String id,String str) {
        if(other_jundge1==1&&other_jundge==1){
            Log.i(MyTAG,"-------------更新");
            WZCLUser wangUser=new WZCLUser();
            if(list_context==null){
                list_context=new ArrayList<String>();
            }
                list_context.add(str);
            wangUser.setList_mine(list_context);
            wangUser.update(objecid, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if(e==null){
                        isSuccessUploadOhter=true;
                        successUpload();
                        L.i(TAG,"更爱uesr成功");
                        T.showShot(mcontext,"上传成功！");
                    }else {
                        L.i(TAG,"更爱uesr失败"+e.toString());
                        failUpload();
                        T.showShot(mcontext,"上传失败！");
                    }
                }
            });
            //更新Person表里面id为6b6c11c537的数据，address内容更新为“北京朝阳”
//            Other pp = new Other();
//            pp.setValue("contextid_list",list_other);
//            pp.update(other_get.getObjectId(), new UpdateListener() {
//                @Override
//                public void done(BmobException e) {
//                    if(e==null){
//                        isSuccessUploadOhter=true;
//                        successUpload();
//                    }else{
//                        failUpload();
//                    }
//                }
//
//            });
        }
    }

    private void successUpload() {
        if(isSuccessUploadMine&&isSuccessUploadOhter) {
            wuProcessDialogSecond.dismiss();
            Toast.makeText(mcontext,"上传成功！",Toast.LENGTH_SHORT).show();
        }
    }
    private void failUpload() {
        if((!isSuccessUploadMine)&&(!isSuccessUploadOhter))
        {
            wuProcessDialogSecond.dismiss();
            Toast.makeText(mcontext,"上传失败！",Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteimage(int num){
        if(num==coutnimg){
            finaldelete();
        }else{
           if(coutnimg==2){
               beforedelete(1,num);
           }else if(coutnimg==3&&num==1){
               beforedelete(2,num);
           }else if(coutnimg==3&&num==2){
               beforedelete(3,num);
           }
        }
        coutnimg--;
    }

    private void finaldelete() {
        File imgfilefinal=new File(mysdcard.pathwriteimg+ File.separator+"img"+coutnimg+".jpg");
        if(imgfilefinal.exists())
            imgfilefinal.delete();
        switch (coutnimg){
            case 1:image1.setImageResource(R.mipmap.mall_comment_photos_add);deleteimage1.setVisibility(View.INVISIBLE);image2.setVisibility(View.INVISIBLE);break;
            case 2:image2.setImageResource(R.mipmap.mall_comment_photos_add);deleteimage2.setVisibility(View.INVISIBLE); image3.setVisibility(View.INVISIBLE);break;
            case 3:image3.setImageResource(R.mipmap.mall_comment_photos_add);deleteimage3.setVisibility(View.INVISIBLE);break;
        }
    }
    private void beforedelete(int i,int num) {
        File imgfilefinal=new File(mysdcard.pathwriteimg+ File.separator+"img"+num+".jpg");
        if(imgfilefinal.exists())
            imgfilefinal.delete();
        switch (i){
            case 1: File imgfilefinal1=new File(mysdcard.pathwriteimg+ File.separator+"img"+(num+1)+".jpg");
                Bitmap mbitmap=BitmapFactory.decodeFile(mysdcard.pathwriteimg+ File.separator+"img"+(num+1)+".jpg");
                image1.setImageBitmap(mbitmap);
                imgfilefinal1.renameTo(new File(mysdcard.pathwriteimg+ File.separator+"img"+num+".jpg"));break;
            case 2:File imgfilefinal2=new File(mysdcard.pathwriteimg+ File.separator+"img"+(num+1)+".jpg");
                Bitmap mbitmap2=BitmapFactory.decodeFile(mysdcard.pathwriteimg+ File.separator+"img"+(num+1)+".jpg");
                image1.setImageBitmap(mbitmap2);
                imgfilefinal2.renameTo(new File(mysdcard.pathwriteimg+ File.separator+"img"+num+".jpg"));
                imgfilefinal2=new File(mysdcard.pathwriteimg+ File.separator+"img"+(num+2)+".jpg");
                mbitmap2=BitmapFactory.decodeFile(mysdcard.pathwriteimg+ File.separator+"img"+(num+2)+".jpg");
                image2.setImageBitmap(mbitmap2);
                imgfilefinal2.renameTo(new File(mysdcard.pathwriteimg+ File.separator+"img"+(num+1)+".jpg"));break;
            case 3:File imgfilefinal3=new File(mysdcard.pathwriteimg+ File.separator+"img"+(num+1)+".jpg");
                Bitmap mbitmap3=BitmapFactory.decodeFile(mysdcard.pathwriteimg+ File.separator+"img"+(num+1)+".jpg");
                image2.setImageBitmap(mbitmap3);
                imgfilefinal3.renameTo(new File(mysdcard.pathwriteimg+ File.separator+"img"+num+".jpg"));break;
        }
        switch (coutnimg){
            case 1:image1.setImageResource(R.mipmap.mall_comment_photos_add);deleteimage1.setVisibility(View.INVISIBLE);image2.setVisibility(View.INVISIBLE);break;
            case 2:image2.setImageResource(R.mipmap.mall_comment_photos_add);deleteimage2.setVisibility(View.INVISIBLE); image3.setVisibility(View.INVISIBLE);break;
            case 3:image3.setImageResource(R.mipmap.mall_comment_photos_add);deleteimage3.setVisibility(View.INVISIBLE);break;
        }
    }

    private void setImgstr(){
        switch (coutnimg){
            case 1:imgstr="img1.jpg";break;
            case 2:imgstr="img2.jpg";break;
            case 3:imgstr="img3.jpg";break;
        }
    }
    private void openpicture() {
        setImgstr();
        Intent openAlbumIntent = new Intent(
                Intent.ACTION_PICK);
        openAlbumIntent.setType("image/*");
        startActivityForResult(openAlbumIntent, 1);
    }


    /**
     * 转换图片成圆形
     * @param bitmap 传入Bitmap对象
     * @return
     */
    public Bitmap toRoundBitmap(Bitmap bitmap)
    {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float roundPx;
        float left,top,right,bottom,dst_left,dst_top,dst_right,dst_bottom;
        if (width <= height) {
            roundPx = width / 2 -5;
            top = 0;
            bottom = width;
            left = 0;
            right = width;
            height = width;
            dst_left = 0;
            dst_top = 0;
            dst_right = width;
            dst_bottom = width;
        } else {
            roundPx = height / 2 -5;
            float clip = (width - height) / 2;
            left = clip;
            right = width - clip;
            top = 0;
            bottom = height;
            width = height;
            dst_left = 0;
            dst_top = 0;
            dst_right = height;
            dst_bottom = height;
        }

        Bitmap output = Bitmap.createBitmap(width,
                height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect src = new Rect((int)left, (int)top, (int)right, (int)bottom);
        final Rect dst = new Rect((int)dst_left, (int)dst_top, (int)dst_right, (int)dst_bottom);
        final RectF rectF = new RectF(dst_left+15, dst_top+15, dst_right-20, dst_bottom-20);

        paint.setAntiAlias(true);

        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);

        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, src, dst, paint);
        return output;
    }
    /**
     * 保存图片
     *
     * @param bitmap
     * @param fileName
     * @param baseFile
     */
    public static void saveBitmap(Bitmap bitmap, String fileName, File baseFile) {
        FileOutputStream bos = null;
        File imgFile = new File(baseFile, "/" + fileName);
        try {
            bos = new FileOutputStream(imgFile);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                bos.flush();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("Wu",TAG+requestCode+"hahahah"+resultCode);
        if(resultCode!=0) {
            switch (requestCode) {
                case 1:
                    if (data.getData() != null) {
                        startPhotoZoom(data.getData());
                    }// 开始对图片进行裁剪处理
                    break;
                case 2:
                    if (data != null) {
                        setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                    }
                    break;
                case 3:
                    String resultname = data.getStringExtra("result");
                    if (resultname != null)
                        break;
                case 4:
                    String resultsign = data.getStringExtra("result");
                    if (resultsign != null)
                        //  text_sign.setText(resultsign);
                        break;
            }
        }
        switch (resultCode) {
            case 0:break;
            case 1:
                String sex=data.getStringExtra("sex");
              //  text_sex.setText(sex);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    /**
     * 裁剪图片方法实现
     *
     * @param uri
     */

    protected void startPhotoZoom(Uri uri) {
        File cutFile;
        if (uri == null) {
            Log.i("Wu", "The uri is not exist.");
        }
        tempUri = getImageAbsolutePath(this,uri);
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("return-data", true);
        cutFile = new File(mysdcard.pathwriteimg+ File.separator+imgstr);
        Log.i("Wu",TAG+mysdcard.pathwriteimg+ File.separator+imgstr);
        if (cutFile.exists() == false) {
            try {
                cutFile.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // 解决方法
        intent.putExtra("output", Uri.fromFile(cutFile));  // 指定目标文件
        intent.putExtra("outputFormat", "JPEG"); //输出文件格式

        startActivityForResult(intent, 2);
    }
    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     *
     * @param
     */
    protected void setImageToView(Intent data) {
        //  Bundle extras = data.getExtras();
        // if (extras != null) {
        //      Bitmap photo = extras.getParcelable("data");
        Bitmap photo=convertToBitmap(mysdcard.pathwriteimg+File.separator+ imgstr,300,300);
        //   Bitmap bip=Bit
        //    photo = Utils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
    //    img_head.setImageBitmap(photo);
        switch (coutnimg){
            case 1:image1.setImageBitmap(photo);image2.setVisibility(View.VISIBLE);deleteimage1.setVisibility(View.VISIBLE);break;
            case 2:image2.setImageBitmap(photo);image3.setVisibility(View.VISIBLE);deleteimage2.setVisibility(View.VISIBLE);break;
            case 3:image3.setImageBitmap(photo);deleteimage3.setVisibility(View.VISIBLE);break;
        }
        //uploadPic(photo);
        //  }
    }
    private void uploadPic(Bitmap bitmap) {
        // 上传至服务器
        // ... 可以在这里把Bitmap转换成file，然后得到file的url，做文件上传操作
        // 注意这里得到的图片已经是圆形图片了
        // bitmap是没有做个圆形处理的，但已经被裁剪了

//        String imagePath = Utils.savePhoto(bitmap, Environment
//                .getExternalStorageDirectory().getAbsolutePath(), String
//                .valueOf(System.currentTimeMillis()));
//        Log.e("imagePath", imagePath+"");
//        if(imagePath != null){
//            // 拿着imagePath上传了
//            // ...
//        }
    }
    public Bitmap convertToBitmap(String path, int w, int h) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        float scaleWidth = 0.f, scaleHeight = 0.f;
        if (width > w || height > h) {
            // 缩放
            scaleWidth = ((float) width) / w;
            scaleHeight = ((float) height) / h;
        }
        opts.inJustDecodeBounds = false;
        float scale = Math.max(scaleWidth, scaleHeight);
        opts.inSampleSize = (int)scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }
    /**
     * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
     * @param
     * @param imageUri
     * @author yaoxing
     * @date 2014-10-12
     */
    @TargetApi(19)
    public static String getImageAbsolutePath(Activity context, Uri imageUri) {
        if (context == null || imageUri == null)
            return null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
            if (isExternalStorageDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(imageUri)) {
                String id = DocumentsContract.getDocumentId(imageUri);
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(imageUri)) {
                String docId = DocumentsContract.getDocumentId(imageUri);
                String[] split = docId.split(":");
                String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = new String[] { split[1] };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } // MediaStore (and general)
        else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
            // Return the remote address
            if (isGooglePhotosUri(imageUri))
                return imageUri.getLastPathSegment();
            return getDataColumn(context, imageUri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
            return imageUri.getPath();
        }
        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    private void initdatalocation() {
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
//设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Battery_Saving，低功耗模式。
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
        //获取一次定位结果：
//该方法默认为false。
        mLocationOption.setOnceLocation(true);

//获取最近3s内精度最高的一次定位结果：
//设置setOnceLocationLatest(boolean b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean b)接口也会被设置为true，反之不会，默认为false。
        mLocationOption.setOnceLocationLatest(true);
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption.setHttpTimeOut(20000);
        //关闭缓存机制
        mLocationOption.setLocationCacheEnable(false);
//给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
//启动定位
        mLocationClient.startLocation();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
//            Intent it=new Intent(Writetreememory.this, MainActivity.class);
//            startActivity(it);
//            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
