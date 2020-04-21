package com.zlyandroid.mvcdemo.base;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


import com.trello.rxlifecycle3.LifecycleTransformer;
import com.trello.rxlifecycle3.android.ActivityEvent;
import com.trello.rxlifecycle3.android.FragmentEvent;
import com.zlyandroid.mvcdemo.mvp.BasePresenter;
import com.zlyandroid.mvcdemo.mvp.BaseView;

public abstract class BaseMvpFragment<T extends BasePresenter> extends BaseFragment implements BaseView {

    protected T mPresenter;

    protected View rootView;// 缓存Fragment view

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        initPresenter();
        super.onCreate(savedInstanceState);
    }

    // 初始化Presenter
    private void initPresenter() {
        if (mPresenter == null) {
            mPresenter = createPresenter();
        }
        mPresenter.attachView(this);
    }
    protected abstract T createPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
       // RefWatcher refWatcher = ProApplication.getRefWatcher(getActivity());//1
        //refWatcher.watch(this);
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }
   /* @Override
    public <T> LifecycleTransformer<T> bindToLife() {
        return bindUntilEvent(FragmentEvent.DESTROY);
    }*/
   @Override
   public <T> LifecycleTransformer<T> bindToLife() {
       return bindUntilEvent(FragmentEvent.DESTROY);
   }
}
