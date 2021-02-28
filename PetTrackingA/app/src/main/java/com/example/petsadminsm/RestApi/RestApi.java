package com.example.petsadminsm.RestApi;


import com.example.petsadminsm.Models.AsiOnaylaModel;
import com.example.petsadminsm.Models.CevaplaModel;
import com.example.petsadminsm.Models.KampanyaEkleModel;
import com.example.petsadminsm.Models.KampanyaModel;
import com.example.petsadminsm.Models.KampanyaSilModel;
import com.example.petsadminsm.Models.PetAsiTakipModel;
import com.example.petsadminsm.Models.SorularModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface RestApi {


    @GET("/veterinary/adminkampanya.php")
    Call<List<KampanyaModel>> getKampanya();

    @FormUrlEncoded
    @POST("/veterinary/adminkampanyaekle.php")
    Call<KampanyaEkleModel> addKampanya(@Field("baslik") String baslik, @Field("text") String text, @Field("resim") String resim);

    @FormUrlEncoded
    @POST("/veterinary/adminkampanyasil.php")
    Call<KampanyaSilModel> kampanyaSil(@Field("id") String id);

    @FormUrlEncoded
    @POST("/veterinary/adminasitakip.php")
    Call<List<PetAsiTakipModel>> getPetAsiTakip(@Field("asitarih") String asitarih);

    @FormUrlEncoded
    @POST("/veterinary/adminasionayla.php")
    Call<AsiOnaylaModel> asiOnayla(@Field("id") String id);

    @FormUrlEncoded
    @POST("/veterinary/adminasiiptal.php")
    Call<AsiOnaylaModel> asiIptal(@Field("id") String id);

    @GET("/veterinary/adminsorular.php")
    Call<List<SorularModel>> getSorular();

    @FormUrlEncoded
    @POST("/veterinary/adminsorucevapla.php")
    Call<CevaplaModel> soruCevapla(@Field("musid") String musid,@Field("soruid") String soruid,@Field("text") String text);
}
