package com.blutest.singleton;

import java.util.ArrayList;

import com.blutest.model.Kendaraan;

public class DB {
    private static DB instance;
    private ArrayList<Kendaraan> kendaraanList;

    private DB() {
        kendaraanList = new ArrayList<>();
    }

    public static synchronized DB getInstance() {
        if (instance == null) {
            if (instance == null) {
                instance = new DB();
            }
        }
        return instance;
    }

    public ArrayList<Kendaraan> getKendaraanList() {
        return kendaraanList;
    }
    
    public void addData(Kendaraan k) {
    	kendaraanList.add(k);
    }


}
