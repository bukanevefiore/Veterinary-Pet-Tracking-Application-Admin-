package com.example.petsadminsm.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.petsadminsm.R;
import com.example.petsadminsm.Utils.ChangeFragments;


public class HomeFragment extends Fragment {

    private View view;
    private LinearLayout anaSorular_linear,anaKullanıcılar_linear,anaKampanyalar_linear,anaPetTakip_linear;
    private ChangeFragments changeFragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_home, container, false);

        tanimlamalar();
        clickToLayout();

        return view;
    }

    public void tanimlamalar(){

        changeFragments=new ChangeFragments(getContext());
        anaSorular_linear=view.findViewById(R.id.anaSorular_linear);
        anaKullanıcılar_linear=view.findViewById(R.id.anaKullanıcılar_linear);
        anaKampanyalar_linear=view.findViewById(R.id.anaKampanyalar_linear);
        anaPetTakip_linear=view.findViewById(R.id.anaPetTakip_linear);
    }

    public void clickToLayout(){

        anaKampanyalar_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeFragments.change(new CampaignFragment());
            }
        });

        anaPetTakip_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeFragments.change(new AsiTakipFragment());
            }
        });


        anaSorular_linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeFragments.change(new SorularFragment());
            }
        });
    }




}