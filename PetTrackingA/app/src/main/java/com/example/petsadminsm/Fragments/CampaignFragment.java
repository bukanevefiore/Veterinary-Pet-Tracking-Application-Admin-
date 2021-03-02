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

import com.example.petsadminsm.Adapters.CampaignAdapter;
import com.example.petsadminsm.Models.KampanyaEkleModel;
import com.example.petsadminsm.Models.KampanyaModel;
import com.example.petsadminsm.R;
import com.example.petsadminsm.RestApi.ManagerAll;
import com.example.petsadminsm.Utils.ChangeFragments;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
    private FloatingActionButton yeniKampanyaEkleButon;
    private ChangeFragments changeFragments;
    private ImageView kampanyaImage,kampanya_backImage;
    private Bitmap bitmap;  // galeriden resmi almak için
    private String imageString;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_campaign, container, false);

        tanimlamalar();
        kampanyaListelemeRequest();
        action();

        return view;
    }

    public void tanimlamalar() {

        kampanya_recyclerview = view.findViewById(R.id.kampanya_recyclerview);
        RecyclerView.LayoutManager mng = new GridLayoutManager(getContext(), 1);
        kampanya_recyclerview.setLayoutManager(mng);
        kampanyaList = new ArrayList<>();
        yeniKampanyaEkleButon = view.findViewById(R.id.yeniKampanyaEkleButon);
        bitmap=null;
        imageString="";
        changeFragments=new ChangeFragments(getContext());
        kampanya_backImage=view.findViewById(R.id.kampanya_backImage);
    }

    public void action() {

        yeniKampanyaEkleButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // alert dialog açılacak
                openKampanyaAlert();

            }
        });

        // geri gelme
        kampanya_backImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                changeFragments.change(new HomeFragment());
            }
        });
    }

    public void kampanyaListelemeRequest() {

        Call<List<KampanyaModel>> request = ManagerAll.getInstance().getKampanya();
        request.enqueue(new Callback<List<KampanyaModel>>() {
            @Override
            public void onResponse(Call<List<KampanyaModel>> call, Response<List<KampanyaModel>> response) {

                if (response.body().get(0).isTf()) {

                    Log.i("kampanyaliste", response.body().toString());
                    kampanyaList = response.body();
                    campaignAdapter = new CampaignAdapter(kampanyaList, getContext(),getActivity());
                    kampanya_recyclerview.setAdapter(campaignAdapter);
                    Toast.makeText(getContext(), "We have " + response.body().size() + " campaign", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(getContext(), "There is no campaign", Toast.LENGTH_LONG).show();

                }


            }

            @Override
            public void onFailure(Call<List<KampanyaModel>> call, Throwable t) {

                Log.e("kampanyajontrol", t.getMessage());

            }
        });
    }

    // yeni kampanya eklemek için alert diyalog açma
    public void openKampanyaAlert() {

        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.kampanya_ekle_alert_layout, null);

        final TextInputEditText kampanyabaslik, kampanyaicerik;

        final Button kampanyaKaydetButon, kampanyaImageButon;

        kampanyabaslik = view.findViewById(R.id.kampanyabaslik);
        kampanyaicerik = view.findViewById(R.id.kampanyaicerik);
        kampanyaImage = view.findViewById(R.id.kampanyaImage);
        kampanyaKaydetButon = view.findViewById(R.id.kampanyaKaydetButon);
        kampanyaImageButon = view.findViewById(R.id.kampanyaImageButon);

        AlertDialog.Builder alert = new AlertDialog.Builder(getContext());
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();

        // kampanyaya resim ekleme
        kampanyaImageButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                galeriAc();
            }
        });

        // kampanyayı kaydetme
        kampanyaKaydetButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



    if (imageToString() != "" && kampanyabaslik.getText().toString() != ""
            && kampanyaicerik.getText().toString() != "")

                {

                    // kampanya ekleme methodunu çağırma
                    kampanyaEkleRequest(kampanyabaslik.getText().toString(),kampanyaicerik.getText().toString(),imageToString(),
                            alertDialog);
                    // edittextleri boşaltma
                    kampanyabaslik.setText("");
                    kampanyaicerik.setText("");


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
        Log.i("kampanyaresimtetikleme", "" + data.getData());


        if(requestCode==777 && data!=null) {
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), path);
                kampanyaImage.setImageBitmap(bitmap);
                kampanyaImage.setVisibility(View.VISIBLE);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }

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

    public void kampanyaEkleRequest(String baslik,String icerik,String resim,AlertDialog alertDialog){

        Call<KampanyaEkleModel> request=ManagerAll.getInstance().addKampanya(baslik, icerik, resim);
        request.enqueue(new Callback<KampanyaEkleModel>() {
            @Override
            public void onResponse(Call<KampanyaEkleModel> call, Response<KampanyaEkleModel> response) {

                if(response.body().isTf()){

                    Toast.makeText(getContext(), "Campaign registration successful", Toast.LENGTH_LONG).show();
                    kampanyaListelemeRequest(); // kampanyaların son halinin listeleme için methodu tekrar çağırıyoruz
                    alertDialog.cancel();
                    
                }else{
                    Toast.makeText(getContext(), "An error occurred!!", Toast.LENGTH_LONG).show();
                    
                }

            }

            @Override
            public void onFailure(Call<KampanyaEkleModel> call, Throwable t) {

                Log.e("kampanyaeklekontrol",t.getMessage());

            }
        });
    }
}