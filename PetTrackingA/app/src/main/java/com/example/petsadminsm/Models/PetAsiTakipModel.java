package com.example.petsadminsm.Models;

public class PetAsiTakipModel {

    public String petisim;
    public String pettur;
    public String petcins;
    public String petresim;
    public String asiisim;
    public String asitarih;
    public String asiId;
    public String kadi;
    public String telefon;
    public String asidurum;
    public boolean tf;


    public String getPetisim() {
        return petisim;
    }

    public void setPetisim(String petisim) {
        this.petisim = petisim;
    }

    public String getPettur() {
        return pettur;
    }

    public void setPettur(String pettur) {
        this.pettur = pettur;
    }

    public String getPetcins() {
        return petcins;
    }

    public void setPetcins(String petcins) {
        this.petcins = petcins;
    }

    public String getPetresim() {
        return petresim;
    }

    public void setPetresim(String petresim) {
        this.petresim = petresim;
    }

    public String getAsiisim() {
        return asiisim;
    }

    public void setAsiisim(String asiisim) {
        this.asiisim = asiisim;
    }

    public String getAsitarih() {
        return asitarih;
    }

    public void setAsitarih(String asitarih) {
        this.asitarih = asitarih;
    }

    public String getAsiId() {
        return asiId;
    }

    public void setAsiId(String asiId) {
        this.asiId = asiId;
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

    public String getAsidurum() {
        return asidurum;
    }

    public void setAsidurum(String asidurum) {
        this.asidurum = asidurum;
    }

    @Override
    public String toString() {
        return "PetAsiTakipModel{" +
                "petisim='" + petisim + '\'' +
                ", pettur='" + pettur + '\'' +
                ", petcins='" + petcins + '\'' +
                ", petresim='" + petresim + '\'' +
                ", asiisim='" + asiisim + '\'' +
                ", asitarih='" + asitarih + '\'' +
                ", asiId='" + asiId + '\'' +
                ", kadi='" + kadi + '\'' +
                ", telefon='" + telefon + '\'' +
                ", asidurum='" + asidurum + '\'' +
                ", tf=" + tf +
                '}';
    }
}
