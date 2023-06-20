package com.blutest.model;

public abstract class Kendaraan implements KendaraanInterface{
	String jenisKendaraan, tipeKendaraan;
	int tahunKendaraan, jumlahPinjaman, tenorPinjaman, jumlahDp;
	
	public Kendaraan(String jenisKendaraan, String tipeKendaraan, int tahunKendaraan, int jumlahPinjaman,
			int tenorPinjaman, int jumlahDp) {
		super();
		this.jenisKendaraan = jenisKendaraan;
		this.tipeKendaraan = tipeKendaraan;
		this.tahunKendaraan = tahunKendaraan;
		this.jumlahPinjaman = jumlahPinjaman;
		this.tenorPinjaman = tenorPinjaman;
		this.jumlahDp = jumlahDp;
	}

	public String getJenisKendaraan() {
		return jenisKendaraan;
	}

	public String getTipeKendaraan() {
		return tipeKendaraan;
	}

	public int getTahunKendaraan() {
		return tahunKendaraan;
	}

	public int getJumlahPinjaman() {
		return jumlahPinjaman;
	}

	public int getTenorPinjaman() {
		return tenorPinjaman;
	}

	public int getJumlahDp() {
		return jumlahDp;
	}

	public abstract void createVehicle();

}
