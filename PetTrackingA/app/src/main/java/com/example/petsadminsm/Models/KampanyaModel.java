package com.example.petsadminsm.Models;

public class KampanyaModel {

    public String id;
    public String baslik;
    public String text;
    public String resim;
    public boolean tf;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBaslik() {
        return baslik;
    }

    public void setBaslik(String baslik) {
        this.baslik = baslik;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getResim() {
        return resim;
    }

    public void setResim(String resim) {
        this.resim = resim;
    }

    public boolean isTf() {
        return tf;
    }

    public void setTf(boolean tf) {
        this.tf = tf;
    }

    @Override
    public String toString() {
        return "KampanyaModel{" +
                "id='" + id + '\'' +
                ", baslik='" + baslik + '\'' +
                ", text='" + text + '\'' +
                ", resim='" + resim + '\'' +
                ", tf=" + tf +
                '}';
    }
}
