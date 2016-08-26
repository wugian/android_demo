package com.wugian.mvpmodule.view;

/**
 * Created by 李攀 on 2016/8/24
 */
public interface IUserLoginView {
    void showProgress();
    void hideProgress();
    String getUserName();
    String getUserPwd();
    void clearName();
    void clearPWD();
    void successJump();
    void failureShow();
}
