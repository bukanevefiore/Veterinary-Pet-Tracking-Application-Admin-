package com.example.petsadminsm.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petsadminsm.Fragments.KullaniciPetFragment;
import com.example.petsadminsm.Models.KullanicilarModel;
import com.example.petsadminsm.Models.PetModel;
import com.example.petsadminsm.R;
import com.example.petsadminsm.Utils.ChangeFragments;
import com.google.gson.internal.$Gson$Preconditions;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PetsAdapter extends RecyclerView.Adapter<PetsAdapter.ViewHolder>{

    private Context context;
    private List<PetModel> list;
    private Activity activity;
    ChangeFragments changeFragments;
    private String musid;


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

        Picasso.get().load("http://192.168.1.4/veterinary/"+list.get(position).getPetresim().toString()).resize(70,70)
                .into(holder.kullanici_petImage);

        // pete yeni aşı ekleme
        holder.pet_asi_ekleme_cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
        TextView pet_Nametext,petBilgi_Bilgitext;
        CircleImageView kullanici_petImage;
        private CardView pet_asi_ekleme_cardView;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            pet_Nametext=itemView.findViewById(R.id.pet_Nametext);
            petBilgi_Bilgitext=itemView.findViewById(R.id.petBilgi_Bilgitext);
            kullanici_petImage=itemView.findViewById(R.id.kullanici_petImage);
            pet_asi_ekleme_cardView=itemView.findViewById(R.id.pet_asi_ekleme_cardView);

        }
    }

    // pet in sahibini arama işlemi
    public void ara(String numara){

        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:"+numara));
        activity.startActivity(intent);

    }

/*
    // yeni kampanya eklemek için alert diyalog açma
    public void openKampanyaSilAlert(final int position) {

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.kampanya_sil_layout, null);

        final Button kampanyaSilButon,alertKapatButon;

        kampanyaSilButon=view.findViewById(R.id.kampanyaSilButon);
        alertKapatButon=view.findViewById(R.id.alertKapatButon);



        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();

        // kampanya silme işlemi
        kampanyaSilButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                kampanyaSilRequest(list.get(position).getId().toString(),position);
                alertDialog.cancel();
            }
        });

        // silmeyi iptal etme
        alertKapatButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.cancel();

            }
        });


        alertDialog.show();
    }


    // burdaki listten de kampanyayı silmek için
    public void deleteToKampanyaList(int position){

        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();

    }
*/

}
