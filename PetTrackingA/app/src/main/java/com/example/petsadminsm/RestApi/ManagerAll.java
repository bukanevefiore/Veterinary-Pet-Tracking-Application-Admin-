package com.example.petsadminsm.RestApi;



import com.example.petsadminsm.Models.KampanyaEkleModel;
import com.example.petsadminsm.Models.KampanyaModel;
import com.example.petsadminsm.Models.KampanyaSilModel;

import java.util.List;

import retrofit2.Call;

public class ManagerAll extends BaseManager {

    private static ManagerAll ourInstance=new ManagerAll();

    public static synchronized ManagerAll getInstance()
    {
        return ourInstance;
    }


    public Call<List<KampanyaModel>> getKampanya()
    {
        Call<List<KampanyaModel>> x=getRestApi().getKampanya();
        return x;
    }


    public Call<KampanyaEkleModel> addKampanya(String baslik, String text, String resim)
    {
        Call<KampanyaEkleModel> x=getRestApi().addKampanya(baslik,text,resim);
        return x;
    }

    public Call<KampanyaSilModel> kampanyaSil(String id)
    {
        Call<KampanyaSilModel> x=getRestApi().kampanyaSil(id);
        return x;
    }


}
