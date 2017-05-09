package com.namewu.booksalesystem.species;

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

public class KindFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_species,null);
        return view;
    }
}
