package com.example.petsadminsm.RestApi;



import com.example.petsadminsm.Models.AsiEkleModel;
import com.example.petsadminsm.Models.AsiOnaylaModel;
import com.example.petsadminsm.Models.CevaplaModel;
import com.example.petsadminsm.Models.KampanyaEkleModel;
import com.example.petsadminsm.Models.KampanyaModel;
import com.example.petsadminsm.Models.KampanyaSilModel;
import com.example.petsadminsm.Models.KullanicilarModel;
import com.example.petsadminsm.Models.PetAsiTakipModel;
import com.example.petsadminsm.Models.PetEkleModel;
import com.example.petsadminsm.Models.PetModel;
import com.example.petsadminsm.Models.SorularModel;

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

    public Call<List<SorularModel>> getSorular()
    {
        Call<List<SorularModel>> x=getRestApi().getSorular();
        return x;
    }

    public Call<CevaplaModel> soruCevapla(String musid,String soruid,String text)
    {
        Call<CevaplaModel> x=getRestApi().soruCevapla(musid, soruid, text);
        return x;
    }

    public Call<List<KullanicilarModel>> getKullanicilar()
    {
        Call<List<KullanicilarModel>> x=getRestApi().getKullanicilar();
        return x;
    }

    public Call<List<PetModel>> getPets(String musid)
    {
        Call<List<PetModel>> x=getRestApi().getPets(musid);
        return x;
    }

    public Call<PetEkleModel> petEkle(String musid, String petisim, String pettur,String petcins,String petresim)
    {
        Call<PetEkleModel> x=getRestApi().petEkle(musid, petisim, pettur,petcins,petresim);
        return x;
    }


    public Call<AsiEkleModel> asiEkle(String musid, String petid, String asiisim, String asitarih)
    {
        Call<AsiEkleModel> x=getRestApi().asiEkle(musid,petid,asiisim,asitarih);
        return x;
    }


}
