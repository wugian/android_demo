package com.wugian.mvpmodule.model;

import com.wugian.mvpmodule.model.bean.User;

/**
 * Created by 李攀 on 2016/8/24
 */
public class UserImpl implements IUser {

    @Override
    public void login(final String name, final String pwd, final OnLoginListener loginListener) {
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(3*1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if (name.endsWith("123")&&pwd.equals("123")) {
                    User user = new User();
                    user.setName(name);
                    user.setPwd(pwd);
                    loginListener.loginSuccess(user);
                }else{
                    loginListener.loginFailed();
                }
            }
        }.start();
    }

    public interface OnLoginListener {
        void loginSuccess(User user);
        void loginFailed();
    }

}
