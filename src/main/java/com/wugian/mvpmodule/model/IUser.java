package com.wugian.mvpmodule.model;

/**
 * Created by 李攀 on 2016/8/24.
 */
public interface IUser {
    public void login(String name,String pwd,UserImpl.OnLoginListener loginListener);
}
