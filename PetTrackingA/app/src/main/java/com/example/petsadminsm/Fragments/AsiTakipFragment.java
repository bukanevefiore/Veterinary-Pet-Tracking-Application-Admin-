package com.example.petsadminsm.Fragments;

import android.accessibilityservice.AccessibilityService;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.petsadminsm.Adapters.PetAsiTakipAdapter;
import com.example.petsadminsm.Models.PetAsiTakipModel;
import com.example.petsadminsm.R;
import com.example.petsadminsm.RestApi.ManagerAll;
import com.example.petsadminsm.Utils.ChangeFragments;
import com.example.petsadminsm.Utils.Warnings;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AsiTakipFragment extends Fragment {

    private View view;
    private DateFormat format;
    private Date date;
    private String today;
    private RecyclerView asiTakip_recyclerview;
    private List<PetAsiTakipModel> takipList;
    private PetAsiTakipAdapter petAsiTakipAdapter;
    private ImageView asitakip_backImage;
    private ChangeFragments changeFragments;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_asi_takip, container, false);

        tanimlamalar();
        asiTakipRequest(today);
        return view;

    }

    public void tanimlamalar(){

        format=new SimpleDateFormat("dd/MM/yyyy");
        date= Calendar.getInstance().getTime();
        today=format.format(date);
        Log.i("bugün",today);

        asiTakip_recyclerview=view.findViewById(R.id.asiTakip_recyclerview);
        RecyclerView.LayoutManager mng=new GridLayoutManager(getContext(),1);
        asiTakip_recyclerview.setLayoutManager(mng);
        takipList=new ArrayList<>();
        asitakip_backImage=view.findViewById(R.id.asitakip_backImage);
        changeFragments=new ChangeFragments(getContext());

        asitakip_backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeFragments.change(new HomeFragment());
            }
        });

    }

    // bugün kü yapılacak aşıların listelenmesi işlemi
    public void asiTakipRequest(String asitarih){

        Call<List<PetAsiTakipModel>> request= ManagerAll.getInstance().getPetAsiTakip(asitarih);
        request.enqueue(new Callback<List<PetAsiTakipModel>>() {
            @Override
            public void onResponse(Call<List<PetAsiTakipModel>> call, Response<List<PetAsiTakipModel>> response) {
                Log.i("bugünküpetler",response.body().toString());
                if(response.body().get(0).isTf())
                {

                    takipList=response.body();
                    petAsiTakipAdapter=new PetAsiTakipAdapter(takipList,getContext(),getActivity());
                    asiTakip_recyclerview.setAdapter(petAsiTakipAdapter);

                    Toast.makeText(getContext(), "There are" +response.body().size()+ "vaccines available today ..", Toast.LENGTH_LONG).show();



                }else
                {
                    Toast.makeText(getContext(), "There is no vaccine to be given today !!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<PetAsiTakipModel>> call, Throwable t) {

                Toast.makeText(getContext(), Warnings.internetProblemText, Toast.LENGTH_SHORT).show();
                Log.e("asilistekontrol",t.getMessage());
            }
        });
    }
}