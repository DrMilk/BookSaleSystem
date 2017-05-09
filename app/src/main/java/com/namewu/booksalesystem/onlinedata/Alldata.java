package com.namewu.booksalesystem.onlinedata;

import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/3/20.
 */

public class Alldata extends BmobObject {
    private ArrayList<String> list_spot;
    private ArrayList<String> list_travel;
    private ArrayList<String> list_food;
    private ArrayList<String> list_hotel;
    public Alldata(){}
    public ArrayList<String> getList_spot() {
        return list_spot;
    }

    public void setList_spot(ArrayList<String> list_spot) {
        this.list_spot = list_spot;
    }

    public ArrayList<String> getList_travel() {
        return list_travel;
    }

    public void setList_travel(ArrayList<String> list_travel) {
        this.list_travel = list_travel;
    }

    public ArrayList<String> getList_food() {
        return list_food;
    }

    public void setList_food(ArrayList<String> list_food) {
        this.list_food = list_food;
    }

    public ArrayList<String> getList_hotel() {
        return list_hotel;
    }

    public void setList_hotel(ArrayList<String> list_hotel) {
        this.list_hotel = list_hotel;
    }
}
