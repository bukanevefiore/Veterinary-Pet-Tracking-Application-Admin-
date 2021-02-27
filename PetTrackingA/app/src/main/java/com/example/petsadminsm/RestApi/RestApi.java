package com.example.petsadminsm.RestApi;


import com.example.petsadminsm.Models.KampanyaModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface RestApi {


    @GET("/veterinary/adminkampanya.php")
    Call<List<KampanyaModel>> getKampanya();



}
