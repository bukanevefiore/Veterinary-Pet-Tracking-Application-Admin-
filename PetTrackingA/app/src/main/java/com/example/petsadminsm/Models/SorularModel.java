package com.example.petsadminsm.Models;

public class SorularModel {

    public String musid;
    public String soru;
    public String kadi;
    public String telefon;
    public String soruid;
    public boolean tf;

    public String getSoru() {
        return soru;
    }

    public void setSoru(String soru) {
        this.soru = soru;
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

    public String getSoruid() {
        return soruid;
    }

    public void setSoruid(String soruid) {
        this.soruid = soruid;
    }

    public boolean isTf() {
        return tf;
    }

    public void setTf(boolean tf) {
        this.tf = tf;
    }

    public String getMusid() {
        return musid;
    }

    public void setMusid(String musid) {
        this.musid = musid;
    }

    @Override
    public String toString() {
        return "SorularModel{" +
                "musid='" + musid + '\'' +
                ", soru='" + soru + '\'' +
                ", kadi='" + kadi + '\'' +
                ", telefon='" + telefon + '\'' +
                ", soruid='" + soruid + '\'' +
                ", tf=" + tf +
                '}';
    }
}
