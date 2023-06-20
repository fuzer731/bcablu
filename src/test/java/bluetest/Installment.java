package bluetest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import com.blutest.model.Kendaraan;
import com.blutest.model.Mobil;
import com.blutest.model.Motor;
import com.blutest.singleton.DB;

class Installment {

	@Test
	//Melihat jika pembayaran tahun pertama sesuai
	void calcTahunPertama() {
        String jenisKendaraan = "Mobil";
        String tipeKendaraan = "Baru";
        int tahunKendaraan = 2019;
        int jumlahPinjaman = 100000000;
        int tenorPinjaman = 5;
        int jumlahDp = 25000000;
        
        double bayarBulanan;
		bayarBulanan = (jumlahPinjaman - jumlahDp) / tenorPinjaman;
		bayarBulanan = (bayarBulanan + (bayarBulanan * 0.08))/12;

        Mobil mobil = new Mobil(jenisKendaraan, tipeKendaraan, tahunKendaraan, jumlahPinjaman, tenorPinjaman, jumlahDp);
        
        assertEquals(bayarBulanan, mobil.calcBayarTahunPertama());
	}
	
	@Test
	//Simulasi pengetesan jika kredit simulasi telah dijalankan, maka dia akan tersimpan pada "DB"
	void retrieveList() {
		DB singleton = DB.getInstance();
        String jenisKendaraan, tipeKendaraan;
        int tahunKendaraan, jumlahPinjaman, tenorPinjaman, jumlahDp;
        
        jenisKendaraan = "Mobil";
        tipeKendaraan = "Baru";
        tahunKendaraan = 2019;
        jumlahPinjaman = 100000000;
        tenorPinjaman = 5;
        jumlahDp = 25000000;
        
        Kendaraan mobil = new Mobil(jenisKendaraan, tipeKendaraan, tahunKendaraan, jumlahPinjaman, tenorPinjaman, jumlahDp);
        
        jenisKendaraan = "Motor";
        tipeKendaraan = "Bekas";
        tahunKendaraan = 2015;
        jumlahPinjaman = 12000000;
        tenorPinjaman = 2;
        jumlahDp = 7500000;
        
        Kendaraan motor = new Motor(jenisKendaraan, tipeKendaraan, tahunKendaraan, jumlahPinjaman, tenorPinjaman, jumlahDp);
		
		singleton.getKendaraanList().add(mobil);
        singleton.getKendaraanList().add(motor);
        
        ArrayList<Kendaraan> kendaraanList = singleton.getKendaraanList();
        
        //Cek jika kendaraanList benar tidak kosong
        assertNotNull(kendaraanList);
        
        //Cek jika kendaraanList benar = 2
        assertEquals(2, kendaraanList.size());
        
        //Cek jika kendaraanList ada motor
        assertTrue(kendaraanList.contains(motor));
        
        //Cek jika kendaraanList ada mobil
        assertTrue(kendaraanList.contains(mobil));
	}

}
