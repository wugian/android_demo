package com.wugian.mvpmodule.presenter;

import android.os.Handler;

import com.wugian.mvpmodule.model.IUser;
import com.wugian.mvpmodule.model.UserImpl;
import com.wugian.mvpmodule.model.bean.User;
import com.wugian.mvpmodule.view.IUserLoginView;

/**
 * Created by 李攀 on 2016/8/24
 */
public class UserLoginPresenter {
    private IUser userImpl;
    private IUserLoginView userLoginView;
    private Handler handler = new android.os.Handler();

    public UserLoginPresenter(IUserLoginView userLoginView){
        this.userLoginView  = userLoginView;
        this.userImpl = new UserImpl();
    }

    public void login(){
        userLoginView.showProgress();
        userImpl.login(userLoginView.getUserName(), userLoginView.getUserPwd(), new UserImpl.OnLoginListener() {
            @Override
            public void loginSuccess(User user) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        userLoginView.hideProgress();
                        userLoginView.successJump();
                    }
                });
            }

            @Override
            public void loginFailed() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        userLoginView.hideProgress();
                        userLoginView.failureShow();
                    }
                });
            }
        });
    }

    public void clear(){
        userLoginView.clearName();
        userLoginView.clearPWD();
    }
}
