package com.zlyandroid.mvcdemo.mvp.contract;

import com.trello.rxlifecycle3.LifecycleTransformer;
import com.zlyandroid.mvcdemo.bean.LoginBean;
import com.zlyandroid.mvcdemo.mvp.BaseView;
import com.zlyandroid.mvcdemo.net.BaseObserver;

public interface LoginContract {

    interface Model {
        void login(String username, String password, LifecycleTransformer bindToLife, BaseObserver<LoginBean.DataBean> scheduler);

        //void getList(String city, Integer sn,Integer pn,OnLoadDatasListener<LoginBean> onLoadDatasListener);
    }

    interface Presenter {
        /**
         * 登陆
         *
         * @param username
         * @param password
         */
        void login(String username, String password);
        /**
         * 测试
         */
        //  void getList(String city, Integer sn,Integer pn);
    }
}
