package com.example.petsadminsm;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.petsadminsm.Fragments.HomeFragment;
import com.example.petsadminsm.Utils.ChangeFragments;

public class MainActivity extends AppCompatActivity {

     ChangeFragments changeFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeFragments=new ChangeFragments(MainActivity.this);
        changeFragments.change(new HomeFragment());
    }
}