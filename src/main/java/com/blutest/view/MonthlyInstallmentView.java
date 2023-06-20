package com.blutest.view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.Year;
import java.util.ArrayList;
import java.util.Scanner;

import com.blutest.controller.MonthlyInstallmentController;
import com.blutest.model.Kendaraan;
import com.blutest.model.KendaraanInterface;
import com.blutest.model.Mobil;
import com.blutest.model.Motor;
import com.blutest.factory.KendaraanFactory;

public class MonthlyInstallmentView {
	static Scanner sc = new Scanner(System.in);

    MonthlyInstallmentController controller = new MonthlyInstallmentController();
    
	
	private static void mainScreen() {
		System.out.println();
		System.out.println("-------------------CREDIT SIMULATOR----------------");
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("1. Simulasi Kredit");
		System.out.println("2. Load Data");
		System.out.println("3. Melihat Status Pengajuan");
		System.out.println("4. Keluar");
		System.out.println("----------------------------------------------------");
	}
	
	public void getKendaraanView(String type, BufferedReader reader) {

		int input = 1;
		System.out.println();
		if(type == "cmd") {
			while((input >= 1 && input <= 4) || input < 1 || input > 4) {
				mainScreen();
            	System.out.print("> ");input = sc.nextInt();sc.nextLine();
            	if(input < 1 || input > 4)
            		System.out.println("[input tidak valid]");
            	else if(input == 1)
            		loadMenu(type, reader);
            	else if(input == 2)
            		controller.getLoad();
            	else if(input == 3)
            		viewDataProcess();
            	else if(input == 4)
            		break;
           }
		} else {
	        try {
                String line;
                while ((line = reader.readLine()) != null) {
                	mainScreen();
                    System.out.print("> ");
                    input = Integer.parseInt(line);
                	if(input < 1 || input > 4)
                		System.out.println("[input tidak valid]");
                	else if(input == 1)
                		loadMenu(type, reader);
                	else if(input == 2)
                		controller.getLoad();
                	else if(input == 3)
                		viewDataProcess();
                	else if(input == 4)
                		break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
		}
	}
	private void viewDataProcess() {
		ArrayList<Kendaraan> arrayList = controller.getDataProcess();
		System.out.println("------------------Status Pengajuan------------------");
		if(arrayList.size() > 0) {
			for(Kendaraan k : arrayList) {
				System.out.println("Tipe Kendaraan  : " + k.getTipeKendaraan());
				System.out.println("Kendaraan       : " + k.getJenisKendaraan());
				System.out.println("Tahun Kendaraan : " + k.getTahunKendaraan());
				System.out.println("Tenor Cicilan   : " + k.getTenorPinjaman());
				System.out.println("Jumlah Pinjaman : " + k.getJumlahPinjaman());
				System.out.println("Jumlah DP       : " + k.getJumlahDp());
				System.out.println("-------------------------------------------------------");
			}
			
		} else {
			System.out.println("------------------Belum Ada Pengajuan------------------");	
		}
		System.out.println();
	}
	
	private void loadMenu(String type, BufferedReader reader) {
		String jenisKendaraan = "", tipeKendaraan = "";
		int tahunKendaraan = -1, tenorPinjaman = -1, jumlahDp = -1, jumlahPinjaman = -1;
		while(!jenisKendaraan.equalsIgnoreCase("Mobil") && !jenisKendaraan.equalsIgnoreCase("Motor")){
			System.out.println("Input Jenis Kendaraan [Mobil/Motor]");
			System.out.print(">");
			jenisKendaraan = controller.getJenisKendaraan(type, reader);
		}
		

		while(!tipeKendaraan.equalsIgnoreCase("Bekas") && !tipeKendaraan.equalsIgnoreCase("Baru")){
			System.out.println("Input Tipe Kendaraan [Baru/Bekas]");
			System.out.print(">");
			tipeKendaraan = controller.getTipeKendaraan(type, reader);
		}
		
	    do {
			System.out.println("Input Tahun Kendaraan");
			System.out.print(">");
			tahunKendaraan = controller.getTahunKendaraan(type, reader, tipeKendaraan);
		} while (tahunKendaraan < 0 || (tahunKendaraan > 9999 || tahunKendaraan <= 1000));
	    
	    
	    do {
	    	System.out.println("Input Jumlah Pinjaman Total");
	    	System.out.print(">");
	    	jumlahPinjaman = controller.getJumlahPinjaman(type, reader);	
	    }while(jumlahPinjaman < 1 || jumlahPinjaman > 1000000000);
	    
	    
	    while(tenorPinjaman < 1 || tenorPinjaman > 6) {
	    	System.out.println("Input Tenor Pinjaman 1-6 Tahun");
	    	System.out.print(">");
	    	tenorPinjaman = controller.getTenorPinjaman(type, reader);
	    }
	    
	    double totalJumlahPinjaman = jumlahPinjaman;
	    while(jumlahDp < totalJumlahPinjaman*0.25) {
	    	System.out.println("Input Jumlah DP");
	    	System.out.print(">");
	    	jumlahDp = controller.getJumlahDp(type, reader, totalJumlahPinjaman);

	    }
	    
	    controller.createVehicle(jenisKendaraan, tipeKendaraan, tahunKendaraan, jumlahPinjaman, tenorPinjaman, jumlahDp);
	}


}
