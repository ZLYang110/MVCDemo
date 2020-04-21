package com.zlyandroid.mvcdemo.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.viewpager.widget.ViewPager;

import com.zlyandroid.mvcdemo.MainActivity;
import com.zlyandroid.mvcdemo.R;
import com.zlyandroid.mvcdemo.adapter.MainPageAdapter;
import com.zlyandroid.mvcdemo.base.BaseFragment;
import com.zlyandroid.mvcdemo.base.BaseMvpActivity;
import com.zlyandroid.mvcdemo.bean.LoginBean;
import com.zlyandroid.mvcdemo.mvp.BasePresenter;
import com.zlyandroid.mvcdemo.mvp.presenter.HomePresenter;
import com.zlyandroid.mvcdemo.mvp.presenter.LoginPresenter;
import com.zlyandroid.mvcdemo.mvp.view.HomeView;
import com.zlyandroid.mvcdemo.ui.fragment.MineFragment;
import com.zlyandroid.mvcdemo.ui.fragment.TestFragment;
import com.zlyandroid.mvcdemo.util.log.ZLog;
import com.zlyandroid.mvcdemo.widget.MyBottomTab;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class HomeActivity extends BaseMvpActivity implements ViewPager.OnPageChangeListener  {

    @BindView(R.id.bottom_bar)
    MyBottomTab bottomBar;
    private ViewPager viewPager;
    private List<BaseFragment> fragments = new ArrayList<>();

    private static MineFragment mineFragment;
    private static TestFragment testFragment;
    private static MineFragment mineFragment3;
    private static MineFragment mineFragment4;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }
    /**获取启动UserActivity的intent
     * @param context
     * @return
     */
    public static Intent createIntent(Context context) {
        return new Intent(context, HomeActivity.class);
    }
    @Override
    public int getLayoutID() {
        return R.layout.activity_home;
    }

    @Override
    public void initView() {
        bottomBar.setOnTabChangeListener((v, position, isChange) -> setSelect(position, isChange));
    }

    private void setSelect(int position, boolean isChange) {
        if (isChange)
            viewPager.setCurrentItem(position, false);
        else
            fragments.get(position).ScrollToTop();
    }
    @Override
    public void initData() {
        initViewpager();
    }

    private void initViewpager() {
        viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(4);
        viewPager.addOnPageChangeListener(this);
        mineFragment = new MineFragment();
        testFragment = new TestFragment();
        mineFragment3 = new MineFragment();
        mineFragment4 = new MineFragment();
        fragments.add(mineFragment);
        fragments.add(testFragment);
        fragments.add(mineFragment3);
        fragments.add(mineFragment4);
        MainPageAdapter adapter = new MainPageAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(adapter);
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
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
            bottomBar.setSelect(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
    private long mExitTime;
    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            if ((System.currentTimeMillis() - mExitTime) > 1500) {
                Toast.makeText(this, "再按一次退出ZLY客户端(｡･ω･｡)~~", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
        }
    }
}
