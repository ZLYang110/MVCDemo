package com.zlyandroid.mvcdemo.adapter;



import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.zlyandroid.mvcdemo.base.BaseFragment;

import java.util.List;

/**
 * 主页切换Adapter
 */

public class MainPageAdapter extends FragmentStatePagerAdapter {

    private List<BaseFragment> fragments;

    public MainPageAdapter(FragmentManager fm, List<BaseFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }
}
