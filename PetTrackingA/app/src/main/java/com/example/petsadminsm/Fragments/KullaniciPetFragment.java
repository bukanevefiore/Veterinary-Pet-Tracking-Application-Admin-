package com.example.petsadminsm.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.petsadminsm.Adapters.PetsAdapter;
import com.example.petsadminsm.Models.PetEkleModel;
import com.example.petsadminsm.Models.PetModel;
import com.example.petsadminsm.R;
import com.example.petsadminsm.RestApi.ManagerAll;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    private ImageView petresim_ekle;
    private Bitmap bitmap;
    private String imageString;


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
        bitmap=null;
        petEkleButon=view.findViewById(R.id.petEkleButon);

        // pet ekleme alertini açmak için butona click özelliği
        petEkleButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // butona tıklanınca alertimizi açıyoruz
                openPetEkleAlert();

            }
        });

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


    // yeni pet eklemek için alert diyalog açma
    public void openPetEkleAlert() {

        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.pets_ekle_alert_layout, null);

        final TextInputEditText petname_ekle, pettur_ekle,petcins_ekle;
        final Button petResimEkleButon, petEkleKaydetButon;

        petname_ekle = view.findViewById(R.id.petname_ekle);
        pettur_ekle = view.findViewById(R.id.pettur_ekle);
        petcins_ekle = view.findViewById(R.id.petcins_ekle);
        petresim_ekle = view.findViewById(R.id.petresim_ekle);
        petResimEkleButon = view.findViewById(R.id.petResimEkleButon);
        petEkleKaydetButon = view.findViewById(R.id.petEkleKaydetButon);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();

        // kampanyaya resim ekleme
        petResimEkleButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                galeriAc();
            }
        });

        // kampanyayı kaydetme
        petEkleKaydetButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (imageToString() != "" && petname_ekle.getText().toString() != ""
                        && pettur_ekle.getText().toString() != "" && petcins_ekle.getText().toString() != "")

                {

                    // kampanya ekleme methodunu çağırma
                    petEkleRequest(musid,petname_ekle.getText().toString(),pettur_ekle.getText().toString(),petcins_ekle.getText().toString(),imageToString().toString(),
                            alertDialog);
                    // edittextleri boşaltma
                    petname_ekle.setText("");
                    pettur_ekle.setText("");


                }else{
                    Toast.makeText(getContext(), "Fill in all fields!", Toast.LENGTH_LONG).show();
                }


            }
        });

        alertDialog.show();
    }

    public void galeriAc() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 777);
    }


    // imageview set etme
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.i("petresimtetikleme", "" + data.getData());

        Log.i("petresim1", "" + petresim_ekle);

        if(requestCode==777 && data!=null) {
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
                petresim_ekle.setImageBitmap(bitmap);
                petresim_ekle.setVisibility(View.VISIBLE);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.i("petresim", "" + petresim_ekle);

    }

    public String imageToString(){

        if(bitmap!=null) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
            byte[] byt = byteArrayOutputStream.toByteArray();
            imageString = Base64.encodeToString(byt, Base64.DEFAULT);
            return imageString;
        }else{
            return imageString;
        }
    }

    public void petEkleRequest(String musid,String petisim,String pettur,String petcins,String petresim,AlertDialog alertDialog){

        Call<PetEkleModel> request=ManagerAll.getInstance().petEkle(musid, petisim, pettur, petcins, petresim);
        request.enqueue(new Callback<PetEkleModel>() {
            @Override
            public void onResponse(Call<PetEkleModel> call, Response<PetEkleModel> response) {

                if(response.body().isTf()){

                    getPets(musid);
                    Toast.makeText(getContext(), response.body().getText().toString(), Toast.LENGTH_SHORT).show();
                    alertDialog.cancel();

                }else{

                    Toast.makeText(getContext(), response.body().getText().toString(), Toast.LENGTH_SHORT).show();
                    alertDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<PetEkleModel> call, Throwable t) {

            }
        });

    }

}