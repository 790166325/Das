package com.example.administrator.day03.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by JSJ01 on 2017/5/7.
 */

public class Person extends BmobObject {
    private  String moo;
    private  String data;
    String createname;

    public String getCreatename() {
        return createname;
    }

    public void setCreatename(String createname) {
        this.createname = createname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public String getMoo() {
        return moo;
    }

    public void setMoo(String moo) {
        this.moo = moo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
