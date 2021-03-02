package com.example.petsadminsm.Models;

public class KullanicilarModel {

    public String id;
    public String kadi;
    public String telefon;
    public boolean tf;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKadi() {
        return kadi;
    }

    public void setKadi(String kadi) {
        this.kadi = kadi;
    }

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public boolean isTf() {
        return tf;
    }

    public void setTf(boolean tf) {
        this.tf = tf;
    }

    @Override
    public String toString() {
        return "KullanicilarModel{" +
                "id='" + id + '\'' +
                ", kadi='" + kadi + '\'' +
                ", telefon='" + telefon + '\'' +
                ", tf=" + tf +
                '}';
    }
}
