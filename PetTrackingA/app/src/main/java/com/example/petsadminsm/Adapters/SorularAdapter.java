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
import androidx.recyclerview.widget.RecyclerView;

import com.example.petsadminsm.Models.AsiOnaylaModel;
import com.example.petsadminsm.Models.CevaplaModel;
import com.example.petsadminsm.Models.PetAsiTakipModel;
import com.example.petsadminsm.Models.SorularModel;
import com.example.petsadminsm.R;
import com.example.petsadminsm.RestApi.ManagerAll;
import com.example.petsadminsm.Utils.Warnings;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SorularAdapter extends RecyclerView.Adapter<SorularAdapter.ViewHolder>{

    private Context context;
    private List<SorularModel> list;
    private Activity activity;



    public SorularAdapter(List<SorularModel> list, Context context, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.sorular_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {

        holder.kullanici_text.setText(list.get(position).getKadi().toString());
        holder.soru_text.setText(list.get(position).getSoru().toString());

        holder.soruAraButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.soruAraButon.setImageResource(R.drawable.soru_phone2);
                ara(list.get(position).getTelefon().toString());
            }
        });

        holder.soruCevaplaButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.soruCevaplaButon.setImageResource(R.drawable.ic_baseline_message_25);
                openCevaplamaAlert(list.get(position).getMusid().toString(),list.get(position).getSoruid().toString(),
                        position,list.get(position).getSoru().toString());

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
        final TextView kullanici_text,soru_text;
        final ImageView soruAraButon,soruCevaplaButon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            kullanici_text=itemView.findViewById(R.id.kullanici_text);
            soru_text=itemView.findViewById(R.id.soru_text);
            soruAraButon=itemView.findViewById(R.id.soruAraButon);
            soruCevaplaButon=itemView.findViewById(R.id.soruCevaplaButon);



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

    // gelen soruları cevaplamak için alert dialog açma
    public void openCevaplamaAlert(final String musid,final String soruid,final int position,String soru){

        LayoutInflater layoutInflater=activity.getLayoutInflater();
        View view=layoutInflater.inflate(R.layout.cevapla_alert_layout,null);

        TextInputEditText cevapVerEditText;
        TextView cevapLayout_sorutext;
        Button cevapVerButon;

        cevapVerEditText=view.findViewById(R.id.cevapVerEditText);
        cevapVerButon=view.findViewById(R.id.cevapVerButon);
        cevapLayout_sorutext=view.findViewById(R.id.cevapLayout_sorutext);
        cevapLayout_sorutext.setText(soru);  // tıklanan soruyu alertte gözterme

        AlertDialog.Builder alert=new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog=alert.create();

        // alert layout içindeki buton
        cevapVerButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String cevap=cevapVerEditText.getText().toString();
                cevapVerEditText.setText("");
                alertDialog.cancel();
                soruCevaplaRequest(musid,soruid,cevap,alertDialog,position);

            }
        });

        alertDialog.show();
    }

    public void soruCevaplaRequest(String musid,String soruid,String text,AlertDialog alertDialog,int position){

        Call<CevaplaModel> request=ManagerAll.getInstance().soruCevapla(musid, soruid, text);
        request.enqueue(new Callback<CevaplaModel>() {
            @Override
            public void onResponse(Call<CevaplaModel> call, Response<CevaplaModel> response) {

                if(response.body().isTf()){

                    Toast.makeText(context, response.body().getText().toString(), Toast.LENGTH_LONG).show();
                    alertDialog.cancel();
                    deleteToKampanyaList(position); // listede cevaplanmamış sorulardan çıkartma işlemi
                }else{

                    Toast.makeText(context, response.body().getText().toString(), Toast.LENGTH_LONG).show();
                    alertDialog.cancel();
                }
            }

            @Override
            public void onFailure(Call<CevaplaModel> call, Throwable t) {

                Toast.makeText(context, Warnings.internetProblemText, Toast.LENGTH_SHORT).show();
                Log.e("cevapkontrol",t.getMessage());
            }
        });
    }

}
