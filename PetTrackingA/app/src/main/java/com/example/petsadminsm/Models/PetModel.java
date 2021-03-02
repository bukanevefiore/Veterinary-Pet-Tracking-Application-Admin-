package com.example.petsadminsm.Models;

public class PetModel {

    public Object petid;
    public Object petresim;
    public Object petisim;
    public Object pettur;
    public Object petcins;
    public boolean tf;

    public Object getPetid() {
        return petid;
    }

    public void setPetid(Object petid) {
        this.petid = petid;
    }

    public Object getPetresim() {
        return petresim;
    }

    public void setPetresim(Object petresim) {
        this.petresim = petresim;
    }

    public Object getPetisim() {
        return petisim;
    }

    public void setPetisim(Object petisim) {
        this.petisim = petisim;
    }

    public Object getPettur() {
        return pettur;
    }

    public void setPettur(Object pettur) {
        this.pettur = pettur;
    }

    public Object getPetcins() {
        return petcins;
    }

    public void setPetcins(Object petcins) {
        this.petcins = petcins;
    }

    public boolean isTf() {
        return tf;
    }

    public void setTf(boolean tf) {
        this.tf = tf;
    }

    @Override
    public String toString() {
        return "PetModel{" +
                "petid=" + petid +
                ", petresim=" + petresim +
                ", petisim=" + petisim +
                ", pettur=" + pettur +
                ", petcins=" + petcins +
                ", tf=" + tf +
                '}';
    }
}
