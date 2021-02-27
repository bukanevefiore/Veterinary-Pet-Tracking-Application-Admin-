package com.example.petsadminsm.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.petsadminsm.Adapters.CampaignAdapter;
import com.example.petsadminsm.Models.KampanyaModel;
import com.example.petsadminsm.R;
import com.example.petsadminsm.RestApi.ManagerAll;
import com.example.petsadminsm.Utils.ChangeFragments;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class CampaignFragment extends Fragment {

    private View view;
    private RecyclerView kampanya_recyclerview;
    private List<KampanyaModel> kampanyaList;
    private CampaignAdapter campaignAdapter;
    FloatingActionButton yeniKampanyaEkleButon;
    private ChangeFragments changeFragments;



    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_campaign, container, false);

        tanimlamalar();
        kampanyaListelemeRequest();

        return view;
    }

    public void tanimlamalar(){

        kampanya_recyclerview=view.findViewById(R.id.kampanya_recyclerview);
        RecyclerView.LayoutManager mng=new GridLayoutManager(getContext(),1);
        kampanya_recyclerview.setLayoutManager(mng);
        kampanyaList=new ArrayList<>();
        yeniKampanyaEkleButon=view.findViewById(R.id.yeniKampanyaEkleButon);
    }

    public void action(){

        yeniKampanyaEkleButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });
    }

    public void kampanyaListelemeRequest(){

        Call<List<KampanyaModel>> request= ManagerAll.getInstance().getKampanya();
        request.enqueue(new Callback<List<KampanyaModel>>() {
            @Override
            public void onResponse(Call<List<KampanyaModel>> call, Response<List<KampanyaModel>> response) {

                if(response.body().get(0).isTf()){

                    Log.i("kampanyaliste",response.body().toString());
                    kampanyaList=response.body();
                    campaignAdapter=new CampaignAdapter(kampanyaList,getContext());
                    kampanya_recyclerview.setAdapter(campaignAdapter);
                    Toast.makeText(getContext(), "We have "+response.body().size()+" campaign", Toast.LENGTH_SHORT).show();

                }else
                {
                    Toast.makeText(getContext(), "There is no campaign", Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<List<KampanyaModel>> call, Throwable t) {

                Log.e("kampanyajontrol",t.getMessage());

            }
        });
    }

    // yeni kampanya eklemek için alert diyalog açma
    public void openKampanya(){

        LayoutInflater layoutInflater=this.getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.,null);

        final

        AlertDialog.Builder alert=new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog=alert.create();
        kampanyaekleButon.

        alertDialog.show();
    }
}