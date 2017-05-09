package com.namewu.booksalesystem.shoppingcar;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.namewu.booksalesystem.R;

/**
 * Created by Administrator on 2017/5/9.
 */

public class ShoppingCarFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_shop,null);
        return view;
    }
}
