package com.namewu.booksalesystem.onlinedata;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/3/20.
 */

public class Alldata extends BmobObject {
    private ArrayList<String> list_book;
    private ArrayList<String> list_talk;
    public Alldata(){}

    public ArrayList<String> getList_book() {
        return list_book;
    }

    public void setList_book(ArrayList<String> list_book) {
        this.list_book = list_book;
    }

    public ArrayList<String> getList_talk() {
        return list_talk;
    }

    public void setList_talk(ArrayList<String> list_talk) {
        this.list_talk = list_talk;
    }
}
