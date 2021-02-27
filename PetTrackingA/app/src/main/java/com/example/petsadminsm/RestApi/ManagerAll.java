package com.example.petsadminsm.RestApi;



import com.example.petsadminsm.Models.KampanyaModel;

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


}
