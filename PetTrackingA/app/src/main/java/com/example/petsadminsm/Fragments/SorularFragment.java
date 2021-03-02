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

import com.example.petsadminsm.Adapters.SorularAdapter;
import com.example.petsadminsm.Models.SorularModel;
import com.example.petsadminsm.R;
import com.example.petsadminsm.RestApi.ManagerAll;
import com.example.petsadminsm.Utils.ChangeFragments;
import com.example.petsadminsm.Utils.Warnings;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SorularFragment extends Fragment {

    private View view;
    private RecyclerView sorular_recyclerview;
    List<SorularModel> sorularList;
    private SorularAdapter sorularAdapter;
    private ImageView soru_backImage;
    private ChangeFragments changeFragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_sorular, container, false);

        tanimlamalar();
        getSorularRequest();

        return view;
    }

    public void tanimlamalar(){

        sorular_recyclerview=view.findViewById(R.id.sorular_recyclerview);
        RecyclerView.LayoutManager mng=new GridLayoutManager(getContext(),1);
        sorular_recyclerview.setLayoutManager(mng);
        sorularList=new ArrayList<>();
        changeFragments=new ChangeFragments(getContext());
        soru_backImage=view.findViewById(R.id.soru_backImage);
        soru_backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeFragments.change(new HomeFragment());
            }
        });
    }

    public void getSorularRequest(){

        Call<List<SorularModel>> request= ManagerAll.getInstance().getSorular();
        request.enqueue(new Callback<List<SorularModel>>() {
            @Override
            public void onResponse(Call<List<SorularModel>> call, Response<List<SorularModel>> response) {

                if(response.body().get(0).isTf()){
                    
                    sorularList=response.body();
                    sorularAdapter=new SorularAdapter(sorularList,getContext(),getActivity());
                    sorular_recyclerview.setAdapter(sorularAdapter);

                }else{

                    Toast.makeText(getContext(), "There are no questions!!", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<SorularModel>> call, Throwable t) {

                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_SHORT).show();
                Log.e("sorugetirkontrol",t.getMessage());

            }
        });
    }
}