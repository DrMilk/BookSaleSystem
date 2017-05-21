package com.namewu.booksalesystem.mine;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.namewu.booksalesystem.R;
import com.namewu.booksalesystem.Utils.BitmapUtil;
import com.namewu.booksalesystem.Utils.L;
import com.namewu.booksalesystem.Utils.MySdcard;
import com.namewu.booksalesystem.Utils.MyUpload;
import com.namewu.booksalesystem.Utils.T;
import com.namewu.booksalesystem.customView.WangProcessDialog;
import com.namewu.booksalesystem.login.LoginActivity;
import com.namewu.booksalesystem.login.MainActivity;
import com.namewu.booksalesystem.onlinedata.WZCLUser;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;


/**
 * Created by Administrator on 2017/3/18.
 */

public class PersonaldActivity extends Activity implements View.OnClickListener {
    private String TAG = "PersonaldActivity";
    private WZCLUser xuuser;
    private String address;
    private String qq;
    private String wechat;
    private String email;
    private String name;
    private ImageView img_head;
    private EditText ed_emial;
    private EditText ed_name;
    private TextView text_user;
    private Button button_ok;
    private Button button_back;
    private Context mcontext;
    private WangProcessDialog xuprocessdialog;
    private MySdcard mySdcard=new MySdcard();
    private boolean imgheadstutus;
    private String tempUri;
    private String imgstr;
    private MyUpload myUpload;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        mcontext = this;
        myUpload=new MyUpload(mcontext);
        initview();
    }

    private void initview() {
        text_user = (TextView) findViewById(R.id.personal_phone);
        ed_emial = (EditText) findViewById(R.id.personal_email);
        ed_name = (EditText) findViewById(R.id.personal_user);
        img_head = (ImageView) findViewById(R.id.header_headimg);
        button_ok = (Button) findViewById(R.id.button_ok);
        button_back = (Button) findViewById(R.id.button_back);
        button_ok.setOnClickListener(this);
        button_back.setOnClickListener(this);
        img_head.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        if(xuuser==null){
            if (checkuser()) {
                L.i(TAG, "到这步了吗");
                text_user.setText(xuuser.getUsername());
                ed_name.setText(name);
                ed_emial.setText(xuuser.getEmail());
                myUpload.download_asynchronous_head("booksalesystem", "headimg/" + xuuser.getUsername(),img_head);
            }
        }
        super.onResume();
    }

    private boolean checkuser() {
        WZCLUser bmobUser = BmobUser.getCurrentUser(WZCLUser.class);
        L.i(TAG, "到这步了吗1");
        if (bmobUser != null) {
            // 允许用户使用应用
            xuuser = bmobUser;
            email = xuuser.getEmail();
            name = (String) BmobUser.getObjectByKey("name");
            qq = (String) BmobUser.getObjectByKey("qq");
            wechat = (String) BmobUser.getObjectByKey("wechat");
            address = (String) BmobUser.getObjectByKey("address");
            //  text_username.setText(name);
            return true;
        } else {
            //缓存用户对象为空时， 可打开用户注册界面…
            Intent it = new Intent(PersonaldActivity.this, LoginActivity.class);
            startActivity(it);
            PersonaldActivity.this.finish();
            return false;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button_ok:
                xuprocessdialog = new WangProcessDialog(mcontext);
                updatainfo();
                break;
            case R.id.button_back:
//                Intent it=new Intent(PersonaldActivity.this, MainActivity.class);
//                startActivity(it);
                PersonaldActivity.this.finish();
                break;
            case R.id.header_headimg:openpicture();break;
        }
    }

    private void updatainfo() {
        String namenew = ed_name.getText().toString().trim();
        String emailnew = ed_emial.getText().toString().trim();
        xuprocessdialog.show();
//        WangUser newUser = new WangUser();
        if (namenew!=null)
        if (!namenew.equals(name))
            xuuser.setName(namenew);
        if (!email.equals(emailnew))
            xuuser.setEmail(emailnew);
        xuuser.update(xuuser.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    T.showShot(mcontext, "更改成功！");
                    if(imgheadstutus){
                        L.i(TAG,"headimg/"+xuuser.getUsername()+"----"+mySdcard.getPathheadimg()+imgstr);
                        if(myUpload.goUpload("wangweibodata","headimg/"+xuuser.getUsername(),mySdcard.getPathheadimg()+File.separator+imgstr)){
                            xuprocessdialog.dismiss();
                        }
                        xuprocessdialog.dismiss();
                    }
                    xuprocessdialog.dismiss();
                } else {
                    T.showShot(mcontext, "更改失败！" + e.toString());
                    xuprocessdialog.dismiss();
                }
            }
        });
    }

    private void openpicture() {
        imgstr=xuuser.getUsername()+".jpg";
        File file=new File(mySdcard.getPathheadimg()+File.separator+imgstr);
        if(file.exists()){
            L.i(TAG,"存在"+mySdcard.getPathheadimg()+File.separator+imgstr);
            file.delete();
        }
        file=new File(mySdcard.pathCacheImage+File.separator+xuuser.getUsername());
        if(file.exists()){
            L.i(TAG,"存在"+mySdcard.pathCacheImage+File.separator+xuuser.getUsername());
            file.delete();
        }
        Intent openAlbumIntent = new Intent(
                Intent.ACTION_PICK);
        openAlbumIntent.setType("image/*");
        startActivityForResult(openAlbumIntent, 1);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("Wu", TAG + requestCode + "hahahah" + resultCode);
        if (resultCode != 0) {
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
            case 0:
                break;
            case 1:
                String sex = data.getStringExtra("sex");
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
        tempUri = getImageAbsolutePath(this, uri);
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
        cutFile = new File(mySdcard.pathheadimg + File.separator + imgstr);
        Log.i("Wu", TAG + mySdcard.pathheadimg + File.separator + imgstr);
        if (cutFile.exists() == false) {
            try {
                cutFile.createNewFile();
                L.i(TAG,"建了吧!"+cutFile.getAbsolutePath());
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else {
            cutFile.delete();
            try {
                cutFile.createNewFile();
                L.i(TAG,"真的重建了!"+cutFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 解决方法
        intent.putExtra("output", Uri.fromFile(cutFile));  // 指定目标文件
        intent.putExtra("outputFormat", "JPEG"); //输出文件格式

        startActivityForResult(intent, 2);
    }

    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        String column = MediaStore.Images.Media.DATA;
        String[] projection = {column};
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

    /**
     * 根据Uri获取图片绝对路径，解决Android4.4以上版本Uri转换
     *
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
                String[] selectionArgs = new String[]{split[1]};
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
        opts.inSampleSize = (int) scale;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        return Bitmap.createScaledBitmap(weak.get(), w, h, true);
    }

    /**
     * 保存裁剪之后的图片数据
     *
     * @param
     * @param
     */
    protected void setImageToView(Intent data) {
        //  Bundle extras = data.getExtras();
        // if (extras != null) {
        //      Bitmap photo = extras.getParcelable("data");
        Bitmap photo = convertToBitmap(mySdcard.pathheadimg + File.separator + imgstr, 300, 300);
        //   Bitmap bip=Bit
        //    photo = Utils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
        //    img_head.setImageBitmap(photo);
        imgheadstutus = true;
        img_head.setImageBitmap(BitmapUtil.toRoundBitmap(photo));
        //uploadPic(photo);
        //  }
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            Intent it=new Intent(PersonaldActivity.this, MainActivity.class);
            startActivity(it);
            this.finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}