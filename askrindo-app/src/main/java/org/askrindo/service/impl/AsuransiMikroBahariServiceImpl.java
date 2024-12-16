package org.askrindo.service.impl;

import org.askrindo.domain.AsuransiMikroBahari;
import org.askrindo.domain.MasterLookup;
import org.askrindo.dto.AsuransiMikroBahariDto;
import org.askrindo.repository.AsuransiMikroBahariRepository;
import org.askrindo.service.AsuransiMikroBahariService;
import org.askrindo.service.MasterLookupService;
import org.askrindo.service.TransaksiUtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AsuransiMikroBahariServiceImpl implements AsuransiMikroBahariService {

    @Autowired
    private AsuransiMikroBahariRepository asuransiMikroBahariRepository;

    @Autowired
    private MasterLookupService masterLookupService;

    @Autowired
    private TransaksiUtilityService utilityService;

    @Override
    public AsuransiMikroBahari save(AsuransiMikroBahariDto asuransiMikroBahariDto) {

        if (!asuransiMikroBahariDto.getJangkaWaktuAwal().isEqual(LocalDate.now())) {
            throw new IllegalArgumentException("Jangka Waktu Awal hanya bisa diisi dengan hari ini.");
        }

        if (asuransiMikroBahariDto.getJangkaWaktuAkhir().isBefore(asuransiMikroBahariDto.getJangkaWaktuAwal())) {
            throw new IllegalArgumentException("Jangka Waktu Akhir tidak boleh lebih awal dari Jangka Waktu Awal.");
        }

        LocalDate maxEndDate = asuransiMikroBahariDto.getJangkaWaktuAwal().plusYears(1);
        if (asuransiMikroBahariDto.getJangkaWaktuAkhir().isAfter(maxEndDate)) {
            throw new IllegalArgumentException("Jangka Waktu Akhir tidak boleh lebih dari 1 tahun dari Jangka Waktu Awal.");
        }

        AsuransiMikroBahari asuransiMikroBahari = new AsuransiMikroBahari();
        asuransiMikroBahari.setNamaTertanggung(asuransiMikroBahariDto.getNamaTertanggung());
        asuransiMikroBahari.setNomorKTP(asuransiMikroBahariDto.getNomorKTP());
        asuransiMikroBahari.setEmail(asuransiMikroBahariDto.getEmail());
        asuransiMikroBahari.setNomorTelepon(asuransiMikroBahariDto.getNomorTelepon());
        asuransiMikroBahari.setJangkaWaktuAwal(asuransiMikroBahariDto.getJangkaWaktuAwal());
        asuransiMikroBahari.setJangkaWaktuAkhir(asuransiMikroBahariDto.getJangkaWaktuAkhir());
        asuransiMikroBahari.setNoIDKapal(asuransiMikroBahariDto.getNoIDKapal());

        MasterLookup masterLookupJenisKapal = masterLookupService.cekMasterLookup(asuransiMikroBahariDto.getJenisKapal(), "jenis_kapal")
                .orElseThrow(() -> new RuntimeException("Tidak ada Jenis Kapal dengan label : " + asuransiMikroBahariDto.getJenisKapal() + " di grup Jenis Kapal"));
        asuransiMikroBahari.setJenisKapal(masterLookupJenisKapal.getLookupKey());

        MasterLookup masterLookupKonstruksiKapal = masterLookupService.cekMasterLookup(asuransiMikroBahariDto.getKonstruksiKapal(), "MARINE_HULL_CONSTRUCTION")
                .orElseThrow(() -> new RuntimeException("Tidak ada Konstruksi Kapal dengan label : " + asuransiMikroBahariDto.getKonstruksiKapal() + " di grup Marine Hull Construction"));
        asuransiMikroBahari.setKonstruksiKapal(masterLookupKonstruksiKapal.getLookupKey());

        MasterLookup masterLookupPengunaanKapal = masterLookupService.cekMasterLookup(asuransiMikroBahariDto.getPenggunaanKapal(), "penggunaan_kapal")
                .orElseThrow(() -> new RuntimeException("Tidak ada Pengunaan Kapal dengan label : " + asuransiMikroBahariDto.getPenggunaanKapal() + " di grup Penggunaan Kapal"));
        asuransiMikroBahari.setPenggunaanKapal(masterLookupPengunaanKapal.getLookupKey());

        if (asuransiMikroBahariDto.getHargaKapal().compareTo(new BigDecimal("100000000")) < 0 ||
                asuransiMikroBahariDto.getHargaKapal().compareTo(new BigDecimal("500000000")) > 0) {
            throw new IllegalArgumentException("Harga Kapal harus antara 100 juta dan 500 juta.");
        }

        asuransiMikroBahari.setHargaKapal(asuransiMikroBahariDto.getHargaKapal());

        String validateJenisPaket = validateJenisPaket(asuransiMikroBahariDto.getJenisPaket(), asuransiMikroBahariDto.getHargaKapal());
        asuransiMikroBahari.setJenisPaket(validateJenisPaket);

        int nomorUrut = (int) (asuransiMikroBahariRepository.count() + 1);
        String nomorSertifikat = utilityService.generateNomorSertifikat(nomorUrut, "9002", LocalDate.now());
        asuransiMikroBahari.setNomorSertifikat(nomorSertifikat);

        int jumlahHariPertanggungan = utilityService.hitungJumlahHariPertanggungan(asuransiMikroBahariDto.getJangkaWaktuAwal(), asuransiMikroBahariDto.getJangkaWaktuAkhir());

        // Hitung premi prorate
        BigDecimal premiPaket = getPremiPaket(asuransiMikroBahariDto.getJenisPaket());
        BigDecimal premi = utilityService.hitungPremiProrate(premiPaket, jumlahHariPertanggungan);
        asuransiMikroBahari.setPremi(premi);

        return asuransiMikroBahariRepository.save(asuransiMikroBahari);
    }

    @Override
    public AsuransiMikroBahari update(AsuransiMikroBahariDto asuransiMikroBahariDto) {
        AsuransiMikroBahari asuransiMikroBahari = asuransiMikroBahariRepository.findById(asuransiMikroBahariDto.getId())
                .orElseThrow(() -> new RuntimeException("No Asuransi Mikro Bahari Found With Id : " + asuransiMikroBahariDto.getId()));

        if (!asuransiMikroBahariDto.getJangkaWaktuAwal().isEqual(LocalDate.now())) {
            throw new IllegalArgumentException("Jangka Waktu Awal hanya bisa diisi dengan hari ini.");
        }

        if (asuransiMikroBahariDto.getJangkaWaktuAkhir().isBefore(asuransiMikroBahariDto.getJangkaWaktuAwal())) {
            throw new IllegalArgumentException("Jangka Waktu Akhir tidak boleh lebih awal dari Jangka Waktu Awal.");
        }

        LocalDate maxEndDate = asuransiMikroBahariDto.getJangkaWaktuAwal().plusYears(1);
        if (asuransiMikroBahariDto.getJangkaWaktuAkhir().isAfter(maxEndDate)) {
            throw new IllegalArgumentException("Jangka Waktu Akhir tidak boleh lebih dari 1 tahun dari Jangka Waktu Awal.");
        }

        asuransiMikroBahari.setNamaTertanggung(asuransiMikroBahariDto.getNamaTertanggung());
        asuransiMikroBahari.setNomorKTP(asuransiMikroBahariDto.getNomorKTP());
        asuransiMikroBahari.setEmail(asuransiMikroBahariDto.getEmail());
        asuransiMikroBahari.setNomorTelepon(asuransiMikroBahariDto.getNomorTelepon());
        asuransiMikroBahari.setJangkaWaktuAwal(asuransiMikroBahariDto.getJangkaWaktuAwal());
        asuransiMikroBahari.setJangkaWaktuAkhir(asuransiMikroBahariDto.getJangkaWaktuAkhir());
        asuransiMikroBahari.setNoIDKapal(asuransiMikroBahariDto.getNoIDKapal());

        MasterLookup masterLookupJenisKapal = masterLookupService.cekMasterLookup(asuransiMikroBahariDto.getJenisKapal(), "jenis_kapal")
                .orElseThrow(() -> new RuntimeException("Tidak ada Jenis Kapal dengan label : " + asuransiMikroBahariDto.getJenisKapal() + " di grup Jenis Kapal"));
        asuransiMikroBahari.setJenisKapal(masterLookupJenisKapal.getLookupKey());

        MasterLookup masterLookupKonstruksiKapal = masterLookupService.cekMasterLookup(asuransiMikroBahariDto.getKonstruksiKapal(), "MARINE_HULL_CONSTRUCTION")
                .orElseThrow(() -> new RuntimeException("Tidak ada Konstruksi Kapal dengan label : " + asuransiMikroBahariDto.getKonstruksiKapal() + " di grup Marine Hull Construction"));
        asuransiMikroBahari.setKonstruksiKapal(masterLookupKonstruksiKapal.getLookupKey());

        MasterLookup masterLookupPengunaanKapal = masterLookupService.cekMasterLookup(asuransiMikroBahariDto.getPenggunaanKapal(), "penggunaan_kapal")
                .orElseThrow(() -> new RuntimeException("Tidak ada Pengunaan Kapal dengan label : " + asuransiMikroBahariDto.getPenggunaanKapal() + " di grup Penggunaan Kapal"));
        asuransiMikroBahari.setPenggunaanKapal(masterLookupPengunaanKapal.getLookupKey());

        if (asuransiMikroBahariDto.getHargaKapal().compareTo(new BigDecimal("100000000")) < 0 ||
                asuransiMikroBahariDto.getHargaKapal().compareTo(new BigDecimal("500000000")) > 0) {
            throw new IllegalArgumentException("Harga Kapal harus antara 100 juta dan 500 juta.");
        }

        asuransiMikroBahari.setHargaKapal(asuransiMikroBahariDto.getHargaKapal());

        String validateJenisPaket = validateJenisPaket(asuransiMikroBahariDto.getJenisPaket(), asuransiMikroBahariDto.getHargaKapal());
        asuransiMikroBahari.setJenisPaket(validateJenisPaket);

        int jumlahHariPertanggungan = utilityService.hitungJumlahHariPertanggungan(asuransiMikroBahariDto.getJangkaWaktuAwal(), asuransiMikroBahariDto.getJangkaWaktuAkhir());

        // Hitung premi prorate
        BigDecimal premiPaket = getPremiPaket(asuransiMikroBahariDto.getJenisPaket());
        BigDecimal premi = utilityService.hitungPremiProrate(premiPaket, jumlahHariPertanggungan);
        asuransiMikroBahari.setPremi(premi);

        return asuransiMikroBahariRepository.save(asuransiMikroBahari);
    }

    @Override
    public void delete(String id) {
        AsuransiMikroBahari asuransiMikroBahari = asuransiMikroBahariRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Asuransi Mikro Bahari Found With Id : " + id));
        asuransiMikroBahariRepository.delete(asuransiMikroBahari);
    }

    @Override
    public List<AsuransiMikroBahari> listAsuransiMikroBahari() {
        return asuransiMikroBahariRepository.findAll();
    }

    @Override
    public Optional<AsuransiMikroBahari> findByAsuransiMikroBahari(String id) {
        return asuransiMikroBahariRepository.findById(id);
    }

    @Override
    public Page<AsuransiMikroBahari> findAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return asuransiMikroBahariRepository.findAllByOrderByNamaTertanggungAsc(pageable);
    }

    private String validateJenisPaket(String jenisPaket, BigDecimal hargaKapal) {
        if (hargaKapal.compareTo(new BigDecimal("150000000")) <= 0) {
            // Harga kapal ≤ 150 juta hanya bisa memilih Silver
            if (!jenisPaket.equalsIgnoreCase("Silver")) {
                throw new IllegalArgumentException("Untuk harga kapal hingga 150 juta, hanya bisa memilih paket Silver.");
            }
            return "Silver";
        } else if (hargaKapal.compareTo(new BigDecimal("300000000")) <= 0) {
            // Harga kapal ≤ 300 juta bisa memilih Silver atau Gold
            if (!jenisPaket.equalsIgnoreCase("Silver") && !jenisPaket.equalsIgnoreCase("Gold")) {
                throw new IllegalArgumentException("Untuk harga kapal hingga 300 juta, hanya bisa memilih paket Silver atau Gold.");
            }
            return jenisPaket;
        } else if (hargaKapal.compareTo(new BigDecimal("500000000")) <= 0) {
            // Harga kapal ≤ 500 juta bisa memilih Silver, Gold, atau Platinum
            if (!jenisPaket.equalsIgnoreCase("Silver") &&
                    !jenisPaket.equalsIgnoreCase("Gold") &&
                    !jenisPaket.equalsIgnoreCase("Platinum")) {
                throw new IllegalArgumentException("Untuk harga kapal hingga 500 juta, hanya bisa memilih paket Silver, Gold, atau Platinum.");
            }
            return jenisPaket;
        }

        throw new IllegalArgumentException("Jenis Paket tidak valid. Pilih antara Silver, Gold, atau Platinum.");
    }

    private BigDecimal getPremiPaket(String jenisPaket) {
        switch (jenisPaket.toLowerCase()) {
            case "silver": return BigDecimal.valueOf(70000);
            case "gold": return BigDecimal.valueOf(80000);
            case "platinum": return BigDecimal.valueOf(90000);
            default: throw new IllegalArgumentException("Jenis paket tidak valid.");
        }
    }
}
