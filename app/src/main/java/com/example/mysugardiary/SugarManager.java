package com.example.mysugardiary;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SugarManager {


    public static SugarManager instance;
    private Context context;
    private ArrayList<Sugar> sugars = new ArrayList<>();

    static final String FILE_NAME = "sugars.dat";

    private SugarManager(Context context) {
        this.context = context;

        try {
            FileInputStream fis = context.openFileInput(FILE_NAME);
            ObjectInputStream ois  = new ObjectInputStream(fis);
            sugars = (ArrayList<Sugar>)ois.readObject();

            ois.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public static SugarManager getInstance(Context context) {
        if(instance==null)
            instance = new SugarManager(context);

        return instance;
    }

    public Sugar getSugar(int position) {

        if(position<sugars.size())
            return sugars.get(position);

        return null;
    }

    public void addSugar(Sugar sugar) {

        sugars.add(sugar);
        saveSugars();
    }

    public void removeSugar(int position) {
        if(position<sugars.size())
            sugars.remove(position);
        saveSugars();
    }

    private void saveSugars() {

        try {
            FileOutputStream fos = context.openFileOutput(FILE_NAME,Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(sugars);

            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Sugar> getSugars() {
        return sugars;
    }

}
