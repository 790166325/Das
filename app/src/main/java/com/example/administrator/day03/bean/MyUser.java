package com.example.administrator.day03.bean;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/5/8.
 */

public class MyUser extends BmobUser {
    private Integer age;
    String UuName;

    public Boolean gender;
   public BmobFile image;

    public BmobFile getImage() {
        return image;
    }

    public void setImage(BmobFile image) {
        this.image = image;
    }

    public String getUuName() {
        return UuName;
    }

    public void setUuName(String uuName) {
        UuName = uuName;
    }

    private String nickname;

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
