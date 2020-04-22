package com.zlyandroid.mvcdemo.base;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;
import com.trello.rxlifecycle3.components.support.RxAppCompatActivity;
import com.zlyandroid.mvcdemo.R;
import com.zlyandroid.mvcdemo.app.ProApplication;
import com.zlyandroid.mvcdemo.interfaces.ActivityPresenter;
import com.zlyandroid.mvcdemo.manager.SystemBarTintManager;
import com.zlyandroid.mvcdemo.manager.ThreadManager;
import com.zlyandroid.mvcdemo.ui.activity.ThemeActivity;
import com.zlyandroid.mvcdemo.util.DialogUtils;
import com.zlyandroid.mvcdemo.util.DimmenUtils;
import com.zlyandroid.mvcdemo.util.Log;
import com.zlyandroid.mvcdemo.util.StringUtil;

import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseActivity extends RxAppCompatActivity implements ActivityPresenter {

    private static final String TAG = "BaseActivity";

    @Override
    public Activity getActivity() {
        return this; //必须return this;
    }
    /**
     * 该Activity实例，命名为context是因为大部分方法都只需要context，写成context使用更方便
     * @warn 不能在子类中创建
     */
    protected BaseActivity context = null;
    /**
     * 该Activity的界面，即contentView
     * @warn 不能在子类中创建
     */
    protected View view = null;

    private boolean isAlive = false;
    private boolean isRunning = false;

    private Unbinder unbinder;

    protected Dialog dialog;

    /***是否显示标题栏*/
    private boolean isshowtitle = true;
    /***是否显示标题栏*/
    private boolean isshowstate = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 初始化状态栏
        initWindowTitle();
        // 切换主题
        switchTheme();
        setContentView(getLayoutID());
        context = (BaseActivity) getActivity();
        unbinder = ButterKnife.bind(this);
        isAlive = true;
        dialog = DialogUtils.createLoadingDialog(this, "请稍后...");

        // 初始化控件
        initView();
        // 设置数据
        initData();

    }
    /**
     * 初始化状态栏
     */
    private void initWindowTitle() {
        if (!isshowtitle) {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        }

        if (isshowstate) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    /**
     * 是否设置标题栏
     *
     * @return
     */
    public void setTitle(boolean ishow) {
        isshowtitle = ishow;
    }

    /**
     * 设置是否显示状态栏
     *
     * @param ishow
     */
    public void setState(boolean ishow) {
        isshowstate = ishow;
    }
    /**
     * 初始化标题栏
     *
     * @param isshowBack
     * @param text
     */
    protected void initToolBar(boolean isshowBack, String text) {
        View toolbar = findViewById(R.id.myToolBar);
        if (toolbar != null) {
            ((TextView) toolbar.findViewById(R.id.tv_toolbar_title)).setText(text);
            if (isshowBack) {
                findViewById(R.id.iv_toolbar_back).setOnClickListener(view -> finishActivity());
            } else {
                findViewById(R.id.iv_toolbar_back).setVisibility(View.GONE);
            }
        }
    }

    /**
     * 添加标题栏组件
     *
     * @param resid
     * @return
     */
    protected ImageView addToolbarMenu(int resid) {
        View toolbar = findViewById(R.id.myToolBar);
        if (toolbar != null) {
            ImageView i = toolbar.findViewById(R.id.iv_toolbar_menu);
            i.setImageResource(resid);
            i.setVisibility(View.VISIBLE);
            return i;
        }
        return null;
    }

    /**
     * 添加标题栏组件
     *
     * @param v
     */
    protected void addToolbarView(View v) {
        FrameLayout toolbar = findViewById(R.id.myToolBar);
        if (toolbar != null) {
            FrameLayout.LayoutParams pls = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            v.setLayoutParams(pls);
            int padding = DimmenUtils.dip2px(this, 12);
            v.setPadding(padding, padding, padding, padding);
            pls.setMarginEnd(padding);
            pls.gravity = Gravity.END;
            toolbar.addView(v);
        }
    }

//底部滑动实现同点击标题栏左右按钮效果>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    /**
     * 用于 打开activity以及activity之间的通讯（传值）等；一些通讯相关基本操作（打电话、发短信等）
     */
    protected Intent intent = null;



    /**通过id查找并获取控件，使用时不需要强转
     * @param id
     * @return
     */
    @SuppressWarnings("unchecked")
    public <V extends View> V findView(int id) {
        return (V) findViewById(id);
    }
    /**通过id查找并获取控件，并setOnClickListener
     * @param id
     * @param l
     * @return
     */
    public <V extends View> V findView(int id, View.OnClickListener l) {
        V v = findView(id);
        v.setOnClickListener(l);
        return v;
    }
    /**通过id查找并获取控件，并setOnClickListener
     * @param id
     * @param l
     * @return
     */
    public <V extends View> V findViewById(int id, View.OnClickListener l) {
        return findView(id, l);
    }


    //启动新Activity方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**打开新的Activity，向左滑入效果
     * @param intent
     */
    public void toActivity(Intent intent) {
        toActivity(intent, true);
    }
    /**打开新的Activity
     * @param intent
     * @param showAnimation
     */
    public void toActivity(Intent intent, boolean showAnimation) {
        toActivity(intent, -1, showAnimation);
    }
    /**打开新的Activity，向左滑入效果
     * @param intent
     * @param requestCode
     */
    public void toActivity(Intent intent, int requestCode) {
        toActivity(intent, requestCode, true);
    }
    /**打开新的Activity
     * @param intent
     * @param requestCode
     * @param showAnimation
     */
    public void toActivity(final Intent intent, final int requestCode, final boolean showAnimation) {
        runUiThread(new Runnable() {
            @Override
            public void run() {
                if (intent == null) {
                    Log.w(TAG, "toActivity  intent == null >> return;");
                    return;
                }
                //fragment中使用context.startActivity会导致在fragment中不能正常接收onActivityResult
                if (requestCode < 0) {
                    startActivity(intent);
                } else {
                    startActivityForResult(intent, requestCode);
                }
                if (showAnimation) {
                    overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
                } else {
                    overridePendingTransition(R.anim.null_anim, R.anim.null_anim);
                }
            }
        });
    }
    //启动新Activity方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //运行线程 <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**在UI线程中运行，建议用这个方法代替runOnUiThread
     * @param action
     */
    public final void runUiThread(Runnable action) {
        if (isAlive() == false) {
            Log.w(TAG, "runUiThread  isAlive() == false >> return;");
            return;
        }
        runOnUiThread(action);
    }
    /**
     * 线程名列表
     */
    protected List<String> threadNameList;
    /**运行线程
     * @param name
     * @param runnable
     * @return
     */
    public final Handler runThread(String name, Runnable runnable) {
        if (isAlive() == false) {
            Log.w(TAG, "runThread  isAlive() == false >> return null;");
            return null;
        }
        name = StringUtil.getTrimedString(name);
        Handler handler = ThreadManager.getInstance().runThread(name, runnable);
        if (handler == null) {
            Log.e(TAG, "runThread handler == null >> return null;");
            return null;
        }

        if (threadNameList.contains(name) == false) {
            threadNameList.add(name);
        }
        return handler;
    }

    //运行线程 >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    public void hideKeyBoard() {
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    /**
     * 初始化滑动事件
     */
    public void initSlidr() {
        SlidrConfig config = new SlidrConfig.Builder()
                .position(SlidrPosition.LEFT)//滑动起始方向
                .edge(true)
                .edgeSize(0.18f)//距离左边界占屏幕大小的18%
                .build();
        Slidr.attach(this, config);
    }

    /**
     * 中途 切换主题
     */
    public void switchTheme() {
        //直接夜间 设置退出
        int theme = ProApplication.getCustomTheme();
        int cur = AppCompatDelegate.getDefaultNightMode();
        int to = cur;
        boolean autoChnage = false;

        if (theme == ThemeActivity.THEME_NIGHT) {
            //夜间主题
            to = AppCompatDelegate.MODE_NIGHT_YES;
        } else {
            //白天主题
            if (ProApplication.isAutoDarkMode()) {
                autoChnage = true;
                int[] time = ProApplication.getDarkModeTime();
                int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
//                if ((hour >= time[0] || hour < time[1])) {
                //自动切换
//                    to = AppCompatDelegate.MODE_NIGHT_YES;
//                    printLog("toNight");
//                } else {
                to = AppCompatDelegate.MODE_NIGHT_NO;
//                }
            } else {
                to = AppCompatDelegate.MODE_NIGHT_NO;
            }
        }

        if (to == AppCompatDelegate.MODE_NIGHT_YES) {
            //夜间模式主题
            setTheme(R.style.AppTheme);
        } else {
            setTheme(theme);
        }

        //黑白发生了变化
        if (to != cur) {
//            if (autoChnage) {
//                showToast("自动" + (to == AppCompatDelegate.MODE_NIGHT_YES ?
//                        "切换到夜间模式" : "关闭夜间模式"));
//            }
            AppCompatDelegate.setDefaultNightMode(to);
        }
    }

    @Override
    public final boolean isAlive() {
        return isAlive && context != null;// & ! isFinishing();导致finish，onDestroy内runUiThread不可用
    }
    @Override
    public final boolean isRunning() {
        return isRunning & isAlive();
    }


    @Override
    public void finish() {
        super.finish();//必须写在最前才能显示自定义动画
        runUiThread(new Runnable() {
            @Override
            public void run() {
                    try {
                        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
                    } catch (Exception e) {
                        Log.e(TAG, "finish overridePendingTransition(enterAnim, exitAnim);" +
                                " >> catch (Exception e) {  " + e.getMessage());
                    }
            }
        });
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "\n onResume <<<<<<<<<<<<<<<<<<<<<<<");
        super.onResume();
        isRunning = true;
        Log.d(TAG, "onResume >>>>>>>>>>>>>>>>>>>>>>>>\n");
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "\n onPause <<<<<<<<<<<<<<<<<<<<<<<");
        super.onPause();
        isRunning = false;
        Log.d(TAG, "onPause >>>>>>>>>>>>>>>>>>>>>>>>\n");
    }

    /**销毁并回收内存
     * @warn 子类如果要使用这个方法内用到的变量，应重写onDestroy方法并在super.onDestroy();前操作
     */
    @Override
    protected void onDestroy() {
        Log.d(TAG, "\n onDestroy <<<<<<<<<<<<<<<<<<<<<<<");
        ThreadManager.getInstance().destroyThread(threadNameList);
        if (view != null) {
            try {
                view.destroyDrawingCache();
            } catch (Exception e) {
                Log.w(TAG, "onDestroy  try { view.destroyDrawingCache();" +
                        " >> } catch (Exception e) {\n" + e.getMessage());
            }
        }
        unbinder.unbind();
        isAlive = false;
        isRunning = false;
        super.onDestroy();

        view = null;
        threadNameList = null;

        intent = null;

        context = null;

        Log.d(TAG, "onDestroy >>>>>>>>>>>>>>>>>>>>>>>>\n");
    }
    public void finishActivity() {
        finish();
        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
    }
}
