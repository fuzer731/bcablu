package com.blutest.controller;

import java.awt.image.DataBuffer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Year;
import java.util.ArrayList;
import java.util.Scanner;

import org.apache.http.HttpResponse;

import com.blutest.factory.KendaraanFactory;
import com.blutest.model.Kendaraan;
import com.blutest.model.KendaraanInterface;
import com.blutest.singleton.DB;
import com.blutest.view.MonthlyInstallmentView;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonParser;

public class MonthlyInstallmentController {
	MonthlyInstallmentView kendaraanView;
	KendaraanFactory kendaraanFactory = new KendaraanFactory();
	KendaraanInterface kendaraanInterface = null;
	Scanner sc = new Scanner(System.in);

	public void getView(String type, BufferedReader reader) {
		kendaraanView = new MonthlyInstallmentView();
		kendaraanView.getKendaraanView(type, reader);
	}

	public void getLoad() {
		String endpoint = "https://www.mocky.io/v2/5d11a58d310000b23508cd62";
		String jenisKendaraan, tipeKendaraan;
		int tahunKendaraan, jumlahPinjaman, tenorPinjaman, jumlahDp;

		try {
			URL url = new URL(endpoint);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			con.setRequestMethod("GET");

			int respCode = con.getResponseCode();
			if (respCode == HttpURLConnection.HTTP_OK) {

				BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String line;
				StringBuilder response = new StringBuilder();

				while ((line = reader.readLine()) != null) {
					response.append(line);
				}
				reader.close();

				String jsonData = response.toString();

				jenisKendaraan = extractVehicleType(response.toString(), "vehicleType");
				tipeKendaraan = extractVehicleType(jsonData, "vehicleCondition");
				tahunKendaraan = Integer.parseInt(extractVehicleType(jsonData, "tahunMobil"));
				jumlahDp = Integer.parseInt(extractVehicleType(jsonData, "jumlahDownPayment"));
				jumlahPinjaman = Integer.parseInt(extractVehicleType(jsonData, "jumlahPinjaman"));
				tenorPinjaman = Integer.parseInt(extractVehicleType(jsonData, "tenorCicilan"));

				createVehicle(jenisKendaraan, tipeKendaraan, tahunKendaraan, jumlahPinjaman, tenorPinjaman, jumlahDp);

			} else {
				System.out.println("Error: " + respCode);
			}
			con.disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static String extractVehicleType(String jsonData, String type) {
		try {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode jsn = objectMapper.readTree(jsonData);
			return jsn.get("vehicleModel").get(type).asText();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return null;
	}

	public void createVehicle(String jenisKendaraan, String tipeKendaraan, int tahunKendaraan, int jumlahPinjaman,
			int tenorPinjaman, int jumlahDp) {
		kendaraanInterface = kendaraanFactory.createKendaraan(jenisKendaraan, tipeKendaraan, tahunKendaraan,
				jumlahPinjaman, tenorPinjaman, jumlahDp);
		kendaraanInterface.createVehicle();
		Kendaraan k = (Kendaraan) kendaraanInterface;
		DB.getInstance().addData(k);
	}

	public String getJenisKendaraan(String type, BufferedReader reader) {
		if (type == "cmd")
			return sc.nextLine();

		try {
			return reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public String getTipeKendaraan(String type, BufferedReader reader) {
		if (type == "cmd")
			return sc.nextLine();

		try {
			return reader.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public int getTahunKendaraan(String type, BufferedReader reader, String tipeKendaraan) {
		int input = -1;
		boolean isYear = false;
		try {
			if (type == "cmd") {
				input = sc.nextInt();
				sc.nextLine();
				isYear = isTipeKendaraanBaru(tipeKendaraan, input);
				if (isYear)
					input = -1;
			} else
				input = Integer.parseInt(reader.readLine());

			return input;
		} catch (Exception e) {
			System.out.println("[input tidak valid harus dalam bentuk angka]");
			sc.nextLine();
			return input;
		}
	}

	public boolean isTipeKendaraanBaru(String tipeKendaraan, int tahunKendaraan) {
		int yearValue = Year.now().getValue();

		if (tahunKendaraan < 1000 || tahunKendaraan > 9999)
			System.out.println("[Tahun harus berupa 4 digit angka]");
		else if(tipeKendaraan.equalsIgnoreCase("Baru")) {
			if (tahunKendaraan >= yearValue - 1)
				return false;
			else {
				System.out.println("[Untuk kendaraan baru tahun tidak boleh < " + (yearValue - 1) + "]");
			}
		}
		else {
			return false;
		}
		return true;
	}

	public int getJumlahPinjaman(String type, BufferedReader reader) {
		int input = -1;
		try {
			if (type == "cmd") {
				input = sc.nextInt();
				sc.nextLine();
				if (input < 1 || input > 1000000000) {
					System.out.println("[Jumlah pinjaman harus di antara 1 - 1000000000]");
				}
				return input;
			}

			return Integer.parseInt(reader.readLine());
		} catch (Exception e) {
			System.out.println("[input tidak valid harus dalam bentuk angka]");
			sc.nextLine();
			return input;
		}

	}

	public int getTenorPinjaman(String type, BufferedReader reader) {
		int input = -1;

		try {
			if (type == "cmd") {
				input = sc.nextInt();
				sc.nextLine();
				if (input < 1 || input > 6)
					System.out.println("[Tenor pinjaman harus harus diantara 1 - 6 tahun]");

				return input;
			}
			return Integer.parseInt(reader.readLine());
		} catch (Exception e) {
			System.out.println("[input tidak valid harus dalam bentuk angka]");
			sc.nextLine();
			return input;
		}
	}

	public int getJumlahDp(String type, BufferedReader reader, double totalJumlahPinjaman) {
		int input = -1;

		try {
			if (type == "cmd") {
				input = sc.nextInt();
				sc.nextLine();
				if (input < totalJumlahPinjaman * 0.25) {
					input = -1;
					System.out.println("[Minimal jumlah DP adalah 25% / Rp. " + (totalJumlahPinjaman * 0.25) + "]");
				} else if (input > totalJumlahPinjaman) {
					input = -1;
					System.out.println("[Jumlah DP tidak boleh melebihi jumlah pinjaman]");
				}
				return input;
			}
			return Integer.parseInt(reader.readLine());
		} catch (Exception e) {
			System.out.println("[input tidak valid harus dalam bentuk angka]");
			sc.nextLine();
			return input;
		}
	}

	public ArrayList<Kendaraan> getDataProcess() {
		ArrayList<Kendaraan> arrayList = DB.getInstance().getKendaraanList();
		return arrayList;
	}

}
