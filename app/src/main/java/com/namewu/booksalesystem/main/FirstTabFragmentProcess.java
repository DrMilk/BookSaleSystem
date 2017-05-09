package com.namewu.booksalesystem.main;

import android.app.Fragment;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.namewu.booksalesystem.R;
import com.namewu.booksalesystem.Utils.L;


/**
 * Created by Administrator on 2017/3/20.
 */

public class FirstTabFragmentProcess extends Fragment{
    private String TAG="FirstTabFragmentProcess";
    private ImageView img_anim;
    private AnimationDrawable animationDrawable;
    private TextView text_title;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_process_spot,null);
        img_anim= (ImageView) view.findViewById(R.id.fragment_img_process);
        text_title= (TextView) view.findViewById(R.id.fragment_process_title);
         animationDrawable= (AnimationDrawable) img_anim.getDrawable();
        animationDrawable.start();
        L.i(TAG,"onCreateView");
        return view;
    }
    public void updataTitle(String s){
        text_title.setText(s);
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        L.i(TAG,"onAttach");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        L.i(TAG,"onCreate");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        L.i(TAG,"onActivityCreated");
    }

    @Override
    public void onStart() {
        super.onStart();
        L.i(TAG,"onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        L.i(TAG,"onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        L.i(TAG,"onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        L.i(TAG,"onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        L.i(TAG,"onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        L.i(TAG,"onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        L.i(TAG,"onDetach");
    }
}
