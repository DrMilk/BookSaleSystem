package com.namewu.booksalesystem.onlinedata;

import java.util.ArrayList;
import java.util.HashMap;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/3/17.
 */

public class Orderdata extends BmobObject {
    private String name;
    private String sex;
    private String phone;
    private boolean status;
    private String address;
    private ArrayList<String> list_info;
    private String allmoney;
    private String doid;
    private String submitid;

    public Orderdata(String name, String sex, String phone, boolean status, String address,
                     ArrayList<String> list_info, String allmoney, String doid, String submitid) {
        this.name = name;
        this.sex = sex;
        this.phone = phone;
        this.status = status;
        this.address = address;
        this.list_info = list_info;
        this.allmoney = allmoney;
        this.doid = doid;
        this.submitid = submitid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public ArrayList<String> getList_info() {
        return list_info;
    }

    public void setList_info(ArrayList<String> list_info) {
        this.list_info = list_info;
    }

    public String getAllmoney() {
        return allmoney;
    }

    public void setAllmoney(String allmoney) {
        this.allmoney = allmoney;
    }

    public String getDoid() {
        return doid;
    }

    public void setDoid(String doid) {
        this.doid = doid;
    }

    public String getSubmitid() {
        return submitid;
    }

    public void setSubmitid(String submitid) {
        this.submitid = submitid;
    }
}
