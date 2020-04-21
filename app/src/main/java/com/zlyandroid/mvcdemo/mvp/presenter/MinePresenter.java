package com.zlyandroid.mvcdemo.mvp.presenter;

import com.zlyandroid.mvcdemo.R;
import com.zlyandroid.mvcdemo.bean.LoginBean;
import com.zlyandroid.mvcdemo.mvp.BasePresenter;
import com.zlyandroid.mvcdemo.mvp.contract.LoginContract;
import com.zlyandroid.mvcdemo.mvp.contract.MineContract;
import com.zlyandroid.mvcdemo.mvp.model.LoginModel;
import com.zlyandroid.mvcdemo.mvp.model.MineModel;
import com.zlyandroid.mvcdemo.mvp.view.LoginView;
import com.zlyandroid.mvcdemo.mvp.view.MineView;
import com.zlyandroid.mvcdemo.net.BaseObserver;
import com.zlyandroid.mvcdemo.util.log.ZLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MinePresenter extends BasePresenter<MineView> implements MineContract.Presenter{

    private static final int[] icons = new int[]{
//            R.drawable.ic_autorenew_black_24dp,
            R.drawable.ic_palette_black_24dp,
            R.drawable.ic_settings_24dp,
            R.drawable.ic_menu_share_24dp,
            R.drawable.ic_info_24dp,
            R.drawable.ic_favorite_white_12dp,
            R.drawable.ic_lab_24dp,
    };

    private static final String[] titles = new String[]{
//            "签到中心",
            "主题设置",
            "设置",
            "分享Plus客户端",
            "关于本程序",
            "热爱开源，感谢分享",
            "实验室功能",
    };

    private MineContract.Model model;

    public MinePresenter() {
        model = new MineModel();
    }


    @Override
    public void getData() {

    }

    public List<Map<String, Object>> getMenuList() {
        List<Map<String, Object>> list = new ArrayList<>();
        for (int i = 0; i < icons.length; i++) {
            Map<String, Object> ob = new HashMap<>();
            ob.put("icon", icons[i]);
            ob.put("title", titles[i]);
            list.add(ob);
        }
        return list;
    }
}
