package com.zlyandroid.mvcdemo.mvp.presenter;

import com.zlyandroid.mvcdemo.bean.LoginBean;
import com.zlyandroid.mvcdemo.mvp.BasePresenter;
import com.zlyandroid.mvcdemo.mvp.contract.LoginContract;
import com.zlyandroid.mvcdemo.mvp.model.LoginModel;
import com.zlyandroid.mvcdemo.mvp.view.LoginView;
import com.zlyandroid.mvcdemo.net.BaseObserver;
import com.zlyandroid.mvcdemo.util.log.ZLog;

public class LoginPresenter extends BasePresenter<LoginView> implements LoginContract.Presenter{

    private LoginContract.Model model;

    public LoginPresenter() {
        model = new LoginModel();
    }

    @Override
    public void login(String username, String password) {
        //View是否绑定 如果没有绑定，就不执行网络请求
        if (!isViewAttached()) {
            return;
        }
        mView.showLoading();

        model.login(username, password, mView.bindToLife(),new BaseObserver<LoginBean.DataBean>() {
            @Override
            protected void onSuccees(LoginBean.DataBean loginBean) throws Exception {
                  mView.onSuccess(loginBean);
                ZLog.d("login--- "+loginBean.getNickname());
                mView.hideLoading();
            }

            @Override
            protected void onFailure(String error, boolean isNetWorkError) throws Exception {
                mView.onError(error);
                mView.hideLoading();
            }
        });

    }
}
