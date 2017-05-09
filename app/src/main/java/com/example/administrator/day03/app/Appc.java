package com.example.administrator.day03.app;

import android.app.Application;

import com.example.administrator.day03.bean.MyUser;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;

/**
 * Created by JSJ01 on 2017/5/7.
 */

public class Appc extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Bmob.initialize(this,"9f62acb83f5a84b1a1a8058df6e3b318");

    }
}
