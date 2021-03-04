package com.example.petsadminsm.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petsadminsm.Fragments.KullaniciPetFragment;
import com.example.petsadminsm.Models.AsiEkleModel;
import com.example.petsadminsm.Models.KullaniciSilModel;
import com.example.petsadminsm.Models.KullanicilarModel;
import com.example.petsadminsm.Models.PetModel;
import com.example.petsadminsm.Models.PetSilModel;
import com.example.petsadminsm.R;
import com.example.petsadminsm.RestApi.ManagerAll;
import com.example.petsadminsm.Utils.ChangeFragments;
import com.example.petsadminsm.Utils.Warnings;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.internal.$Gson$Preconditions;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetsAdapter extends RecyclerView.Adapter<PetsAdapter.ViewHolder>{

    private Context context;
    private List<PetModel> list;
    private Activity activity;
    ChangeFragments changeFragments;
    private String musid;
    private String tarih="";


    public PetsAdapter(List<PetModel> list, Context context, Activity activity,String musid) {
        this.context = context;
        this.list = list;
        this.activity=activity;
        this.musid=musid;
        changeFragments=new ChangeFragments(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.user_pet_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {

        holder.pet_Nametext.setText(list.get(position).getPetisim().toString());
        holder.petBilgi_Bilgitext.setText("Type" +list.get(position).getPettur().toString()+" of this pet is breed " +list.get(position).getPetcins().toString()+
                " Click on the pet to add the vaccine!");

        Picasso.get().load("http://localhost/veterinary/"+list.get(position).getPetresim().toString()).resize(70,70)
                .into(holder.kullanici_petImage);

        // pete yeni aşı ekleme
        holder.pet_asi_ekleme_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openAsiEkleAlert(list.get(position).getPetid().toString());
            }
        });

        holder.silAlertAcButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

               openPetSilAlert(position);
            }
        });

    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    // view elemanlarını tanımlamak için bir iner class oluştuuryoruz
    public class ViewHolder extends RecyclerView.ViewHolder
    {
        final TextView pet_Nametext,petBilgi_Bilgitext;
        final CircleImageView kullanici_petImage;
        final CardView pet_asi_ekleme_cardView;
        final ImageView silAlertAcButon;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pet_Nametext=itemView.findViewById(R.id.pet_Nametext);
            petBilgi_Bilgitext=itemView.findViewById(R.id.petBilgi_Bilgitext);
            kullanici_petImage=itemView.findViewById(R.id.kullanici_petImage);
            pet_asi_ekleme_cardView=itemView.findViewById(R.id.pet_asi_ekleme_cardView);
            silAlertAcButon=itemView.findViewById(R.id.silAlertAcButon);

        }
    }

    // pet in sahibini arama işlemi
    public void ara(String numara){

        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:"+numara));
        activity.startActivity(intent);

    }

    // yeni aşı eklemek için alert diyalog açma
    public void openAsiEkleAlert(String petid) {

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.asi_ekle_alert_layout, null);

        final TextInputEditText asiname_ekle;
        final Button asiEkleKaydetButon;
        final CalendarView asi_tarih;

        asiname_ekle = view.findViewById(R.id.asiname_ekle);
        asiEkleKaydetButon = view.findViewById(R.id.asiEkleKaydetButon);
        asi_tarih = view.findViewById(R.id.asi_tarih);


        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();

        // aşıya calendar view de tarih işaretleme
        asi_tarih.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {

                DateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
                DateFormat format=new SimpleDateFormat("dd/MM/yyyy");
                tarih=dayOfMonth+"/"+(month+1)+"/"+year;
                try {
                    Date date=inputFormat.parse(tarih);
                    tarih=format.format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Toast.makeText(context, tarih, Toast.LENGTH_LONG).show();
            }
        });

        // aşıyı kaydetme
        asiEkleKaydetButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (asiname_ekle.getText().toString() != "" && tarih != "")

                {

                    // asi ekleme methodunu çağırma
                    asiEkleRequest(musid, petid,asiname_ekle.getText().toString(),tarih,alertDialog);
                    // edittextleri boşaltma
                    asiname_ekle.setText("");



                }else{
                    Toast.makeText(context, "Fill in all fields!", Toast.LENGTH_LONG).show();
                }


            }
        });

        alertDialog.show();
    }

    public void asiEkleRequest(String musid,String petid,String asiisim,String asitarih,final AlertDialog alertDialog){

        Call<AsiEkleModel> request= ManagerAll.getInstance().asiEkle(musid, petid, asiisim, asitarih);
        request.enqueue(new Callback<AsiEkleModel>() {
            @Override
            public void onResponse(Call<AsiEkleModel> call, Response<AsiEkleModel> response) {

                if(response.body().isTf()){

                    Toast.makeText(context, response.body().getText().toString(), Toast.LENGTH_SHORT).show();
                    alertDialog.cancel();
                }else{

                    Toast.makeText(context, response.body().getText().toString(), Toast.LENGTH_SHORT).show();
                    alertDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<AsiEkleModel> call, Throwable t) {

                Toast.makeText(context, Warnings.internetProblemText, Toast.LENGTH_SHORT).show();
                Log.e("asieklekontrol",t.getMessage());

            }
        });
    }

    //  pet silmek için alert diyalog açma
    public void openPetSilAlert(final int position) {

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.pet_sil_layout, null);

        final Button petSilButon,alertPetKapatButon;


        petSilButon=view.findViewById(R.id.petSilButon);
        alertPetKapatButon=view.findViewById(R.id.alertPetKapatButon);



        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();

        // kampanya silme işlemi
        petSilButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                petSilRequest(list.get(position).getPetid().toString(),position);
                alertDialog.cancel();
            }
        });

        // silmeyi iptal etme
        alertPetKapatButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.cancel();

            }
        });


        alertDialog.show();
    }

    public void petSilRequest(String id,int position){

        Call<PetSilModel> request= ManagerAll.getInstance().petSil(id);
        request.enqueue(new Callback<PetSilModel>() {
            @Override
            public void onResponse(Call<PetSilModel> call, Response<PetSilModel> response) {

                if(response.body().isTf()){
                    Toast.makeText(context, response.body().getText(), Toast.LENGTH_SHORT).show();
                    deleteToKullaniciList(position);
                }else{

                    Toast.makeText(context, response.body().getText(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<PetSilModel> call, Throwable t) {

                Toast.makeText(context, Warnings.internetProblemText, Toast.LENGTH_SHORT).show();

            }
        });

    }

    // burdaki listten de kampanyayı silmek için
    public void deleteToKullaniciList(int position){

        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();

    }

}
