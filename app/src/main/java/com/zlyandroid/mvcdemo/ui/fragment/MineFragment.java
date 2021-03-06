package com.zlyandroid.mvcdemo.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.zlyandroid.mvcdemo.MainActivity;
import com.zlyandroid.mvcdemo.R;
import com.zlyandroid.mvcdemo.base.BaseFragment;
import com.zlyandroid.mvcdemo.base.BaseMvpFragment;
import com.zlyandroid.mvcdemo.bean.LoginBean;
import com.zlyandroid.mvcdemo.mvp.BasePresenter;
import com.zlyandroid.mvcdemo.mvp.presenter.LoginPresenter;
import com.zlyandroid.mvcdemo.mvp.presenter.MinePresenter;
import com.zlyandroid.mvcdemo.mvp.view.MineView;
import com.zlyandroid.mvcdemo.ui.activity.ThemeActivity;
import com.zlyandroid.mvcdemo.util.log.ZLog;
import com.zlyandroid.mvcdemo.widget.CircleImageView;

import butterknife.BindView;

public class MineFragment extends BaseMvpFragment<MinePresenter> implements MineView,AdapterView.OnItemClickListener{

    @BindView(R.id.ci_mine_user_img)
    CircleImageView ciMineUserImg;
    @BindView(R.id.lv_mine_function_list)
    ListView lvMineFunctionList;
    @BindView(R.id.tv_mine_user_name)
    TextView tvMineUserName;
    @BindView(R.id.tv_mine_user_email)
    TextView tvMineUserEmail;

    @Override
    protected MinePresenter createPresenter() {
        return new MinePresenter();
    }
    @Override
    public int getLayoutID() {
        return R.layout.fragment_mine;
    }

    @Override
    public void initView() {
        ciMineUserImg.setImageDrawable(getResources().getDrawable(R.drawable.image_placeholder));
        tvMineUserName.setText("点击头像登陆");
        tvMineUserEmail.setVisibility(View.GONE);
    }

    @Override
    public void initData() {
        lvMineFunctionList.setAdapter(new SimpleAdapter(context, mPresenter.getMenuList(),
                R.layout.item_function, new String[]{"icon", "title"},
                new int[]{R.id.icon, R.id.title}));
        lvMineFunctionList.setOnItemClickListener(this);
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
               // gotoActivityForResult(ThemeActivity.class,ThemeActivity.requestCode);
                Intent intent = new Intent(context, ThemeActivity.class);
                context.startActivityForResult(intent, ThemeActivity.requestCode);
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
