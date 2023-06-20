package com.blutest.factory;

import com.blutest.model.KendaraanInterface;
import com.blutest.model.Mobil;
import com.blutest.model.Motor;

public class KendaraanFactory {
	public KendaraanInterface createKendaraan(String jenisKendaraan, String tipeKendaraan, int tahunKendaraan, int jumlahPinjaman, int tenorPinjaman, int jumlahDp) {
		if (jenisKendaraan.equalsIgnoreCase("mobil"))
            return new Mobil(jenisKendaraan, tipeKendaraan, tahunKendaraan, jumlahPinjaman, tenorPinjaman, jumlahDp);
        return new Motor(jenisKendaraan, tipeKendaraan, tahunKendaraan, jumlahPinjaman, tenorPinjaman, jumlahDp); 
    }
}
