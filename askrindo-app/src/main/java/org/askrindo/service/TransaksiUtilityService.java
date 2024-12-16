package org.askrindo.service;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class TransaksiUtilityService {

    public String generateNomorSertifikat(int nomorUrut, String kodeProduk, LocalDate tanggalTransaksi) {
        String bulanRomawi = convertToRomawi(tanggalTransaksi.getMonthValue());
        String tahun = String.valueOf(tanggalTransaksi.getYear());
        return String.format("%05d/%s/%s/%s", nomorUrut, kodeProduk, bulanRomawi, tahun);
    }

    private String convertToRomawi(int bulan) {
        String[] romawi = {"I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX", "X", "XI", "XII"};
        return romawi[bulan - 1];
    }

    public int hitungJumlahHariPertanggungan(LocalDate jangkaWaktuAwal, LocalDate jangkaWaktuAkhir) {
        return (int) ChronoUnit.DAYS.between(jangkaWaktuAwal, jangkaWaktuAkhir) + 1;
    }

    public BigDecimal hitungPremiProrate(BigDecimal premiPaket, int jumlahHariPertanggungan) {
        final int HARI_DALAM_1_TAHUN = 365;
        return premiPaket.multiply(BigDecimal.valueOf(jumlahHariPertanggungan))
                .divide(BigDecimal.valueOf(HARI_DALAM_1_TAHUN), 2, RoundingMode.HALF_UP);
    }


}
