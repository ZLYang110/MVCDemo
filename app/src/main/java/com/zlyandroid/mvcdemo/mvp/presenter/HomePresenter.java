package com.zlyandroid.mvcdemo.mvp.presenter;

import com.zlyandroid.mvcdemo.bean.LoginBean;
import com.zlyandroid.mvcdemo.mvp.BasePresenter;
import com.zlyandroid.mvcdemo.mvp.contract.HomeContract;
import com.zlyandroid.mvcdemo.mvp.contract.LoginContract;
import com.zlyandroid.mvcdemo.mvp.model.HomeModel;
import com.zlyandroid.mvcdemo.mvp.model.LoginModel;
import com.zlyandroid.mvcdemo.mvp.view.HomeView;
import com.zlyandroid.mvcdemo.mvp.view.LoginView;
import com.zlyandroid.mvcdemo.net.BaseObserver;
import com.zlyandroid.mvcdemo.util.log.ZLog;

public class HomePresenter extends BasePresenter<HomeView> implements HomeContract.Presenter{

    private LoginContract.Model model;

    public HomePresenter() {
        model = new HomeModel();
    }
}
