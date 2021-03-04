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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petsadminsm.Models.AsiOnaylaModel;
import com.example.petsadminsm.Models.KampanyaModel;
import com.example.petsadminsm.Models.KampanyaSilModel;
import com.example.petsadminsm.Models.PetAsiTakipModel;
import com.example.petsadminsm.R;
import com.example.petsadminsm.RestApi.ManagerAll;
import com.example.petsadminsm.Utils.Warnings;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetAsiTakipAdapter extends RecyclerView.Adapter<PetAsiTakipAdapter.ViewHolder>{

    private Context context;
    private List<PetAsiTakipModel> list;
    private Activity activity;


    public PetAsiTakipAdapter(List<PetAsiTakipModel> list, Context context, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.asi_takip_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {

        holder.asiTakipPetNmae.setText(list.get(position).getPetisim().toString());
        holder.asiTakipBilgi.setText(list.get(position).getKadi().toString()+"'s pet with type " +list.get(position).getPettur().toString()+ " type " +list.get(position).getPetcins().toString()+
                " has the " +list.get(position).getAsiisim().toString()+ " vaccine today.");
        Picasso.get().load("http://localhost/veterinary/" +list.get(position).getPetresim()).resize(100,100).into(holder.asiTakipImage);
/*
       if(list.get(position).getAsidurum().toString().equals("1")){

           holder.asiTakipOkButon.setImageResource(R.drawable.ic_baseline_check_circle_24);
       }
*/
        // pet sahibini arama işlemi
        holder.asiTakipAraButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ara(list.get(position).getTelefon().toString());
            }
        });

        // aşının yapıldığına dair onaylama işlemi
        holder.asiTakipOkButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.asiTakipOkButon.setImageResource(R.drawable.ic_baseline_check_circle_24);
                asiOnaylaRequest(list.get(position).getAsiId().toString(),position);
            }
        });

        // aşı iptal etme
        holder.asiTakipKapatButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                asiIptalRequest(list.get(position).getAsiId().toString(),position);
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
        final TextView asiTakipPetNmae,asiTakipBilgi;
        final CircleImageView asiTakipImage;
        final ImageView asiTakipOkButon,asiTakipAraButon,asiTakipKapatButon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            asiTakipPetNmae=itemView.findViewById(R.id.asiTakipPetNmae);
            asiTakipBilgi=itemView.findViewById(R.id.asiTakipBilgi);
            asiTakipImage=itemView.findViewById(R.id.asiTakipImage);
            asiTakipOkButon=itemView.findViewById(R.id.asiTakipOkButon);
            asiTakipAraButon=itemView.findViewById(R.id.asiTakipAraButon);
            asiTakipKapatButon=itemView.findViewById(R.id.asiTakipKapatButon);




        }
    }



    // burdaki listten de kampanyayı silmek için
    public void deleteToKampanyaList(int position){

        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();

    }

    // pet in sahibini arama işlemi
    public void ara(String numara){

        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:"+numara));
        activity.startActivity(intent);

    }

    public void asiOnaylaRequest(String id,int position){

        Call<AsiOnaylaModel> request=ManagerAll.getInstance().asiOnayla(id);
        request.enqueue(new Callback<AsiOnaylaModel>() {
            @Override
            public void onResponse(Call<AsiOnaylaModel> call, Response<AsiOnaylaModel> response) {

                if (response.body().isTf()){

                    Toast.makeText(context, response.body().getText().toString(), Toast.LENGTH_SHORT).show();
                    deleteToKampanyaList(position);

                }else{

                    Toast.makeText(context, Warnings.internetProblemText, Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AsiOnaylaModel> call, Throwable t) {

                Toast.makeText(context, Warnings.internetProblemText, Toast.LENGTH_SHORT).show();
                Log.e("asionaylakontrol",t.getMessage());

            }
        });


    }

    public void asiIptalRequest(String id,int position){

        Call<AsiOnaylaModel> request=ManagerAll.getInstance().asiIptal(id);
        request.enqueue(new Callback<AsiOnaylaModel>() {
            @Override
            public void onResponse(Call<AsiOnaylaModel> call, Response<AsiOnaylaModel> response) {

                if(response.body().isTf()){

                    Toast.makeText(context, response.body().getText().toString(), Toast.LENGTH_SHORT).show();
                    deleteToKampanyaList(position);
                }else{
                    Toast.makeText(context, Warnings.internetProblemText, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AsiOnaylaModel> call, Throwable t) {

                Toast.makeText(context, Warnings.internetProblemText, Toast.LENGTH_SHORT).show();
                Log.e("asiiptalkontrol",t.getMessage());

            }
        });
    }
}
