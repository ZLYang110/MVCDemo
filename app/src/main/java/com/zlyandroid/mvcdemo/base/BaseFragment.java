package com.zlyandroid.mvcdemo.base;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.trello.rxlifecycle3.components.support.RxFragment;
import com.zlyandroid.mvcdemo.R;
import com.zlyandroid.mvcdemo.interfaces.FragmentPresenter;
import com.zlyandroid.mvcdemo.util.DialogUtils;
import com.zlyandroid.mvcdemo.util.log.ZLog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseFragment extends RxFragment implements FragmentPresenter {
    private static final String TAG = "BaseFragment";
    /**
     * 添加该Fragment的Activity
     * @warn 不能在子类中创建
     */
    protected BaseActivity context = null;
    /**
     * 该Fragment全局视图
     * @must 非abstract子类的onCreateView中return view;
     * @warn 不能在子类中创建
     */
    protected View mRootView = null;

    private boolean isAlive = false;
    private boolean isRunning = false;

    protected Unbinder unbinder;
    protected Dialog dialog;
    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){

        if (mRootView == null) {
            mRootView = inflater.inflate(getLayoutID(),container,false);
            unbinder = ButterKnife.bind(this, mRootView);
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        context = (BaseActivity) getActivity();
        isAlive = true;

        dialog = DialogUtils.createLoadingDialog(getActivity(), "请稍后...");

        // 初始化控件
        initView();
        // 设置数据
        initData();
        return mRootView;

    }

    protected abstract void initInjector();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initInjector();
    }

    // 实现懒加载
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isVisible() && mRootView != null){
            initView();
        }
    }
    /**
     * 打开targetActivity
     * @param targetActivity
     */
    public void gotoActivity(Class<?> targetActivity){
        startActivity(new Intent(getActivity(),targetActivity));
        getActivity().overridePendingTransition(R.anim.translate_in,R.anim.translate_out);
    }


    public abstract void ScrollToTop();




    @Override
    public final boolean isAlive() {
        return isAlive && context != null;// & ! isRemoving();导致finish，onDestroy内runUiThread不可用
    }
    @Override
    public final boolean isRunning() {
        return isRunning & isAlive();
    }

    @Override
    public void onResume() {
        super.onResume();
        isRunning = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isRunning = false;
    }

    /**销毁并回收内存
     * @warn 子类如果要使用这个方法内用到的变量，应重写onDestroy方法并在super.onDestroy();前操作
     */
    @Override
    public void onDestroy() {


        isAlive = false;
        isRunning = false;
        super.onDestroy();

        context = null;

        //移除view绑定
        if (unbinder != null) {
            unbinder.unbind();
        }

    }

}
