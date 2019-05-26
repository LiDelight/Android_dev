package edu.cqut.MobileOrderFood.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wxy on 16/6/24.
 */
//该类可以实现Tab水平换页的方式
        public class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {
            private List<Fragment> fragments = new ArrayList<>(0);
            private String[] names;
    //FragmentManager　是用来管理Fragment 负责　添加　移除　显示　隐藏，
            public MyFragmentPagerAdapter(FragmentManager fm, List<Fragment> fragments, String[] names) {
                super(fm);
                this.fragments = fragments;
                this.names = names;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
}

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (names != null){
            return names[position];
        }else {
            return "";
        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
    }
}
