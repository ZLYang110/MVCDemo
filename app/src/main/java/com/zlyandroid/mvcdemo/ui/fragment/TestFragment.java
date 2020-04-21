package com.zlyandroid.mvcdemo.ui.fragment;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.zlyandroid.mvcdemo.R;
import com.zlyandroid.mvcdemo.base.BaseMvpFragment;
import com.zlyandroid.mvcdemo.bean.LoginBean;
import com.zlyandroid.mvcdemo.mvp.presenter.MinePresenter;
import com.zlyandroid.mvcdemo.mvp.view.MineView;
import com.zlyandroid.mvcdemo.util.log.ZLog;
import com.zlyandroid.mvcdemo.widget.CircleImageView;

import butterknife.BindView;

public class TestFragment extends BaseMvpFragment<MinePresenter> implements MineView,AdapterView.OnItemClickListener{

    /*@BindView(R.id.ci_mine_user_img)
    CircleImageView ciMineUserImg;
    @BindView(R.id.lv_mine_function_list)
    ListView lvMineFunctionList;
    @BindView(R.id.tv_mine_user_name)
    TextView tvMineUserName;
    @BindView(R.id.tv_mine_user_email)
    TextView tvMineUserEmail;*/

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter();
    }
    @Override
    public int getLayoutID() {
        return R.layout.fragment_test;
    }

    @Override
    public void initView() {
    }

    @Override
    public void initData() {

    }
    @Override
    protected void initInjector() {

    }

    @Override
    public void ScrollToTop() {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                // 主题设置
                // Intent intent = new Intent(mActivity, ThemeActivity.class);
                //mActivity.startActivityForResult(intent, ThemeActivity.requestCode);
//                mActivity.overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
                break;
            case 1:
                // 设置
                break;
            case 2:
                // 分享Plus客户端
                break;
            case 3:
                // 关于本程序
                break;
            case 4:
                // 热爱开源，感谢分享
                break;
            case 5:
                // 实验室功能
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onError(String throwable) {

    }




    @Override
    public void onSuccess(LoginBean.DataBean bean) {

    }


}
