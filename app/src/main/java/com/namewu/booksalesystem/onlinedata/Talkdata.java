package com.namewu.booksalesystem.onlinedata;

import com.namewu.booksalesystem.Utils.L;

import java.io.Serializable;
import java.util.ArrayList;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/1/12.
 */

public class Talkdata extends BmobObject implements Serializable{
    private int num;
    private String context;
    private String writename;
    private int fakelevel;
    private ArrayList<String> list_remark;
    private String address;
    public Talkdata(){

    }
    public Talkdata(String writename, String context, int num, int fakelevel, String address) {
        this.writename=writename;
        this.context = context;
        this.num = num;
        this.fakelevel=fakelevel;
        this.address=address;
    }
    public int getFakelevel() {
        return fakelevel;
    }

    public void setFakelevel(int fakelevel) {
        this.fakelevel = fakelevel;
    }

    public String getWritename() {
        return writename;
    }

    public void setWritename(String writename) {
        this.writename = writename;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<String> getList_remark() {
        if(list_remark==null){
            list_remark=new ArrayList<>();
            L.i("wu","我就看看走没走这步");
        }
        return list_remark;
    }

    public void setList_remark(ArrayList<String> list_remark) {
        this.list_remark = list_remark;
    }
}
