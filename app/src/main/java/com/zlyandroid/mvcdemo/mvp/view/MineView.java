package com.zlyandroid.mvcdemo.mvp.view;

import com.zlyandroid.mvcdemo.bean.LoginBean;
import com.zlyandroid.mvcdemo.mvp.BaseView;

public interface MineView extends BaseView {
    @Override
    void showLoading();

    @Override
    void hideLoading();

    @Override
    void onError(String throwable);

    void onSuccess(LoginBean.DataBean bean);
}
