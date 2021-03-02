package com.example.petsadminsm.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.petsadminsm.Adapters.UsersAdapter;
import com.example.petsadminsm.Models.KullanicilarModel;
import com.example.petsadminsm.R;
import com.example.petsadminsm.RestApi.ManagerAll;
import com.example.petsadminsm.Utils.ChangeFragments;
import com.example.petsadminsm.Utils.Warnings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class KullanicilarFragment extends Fragment {

    private View view;
    private RecyclerView kullanicilar_recyclerview;
    private List<KullanicilarModel> list;
    private UsersAdapter usersAdapter;
    private ImageView kul_backImage;
    ChangeFragments changeFragments;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_kullanicilar, container, false);

        tanimlamalar();
        getKullanicilarRequest();

        return view;
    }

    public void tanimlamalar(){

        kullanicilar_recyclerview=view.findViewById(R.id.kullanicilar_recyclerview);
        RecyclerView.LayoutManager mng=new GridLayoutManager(getContext(),1);
        kullanicilar_recyclerview.setLayoutManager(mng);
        list=new ArrayList<>();
        changeFragments=new ChangeFragments(getContext());
        kul_backImage=view.findViewById(R.id.kul_backImage);

        // geri gelme
        kul_backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeFragments.change(new HomeFragment());
            }
        });
    }

    public void getKullanicilarRequest(){

        Call<List<KullanicilarModel>> request= ManagerAll.getInstance().getKullanicilar();
        request.enqueue(new Callback<List<KullanicilarModel>>() {
            @Override
            public void onResponse(Call<List<KullanicilarModel>> call, Response<List<KullanicilarModel>> response) {

                if(response.body().get(0).isTf()){

                    list=response.body();
                    usersAdapter=new UsersAdapter(list,getContext(),getActivity());
                    kullanicilar_recyclerview.setAdapter(usersAdapter);


                }else{

                    Toast.makeText(getContext(), "No users!!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<KullanicilarModel>> call, Throwable t) {

                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_SHORT).show();
                Log.e("getkullanıcıkontrol",t.getMessage());

            }
        });
    }
}