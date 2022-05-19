package com.maity.whatsapp.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.maity.whatsapp.Fragments.Callfragment;
import com.maity.whatsapp.Fragments.Chatsfragment;
import com.maity.whatsapp.Fragments.StatusFragment;

public class Fragmentadapter extends FragmentPagerAdapter {
    public Fragmentadapter(@NonNull  FragmentManager fm) {
        super(fm);
    }


    @NonNull

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:return new Chatsfragment();
            case 1:return new StatusFragment();
            case 2:return new Callfragment();

            default:return new Chatsfragment();
        }

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable

    @Override
    public CharSequence getPageTitle(int position) {
        String title=null;
        if(position==0)
        {
            title="CHATS";
        }
        if(position==1)
        {
            title="STATUS";
        }
        if(position==2)
        {
            title="CALLS";
        }
        return title;
    }
}
