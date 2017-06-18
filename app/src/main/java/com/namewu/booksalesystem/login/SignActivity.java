package com.namewu.booksalesystem.login;

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
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;


import com.namewu.booksalesystem.R;
import com.namewu.booksalesystem.Utils.BitmapUtil;
import com.namewu.booksalesystem.Utils.L;
import com.namewu.booksalesystem.Utils.MySdcard;
import com.namewu.booksalesystem.Utils.MyUpload;
import com.namewu.booksalesystem.Utils.StringLegalUtil;
import com.namewu.booksalesystem.Utils.T;
import com.namewu.booksalesystem.customView.XuProcessDialog;
import com.namewu.booksalesystem.onlinedata.WZCLUser;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.Date;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by Administrator on 2017/3/16.
 */

public class SignActivity extends Activity implements View.OnClickListener{
    private String TAG="SignActivity";
    private EditText edit_phonenum;
    private EditText edit_password;
    private EditText edit_password_again;
    private EditText edit_name;
    private EditText edit_email;
    private Spinner spinner_sex;
    private Button button_ok;
    private Button button_back;
    private String sex="男";
    private XuProcessDialog xu_dialog;
    private Context mcontext;
    private ImageView img_head;
    private MyUpload myUpload;
    private MySdcard mysdcard=new MySdcard();
    private Boolean imgheadstutus=false;
    private String tempUri;
    private String imgstr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        mcontext=this;
        myUpload=new MyUpload(mcontext);
        initView();
    }

    private void initView() {
        edit_phonenum= (EditText) findViewById(R.id.sign_phonenum);
        edit_password= (EditText) findViewById(R.id.sign_password);
        edit_password_again= (EditText) findViewById(R.id.sign_password_again);
        edit_name= (EditText) findViewById(R.id.sign_name);
        edit_email= (EditText) findViewById(R.id.sign_emial);
        spinner_sex= (Spinner) findViewById(R.id.sign_sex);
        button_ok= (Button) findViewById(R.id.button_ok);
        button_back= (Button) findViewById(R.id.button_back);
        spinner_sex= (Spinner) findViewById(R.id.sign_sex);
        img_head= (ImageView) findViewById(R.id.header_headimg);
        spinner_sex.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sex= (String) spinner_sex.getSelectedItem();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        button_ok.setOnClickListener(this);
        button_back.setOnClickListener(this);
        img_head.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_ok:gosign();break;
            case R.id.button_back:SignActivity.this.finish();break;
            case R.id.header_headimg:openpicture();break;
        }
    }
    private void openpicture() {
        Intent openAlbumIntent = new Intent(
                Intent.ACTION_PICK);
        openAlbumIntent.setType("image/*");
        startActivityForResult(openAlbumIntent, 1);
    }
    private void gosign() {
        boolean jundge_legal=true;
        button_ok.setEnabled(false);
        final String str_phonenum=edit_phonenum.getText().toString().trim();
        String str_password=edit_password.getText().toString().trim();
        String str_password_again=edit_password_again.getText().toString().trim();
        String str_name=edit_name.getText().toString().trim();
        String str_email=edit_email.getText().toString().trim();
        if(!StringLegalUtil.isHaveLength(str_phonenum)){
            edit_phonenum.setError("请输入手机号！");
            jundge_legal=false;
        }
        else if(!StringLegalUtil.isHaveLength(str_password)){
            edit_phonenum.setError("请输入密码！");
            jundge_legal=false;
        }
        else if(!StringLegalUtil.isHaveLength(str_password_again)){
            edit_phonenum.setError("请重复输入密码！");
            jundge_legal=false;
        }
        else if(!StringLegalUtil.isHaveLength(str_name)){
            edit_phonenum.setError("请输入昵称！");
            jundge_legal=false;
        }
        else if(!StringLegalUtil.isHaveLength(str_email)){
            edit_phonenum.setError("请输入邮箱！");
            jundge_legal=false;
        }
        else if(!StringLegalUtil.isCorrectPhonenum(str_phonenum)){
            edit_phonenum.setError("请输入正确的手机号！");
            jundge_legal=false;
        }
        else if(!str_password.equals(str_password_again)){
            edit_password.setError("两次密码输入不一样！");
            edit_password_again.setError("两次密码输入不一样！");
            jundge_legal=false;
        }else if(!StringLegalUtil.isCorrectEmail(str_email)){
            edit_email.setError("请输入正确的邮箱号号！");
            jundge_legal=false;
        }else if(!StringLegalUtil.isSafePassword(str_password)){
            edit_email.setError("输入密码过于简单！");
            jundge_legal=false;
        }
        if(jundge_legal){
            xu_dialog=new XuProcessDialog(this);xu_dialog.show();
            WZCLUser xuuser=new WZCLUser();
            xuuser.setUsername(str_phonenum);
            xuuser.setPassword(str_password);
            xuuser.setPasswordshow(str_password);
            xuuser.setName(str_name);
            xuuser.signUp(new SaveListener<WZCLUser>() {
                @Override
                public void done(WZCLUser o, BmobException e) {
                    if(e==null){
                        T.showShot(mcontext,"注册成功！");
                        L.i(TAG,"注册成功！");
                        if(myUpload.goUpload("booksalesystem","headimg/"+str_phonenum,mysdcard.getPathheadimg()+File.separator+imgstr)){
                            xu_dialog.dismiss();
                        }
                    }else {
                        T.showShot(mcontext,"注册失败！");
                        L.i(TAG,"注册失败！");
                        button_ok.setEnabled(true);
                    }
                    xu_dialog.dismiss();
                }
            });
        }else {
            button_ok.setEnabled(true);
        }
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
        cutFile = new File(mysdcard.pathheadimg+ File.separator+imgstr);
        Log.i("Wu",TAG+mysdcard.pathheadimg+ File.separator+imgstr);
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
        Bitmap photo=convertToBitmap(mysdcard.pathheadimg+File.separator+ imgstr,300,300);
        //   Bitmap bip=Bit
        //    photo = Utils.toRoundBitmap(photo, tempUri); // 这个时候的图片已经被处理成圆形的了
        //    img_head.setImageBitmap(photo);
        imgheadstutus=true;
        img_head.setImageBitmap(BitmapUtil.toRoundBitmap(photo));
        //uploadPic(photo);
        //  }
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
}
