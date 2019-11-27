package org.zoobie.pomd.remotecontrol.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.zoobie.pomd.remotecontrol.fragment.tabs.FirstTabFragment;
import org.zoobie.pomd.remotecontrol.fragment.tabs.SecondTabFragment;
import org.zoobie.pomd.remotecontrol.fragment.tabs.ThirdTabFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment item;
        position++;


        Bundle bundle = new Bundle();
        if(position == 1) item = new FirstTabFragment();
        else if(position == 2) item = new SecondTabFragment();
        else item = new ThirdTabFragment();
        bundle.putString("message","" + position);
        bundle.putInt("id",position);
        item.setArguments(bundle);
        return item;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position){
        position++;

        if(position==1){
            return "TouchPad";
        }
        else if(position == 2){
            return "Keypad";
        } else return "MotionControl";
    }
}
