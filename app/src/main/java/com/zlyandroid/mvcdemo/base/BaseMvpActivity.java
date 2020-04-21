package com.zlyandroid.mvcdemo.base;

import android.os.Bundle;

import androidx.lifecycle.Lifecycle;

import com.trello.rxlifecycle3.LifecycleTransformer;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.zlyandroid.mvcdemo.mvp.BasePresenter;
import com.zlyandroid.mvcdemo.mvp.BaseView;

public abstract class BaseMvpActivity<T extends BasePresenter> extends BaseActivity implements BaseView {
    protected T mPresenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initPresenter();
    }
    // 初始化Presenter
    private void initPresenter() {
        if (mPresenter == null) {
            mPresenter = createPresenter();
            if (mPresenter != null) {
                mPresenter.attachView(this);
            }

        }

    }
    protected abstract T createPresenter();

    @Override
    protected void onDestroy() {
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        super.onDestroy();
    }

    @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindUntilEvent(ActivityEvent.DESTROY);
    }

}
