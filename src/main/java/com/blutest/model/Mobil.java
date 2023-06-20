package com.blutest.model;

import java.util.ArrayList;

public class Mobil extends Kendaraan{

	double baseInterestRate = 0.08;
	
	public Mobil(String jenisKendaraan, String tipeKendaraan, int tahunKendaraan, int jumlahPinjaman, int tenorPinjaman,
			int jumlahDp) {
		super(jenisKendaraan, tipeKendaraan, tahunKendaraan, jumlahPinjaman, tenorPinjaman, jumlahDp);
	}

	@Override
	public void createVehicle() {
		double bayarBulanan, sukuBunga;
		bayarBulanan = calcBayarTahunPertama();
		sukuBunga = baseInterestRate;		
		
		for(int i = 1; i <= tenorPinjaman; i++) {
			System.out.printf("tahun %d : Rp. %.1f/bln, Suku Bunga : %.2f\n", i, bayarBulanan, sukuBunga * 100);
            if (i % 2 == 0) {
                sukuBunga += 0.005;
            } else {
            	sukuBunga += 0.001;
            }
            bayarBulanan =  (bayarBulanan + (bayarBulanan * sukuBunga));
		}
	}
	
	public double calcBayarTahunPertama() {
		double bayarBulanan;
		bayarBulanan = (jumlahPinjaman - jumlahDp) / tenorPinjaman;
		bayarBulanan = (bayarBulanan + (bayarBulanan * baseInterestRate))/12;
		return bayarBulanan;
	}
	


}
