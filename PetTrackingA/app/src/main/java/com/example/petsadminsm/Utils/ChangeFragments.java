package com.example.petsadminsm.Utils;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;


import com.example.petsadminsm.R;


public class ChangeFragments {

    private Context context;

    public ChangeFragments(Context context) {
        this.context = context;
    }

    public void change(Fragment fragment){


        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFrameLayout,fragment,"fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

    }

    public void changeWithParemeters(Fragment fragment,String userid){

        Bundle bundle=new Bundle();
        bundle.putString("userid",userid);
        fragment.setArguments(bundle);

        ((FragmentActivity)context).getSupportFragmentManager().beginTransaction()
                .replace(R.id.mainFrameLayout,fragment,"fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();

    }
}
