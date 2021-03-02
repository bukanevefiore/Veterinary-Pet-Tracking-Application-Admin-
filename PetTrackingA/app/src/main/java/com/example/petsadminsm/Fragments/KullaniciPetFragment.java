package com.example.petsadminsm.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.petsadminsm.Adapters.PetsAdapter;
import com.example.petsadminsm.Models.PetModel;
import com.example.petsadminsm.R;
import com.example.petsadminsm.RestApi.ManagerAll;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KullaniciPetFragment extends Fragment {

    private View view;
    private String musid;
    private RecyclerView petler_recyclerview;
    private FloatingActionButton petEkleButon;
    private List<PetModel> list;
    private PetsAdapter petsAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_kullanici_pet, container, false);

        tanimlamalar();
        getPets(musid);

        return view;
    }

    public void tanimlamalar(){

        // kullanıcı fragmentinden gönderilen musid paremetresini alıyoruz
        musid=getArguments().get("userid").toString();
        petler_recyclerview=view.findViewById(R.id.petler_recyclerview);
        RecyclerView.LayoutManager mng=new GridLayoutManager(getContext(),2);
        petler_recyclerview.setLayoutManager(mng);
        list=new ArrayList<>();
        petEkleButon=view.findViewById(R.id.petEkleButon);

    }

    // petleri listelemek için servise istek
    public void getPets(String musid){

        Call<List<PetModel>> request= ManagerAll.getInstance().getPets(musid);
        request.enqueue(new Callback<List<PetModel>>() {
            @Override
            public void onResponse(Call<List<PetModel>> call, Response<List<PetModel>> response) {
                if(response.body().get(0).isTf()){

                    list=response.body();
                    petsAdapter=new PetsAdapter(list,getContext(),getActivity(),musid);
                    petler_recyclerview.setAdapter(petsAdapter);
                    Toast.makeText(getContext(), "There are"+response.body().size()+ "pets belonging to the user", Toast.LENGTH_SHORT).show();

                }else{

                    Toast.makeText(getContext(), "The pet belonging to the user has not been found !!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PetModel>> call, Throwable t) {

            }
        });
    }
}