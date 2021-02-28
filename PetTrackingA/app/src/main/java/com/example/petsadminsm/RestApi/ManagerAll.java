package com.example.petsadminsm.RestApi;



import com.example.petsadminsm.Models.AsiOnaylaModel;
import com.example.petsadminsm.Models.KampanyaEkleModel;
import com.example.petsadminsm.Models.KampanyaModel;
import com.example.petsadminsm.Models.KampanyaSilModel;
import com.example.petsadminsm.Models.PetAsiTakipModel;

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

    public Call<List<PetAsiTakipModel>> getPetAsiTakip(String asitarih)
    {
        Call<List<PetAsiTakipModel>> x=getRestApi().getPetAsiTakip(asitarih);
        return x;
    }

    public Call<AsiOnaylaModel> asiOnayla(String id)
    {
        Call<AsiOnaylaModel> x=getRestApi().asiOnayla(id);
        return x;
    }

    public Call<AsiOnaylaModel> asiIptal(String id)
    {
        Call<AsiOnaylaModel> x=getRestApi().asiIptal(id);
        return x;
    }
}
