package com.example.mysugardiary;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.Serializable;

public class Sugar implements Serializable {


    private int sugarM;
    private int sugarN;
    private int insulin;
    private String info;
    private  String date;
    transient private Bitmap photo;

    public Sugar( int sugarM, int sugarN, int insulin, String info, String date, Bitmap photo) {
        this.sugarM = sugarM;
        this.sugarN = sugarN;
        this.insulin = insulin;
        this.info = info;
        this.date = date;
        this.photo = photo;
    }
    public int getSugarM() {
        return sugarM;
    }

    public int getSugarN() {
        return sugarN;
    }

    public int getInsulin() {
        return insulin;
    }

    public String getInfo() {
        return info;
    }

    public String getDate() {
        return date;
    }

    public Bitmap getPhoto() {
        return photo;
    }


    public void setSugarM(int sugarM) {
        this.sugarM = sugarM;
    }

    public void setSugarN(int sugarN) {
        this.sugarN = sugarN;
    }

    public void setInsulin(int insulin) {
        this.insulin = insulin;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        photo.compress(Bitmap.CompressFormat.JPEG,100,out);
        out.defaultWriteObject();
    }

    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        photo = BitmapFactory.decodeStream(in);
        in.defaultReadObject();
    }
}
