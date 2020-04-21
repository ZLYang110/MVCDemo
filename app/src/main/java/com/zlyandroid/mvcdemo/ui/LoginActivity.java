package com.zlyandroid.mvcdemo.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;

import com.google.android.material.textfield.TextInputEditText;
import com.zlyandroid.mvcdemo.MainActivity;
import com.zlyandroid.mvcdemo.R;
import com.zlyandroid.mvcdemo.base.BaseMvpActivity;
import com.zlyandroid.mvcdemo.bean.LoginBean;
import com.zlyandroid.mvcdemo.listener.MyTextWatcher;
import com.zlyandroid.mvcdemo.mvp.presenter.LoginPresenter;
import com.zlyandroid.mvcdemo.mvp.view.LoginView;
import com.zlyandroid.mvcdemo.ui.activity.HomeActivity;
import com.zlyandroid.mvcdemo.util.ToastUtils;

import butterknife.BindView;

public class LoginActivity extends BaseMvpActivity<LoginPresenter> implements LoginView,View.OnClickListener {

    @BindView(R.id.myToolBar)
    FrameLayout myToolBar;
    @BindView(R.id.et_login_name)
    TextInputEditText etLoginName;
    @BindView(R.id.et_login_pas)
    TextInputEditText etLoginPas;
    @BindView(R.id.cb_rem_user)
    CheckBox cbRemUser;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutID() {
        return R.layout.activity_login;
    }
    @Override
    protected LoginPresenter createPresenter() {
        return  new LoginPresenter();
    }
    @Override
    public void initView() {
        btnLogin.setOnClickListener(this);
        etLoginName.addTextChangedListener(textWatcher);
        etLoginPas.addTextChangedListener(textWatcher);
        btnLogin.setEnabled(true);
    }

    @Override
    public void initData() {

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                String email = etLoginName.getText().toString().trim();
                String pwd = etLoginPas.getText().toString().trim();
                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pwd))
                     // mPresenter.getList("北京",0,10);
                mPresenter.login("zqpm","123456");

                break;
            case R.id.tv_register:
                toActivity(MainActivity.createIntent(context));
                break;
            case R.id.tv_forgetPwd:
                toActivity(MainActivity.createIntent(context));
                break;
        }
    }

    private MyTextWatcher textWatcher = new MyTextWatcher() {
        @Override
        public void afterMyTextChanged(Editable editable) {
            btnLoginSetEnabled();
        }
    };
    /**
     * 设置判断按钮是否可以点击
     */
    private void btnLoginSetEnabled() {
        if (!TextUtils.isEmpty(etLoginName.getText().toString().trim())
                && !TextUtils.isEmpty(etLoginPas.getText().toString().trim()))
            btnLogin.setEnabled(true);
        else
            btnLogin.setEnabled(false);
    }


    /**启动这个Activity的Intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    public void showLoading() {
        dialog.show();
    }

    @Override
    public void hideLoading() {
        if (dialog!=null&&dialog.isShowing()){
            dialog.dismiss();
        }
    }

    @Override
    public void onError(String throwable) {

    }

    @Override
    public void onSuccess(LoginBean.DataBean bean) {
        ToastUtils.showToast("登录成功");
        toActivity(HomeActivity.createIntent(context),false);
        finish();
    }
}
