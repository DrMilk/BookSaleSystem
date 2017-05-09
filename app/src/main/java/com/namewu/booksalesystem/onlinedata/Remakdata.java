package com.namewu.booksalesystem.onlinedata;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/3/19.
 */

public class Remakdata extends BmobObject {
    private String context;
    private String id;

    public Remakdata(String context, String id) {
        this.context = context;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
