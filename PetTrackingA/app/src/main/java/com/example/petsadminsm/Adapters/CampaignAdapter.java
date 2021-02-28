package com.example.petsadminsm.Adapters;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.petsadminsm.Models.KampanyaModel;
import com.example.petsadminsm.Models.KampanyaSilModel;
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

public class CampaignAdapter extends RecyclerView.Adapter<CampaignAdapter.ViewHolder>{

    private Context context;
    private List<KampanyaModel> list;
    private Activity activity;


    public CampaignAdapter(List<KampanyaModel> list, Context context,Activity activity) {
        this.context = context;
        this.list = list;
        this.activity=activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.kampanya_view_item_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,final int position) {

        holder.kampanya_baslik.setText(list.get(position).getBaslik().toString());
        holder.kampanya_text.setText(list.get(position).getText().toString());
        Picasso.get().load("http://192.168.1.4/veterinary/" +list.get(position).getResim()).resize(100,100).into(holder.kampanya_image);

        holder.kampanyaLayoutSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openKampanyaSilAlert(position);

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
        TextView kampanya_baslik,kampanya_text;
        CircleImageView kampanya_image;
        CardView kampanyaLayoutSil;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            kampanya_baslik=itemView.findViewById(R.id.kampanya_baslik);
            kampanya_text=itemView.findViewById(R.id.kampanya_text);
            kampanya_image=itemView.findViewById(R.id.kampanya_image);
            kampanyaLayoutSil=itemView.findViewById(R.id.kampanyaLayoutSil);

        }
    }


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

    public void kampanyaSilRequest(String id,int position){

        Call<KampanyaSilModel> request= ManagerAll.getInstance().kampanyaSil(id);
        request.enqueue(new Callback<KampanyaSilModel>() {
            @Override
            public void onResponse(Call<KampanyaSilModel> call, Response<KampanyaSilModel> response) {

                if(response.body().isTf()){
                    Toast.makeText(context, response.body().getText(), Toast.LENGTH_SHORT).show();
                    deleteToKampanyaList(position);
                }else{

                    Toast.makeText(context, response.body().getText(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<KampanyaSilModel> call, Throwable t) {

                Toast.makeText(context, Warnings.internetProblemText, Toast.LENGTH_SHORT).show();

            }
        });

    }

    // burdaki listten de kampanyayı silmek için
    public void deleteToKampanyaList(int position){

        list.remove(position);
        notifyItemRemoved(position);
        notifyDataSetChanged();

    }
}
