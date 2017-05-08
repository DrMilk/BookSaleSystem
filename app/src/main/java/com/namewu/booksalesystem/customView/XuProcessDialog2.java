package com.namewu.booksalesystem.customView;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;

import com.namewu.booksalesystem.R;


/**
 * Created by Administrator on 2017/1/17.
 */

public class XuProcessDialog2 extends Dialog{
    private AnimationDrawable anim_process;
    private ImageView anim_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.process_dialog2);
        anim_img= (ImageView) findViewById(R.id.process_img2);
        anim_process= (AnimationDrawable) anim_img.getDrawable();
        anim_process.start();
    }

    public XuProcessDialog2(Context context) {
        super(context,R.style.MyDialog);
    }

    public XuProcessDialog2(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected XuProcessDialog2(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }
}
