package bluetest;

import static org.junit.jupiter.api.Assertions.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import com.blutest.model.Mobil;

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

}
