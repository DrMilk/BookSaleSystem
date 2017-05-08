package com.namewu.booksalesystem.Utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;


/**
 * Created by Administrator on 2017/2/22.
 */

public class AppUtil {
    private AppUtil() {
        throw new UnsupportedOperationException("AppUtil cannot instantiated");
    }

    /**
     * 获取app版本名
     */
    public static String getAppVersionName(Context context){
        PackageManager pm = context.getPackageManager();
        PackageInfo pi;
        try {
            pi = pm.getPackageInfo(context.getPackageName(),0);
            return pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取应用程序版本名称信息
     */
    public static String getVersionName(Context context)
    {
        try{
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    context.getPackageName(), 0);
            return packageInfo.versionName;
        }catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取app版本号
     */
    public static int getAppVersionCode(Context context){
        PackageManager pm = context.getPackageManager();
        PackageInfo pi;
        try {
            pi = pm.getPackageInfo(context.getPackageName(),0);
            return pi.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static Intent getshareMsgIntent(String activityTitle, String msgTitle, String msgText,
                                           String imgPath) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        if (imgPath == null || imgPath.equals("")) {
            intent.setType("text/plain"); // 纯文本
        } else {
            File f = new File(imgPath);
            if (f != null && f.exists() && f.isFile()) {
                intent.setType("image/png");
                Uri u = Uri.fromFile(f);
                intent.putExtra(Intent.EXTRA_STREAM, u);
            }
        }
        intent.putExtra(Intent.EXTRA_SUBJECT, msgTitle);
        intent.putExtra(Intent.EXTRA_TEXT, msgText);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //设置分享列表的标题，并且每次都显示分享列表
        return Intent.createChooser(intent, activityTitle);
    }
    public static Intent getShareMsgIntentTwo(String s){
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, s);
        shareIntent.setType("text/plain");
        return shareIntent;
    }
    //分享单张图片
    public Intent getShareSingleImageIntent(String imagePath) {
        //由文件得到uri
        Uri imageUri = Uri.fromFile(new File(imagePath));
        Log.d("share", "uri:" + imageUri);  //输出：file:///storage/emulated/0/test.jpg
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
        shareIntent.setType("image/*");
        return shareIntent;
    }
    //分享多张图片
    public Intent shareMultipleImage(ArrayList<String> list_uri) {
        ArrayList<Uri> uriList = new ArrayList<>();
        for(int i=0;i<list_uri.size();i++){
            uriList.add(Uri.fromFile(new File(list_uri.get(i))));
        }
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND_MULTIPLE);
        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uriList);
        shareIntent.setType("image/*");
        return shareIntent;
    }
}
