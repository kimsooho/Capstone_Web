package com.example.capstone.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.example.capstone.Fragment.TabDialogueFragment;
import com.example.capstone.Fragment.TabSummaryFragment;

public class ContentPagerAdapter extends FragmentStatePagerAdapter {
    private int mPageCount;

    public ContentPagerAdapter(FragmentManager fm, int pageCount) {
        super(fm);
        this.mPageCount = pageCount;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
                TabDialogueFragment homeFragment = new TabDialogueFragment();
                return homeFragment;

            case 1:
                TabSummaryFragment tab2Fragment = new TabSummaryFragment();
                return tab2Fragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mPageCount;
    }
}
