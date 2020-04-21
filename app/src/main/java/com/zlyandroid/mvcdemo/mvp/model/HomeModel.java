package com.zlyandroid.mvcdemo.mvp.model;

import com.trello.rxlifecycle3.LifecycleTransformer;
import com.zlyandroid.mvcdemo.bean.LoginBean;
import com.zlyandroid.mvcdemo.mvp.contract.LoginContract;
import com.zlyandroid.mvcdemo.net.BaseObserver;
import com.zlyandroid.mvcdemo.net.RetrofitClient;
import com.zlyandroid.mvcdemo.net.RxScheduler;

public class HomeModel implements LoginContract.Model {


    @Override
    public void login(String username, String password, LifecycleTransformer bindToLife, BaseObserver<LoginBean.DataBean> scheduler) {
        RetrofitClient.getInstance().getApi().login(username,password).compose(RxScheduler.Obs_io_main())
                .compose(bindToLife)
                .subscribe(scheduler);
    }
}
