package com.namewu.booksalesystem.onlinedata;

import java.util.ArrayList;
import java.util.Date;

import cn.bmob.v3.BmobUser;


/**
 * Created by Administrator on 2016/11/29.
 */

public class WZCLUser extends BmobUser {
    private String sex;
    private String name;
    private String qq;
    private String passwordshow;
    private ArrayList<String> list_collect;
    public WZCLUser(){}

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPasswordshow() {
        return passwordshow;
    }

    public void setPasswordshow(String passwordshow) {
        this.passwordshow = passwordshow;
    }

    public ArrayList<String> getList_collect() {
        return list_collect;
    }

    public void setList_collect(ArrayList<String> list_collect) {
        this.list_collect = list_collect;
    }
}
