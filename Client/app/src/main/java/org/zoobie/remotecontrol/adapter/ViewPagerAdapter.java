package org.zoobie.remotecontrol.adapter;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import org.zoobie.remotecontrol.core.connection.Connector;
import org.zoobie.remotecontrol.tabs.TouchpadFragment;
import org.zoobie.remotecontrol.tabs.KeyboardFragment;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Connector connector;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior, Connector connector) {
        super(fm, behavior);
        this.connector = connector;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment item;
        position++;


        Bundle bundle = new Bundle();
        if(position == 1) item = new TouchpadFragment(connector);
        else item = new KeyboardFragment(connector);
        bundle.putString("message","" + position);
        bundle.putInt("id",position);
        item.setArguments(bundle);
        return item;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position){
        position++;

        if(position==1){
            return "TouchPad";
        } else return "Keyboard";
    }
}
