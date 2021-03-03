package com.example.petsadminsm.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

import com.example.petsadminsm.Fragments.KullaniciPetFragment;
import com.example.petsadminsm.Models.KampanyaModel;
import com.example.petsadminsm.Models.KampanyaSilModel;
import com.example.petsadminsm.Models.KullaniciSilModel;
import com.example.petsadminsm.Models.KullanicilarModel;
import com.example.petsadminsm.R;
import com.example.petsadminsm.RestApi.ManagerAll;
import com.example.petsadminsm.Utils.ChangeFragments;
import com.example.petsadminsm.Utils.Warnings;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>{

    private Context context;
    private List<KullanicilarModel> list;
    private Activity activity;
    ChangeFragments changeFragments;


    public UsersAdapter(List<KullanicilarModel> list, Context context, Activity activity) {
        this.context = context;
        this.list = list;
        this.activity=activity;
        changeFragments=new ChangeFragments(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.kullanicilar_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {

        holder.kullanici_Nametext.setText(list.get(position).getKadi().toString());

        // tıklanan kullanıcıya ait tüm petlerin listelenmesi
        holder.kullaniciPetlerButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // kullanıcı petleri fragmentine geçerken kullnaıcı id sinin paremetre gönderiyoruz
                changeFragments.changeWithParemeters(new KullaniciPetFragment(),list.get(position).getId().toString());


            }
        });

        holder.kullaniciAraButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                holder.kullaniciAraButon.setImageResource(R.drawable.ic_baseline_perm_phone_msg_25);
                ara(list.get(position).getTelefon().toString());
            }
        });
        // kullanıcı silme işlemi
        holder.kullanicilarLayoutSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openKullaniciSilAlert(position);
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
        TextView kullanici_Nametext;
        ImageView kullaniciAraButon;
        Button kullaniciPetlerButon;
        CardView kullanicilarLayoutSil;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            kullanici_Nametext=itemView.findViewById(R.id.kullanici_Nametext);
            kullaniciPetlerButon=itemView.findViewById(R.id.kullaniciPetlerButon);
            kullaniciAraButon=itemView.findViewById(R.id.kullaniciAraButon);
            kullanicilarLayoutSil=itemView.findViewById(R.id.kullanicilarLayoutSil);


        }
    }

    // pet in sahibini arama işlemi
    public void ara(String numara){

        Intent intent=new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("tel:"+numara));
        activity.startActivity(intent);

    }


    //  user silmek için alert diyalog açma
    public void openKullaniciSilAlert(final int position) {

        LayoutInflater layoutInflater = activity.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.kullanici_sil_layout, null);

        final Button kullaniciSilButon,alertKullaniciKapatButon;

        kullaniciSilButon=view.findViewById(R.id.kullaniciSilButon);
        alertKullaniciKapatButon=view.findViewById(R.id.alertKullaniciKapatButon);



        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setView(view);
        alert.setCancelable(true);
        final AlertDialog alertDialog = alert.create();

        // kampanya silme işlemi
        kullaniciSilButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                kullaniciSilRequest(list.get(position).getId().toString(),position);
                alertDialog.cancel();
            }
        });

        // silmeyi iptal etme
        alertKullaniciKapatButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                alertDialog.cancel();

            }
        });


        alertDialog.show();
    }

    public void kullaniciSilRequest(String id,int position){

        Call<KullaniciSilModel> request= ManagerAll.getInstance().kullaniciSil(id);
        request.enqueue(new Callback<KullaniciSilModel>() {
            @Override
            public void onResponse(Call<KullaniciSilModel> call, Response<KullaniciSilModel> response) {

                if(response.body().isTf()){
                    Toast.makeText(context, response.body().getText(), Toast.LENGTH_SHORT).show();
                    deleteToKullaniciList(position);
                }else{

                    Toast.makeText(context, response.body().getText(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<KullaniciSilModel> call, Throwable t) {

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
