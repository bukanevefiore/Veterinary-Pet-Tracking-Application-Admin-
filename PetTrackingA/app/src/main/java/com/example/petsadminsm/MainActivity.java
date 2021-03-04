package com.example.petsadminsm;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.petsadminsm.Fragments.HomeFragment;
import com.example.petsadminsm.Utils.ChangeFragments;
import com.google.android.material.button.MaterialButtonToggleGroup;

public class MainActivity extends AppCompatActivity {

     ChangeFragments changeFragments;
     private MaterialButtonToggleGroup home_menu_buton,aramaYapButon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        changeFragments=new ChangeFragments(MainActivity.this);
        changeFragments.change(new HomeFragment());

        tanimlamalar();
        click();
    }

    public void tanimlamalar(){

        home_menu_buton=findViewById(R.id.home_menu_buton);
        aramaYapButon=findViewById(R.id.aramaYapButon);
    }

    public void click(){

        home_menu_buton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeFragments.change(new HomeFragment());

            }
        });

        // genel arama yapma
        aramaYapButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ara("11111111111");

            }
        });
    }

    // pet in sahibini arama işlemi
    public void ara(String numara){

        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:"+numara));
        startActivity(intent);

    }
}