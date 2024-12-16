package org.askrindo.service.impl;

import org.askrindo.domain.AsuransiMikroBahari;
import org.askrindo.domain.MasterLookup;
import org.askrindo.dto.AsuransiMikroBahariDto;
import org.askrindo.repository.AsuransiMikroBahariRepository;
import org.askrindo.service.AsuransiMikroBahariService;
import org.askrindo.service.MasterLookupService;
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

    @Override
    public AsuransiMikroBahari save(AsuransiMikroBahariDto asuransiMikroBahariDto) {

        if (!asuransiMikroBahariDto.getJangkaWaktuAwal().isEqual(LocalDate.now())) {
            throw new IllegalArgumentException("Jangka Waktu Awal hanya bisa diisi dengan hari ini.");
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
        asuransiMikroBahari.setJangkaWaktuAwal(asuransiMikroBahariDto.getJangkaWaktuAwal());
        asuransiMikroBahari.setNoIDKapal(asuransiMikroBahariDto.getNoIDKapal());

        MasterLookup masterLookupJenisKapal = masterLookupService.cekMasterLookup(asuransiMikroBahariDto.getJenisKapal(), "jenis_kapal")
                .orElseThrow(() -> new RuntimeException("Tidak ada Jenis Kapal dengan key: " + asuransiMikroBahariDto.getJenisKapal() + "di grup Jenis Kapal"));
        asuransiMikroBahari.setJenisKapal(masterLookupJenisKapal.getLookupKey());

        MasterLookup masterLookupKonstruksiKapal = masterLookupService.cekMasterLookup(asuransiMikroBahariDto.getKonstruksiKapal(), "MARINE_HULL_CONSTRUCTION")
                .orElseThrow(() -> new RuntimeException("Tidak ada Konstruksi Kapal dengan key: " + asuransiMikroBahariDto.getKonstruksiKapal() + "di grup Marine Hull Construction"));
        asuransiMikroBahari.setKonstruksiKapal(masterLookupKonstruksiKapal.getLookupKey());

        MasterLookup masterLookupPengunaanKapal = masterLookupService.cekMasterLookup(asuransiMikroBahariDto.getPenggunaanKapal(), "penggunaan_kapal")
                .orElseThrow(() -> new RuntimeException("Tidak ada Pengunaan Kapal dengan key: " + asuransiMikroBahariDto.getPenggunaanKapal() + "di grup Penggunaan Kapal"));
        asuransiMikroBahari.setPenggunaanKapal(masterLookupKonstruksiKapal.getLookupKey());

        if (asuransiMikroBahariDto.getHargaKapal().compareTo(new BigDecimal("100000000")) < 0 ||
                asuransiMikroBahariDto.getHargaKapal().compareTo(new BigDecimal("500000000")) > 0) {
            throw new IllegalArgumentException("Harga Kapal harus antara 100 juta dan 500 juta.");
        }

        asuransiMikroBahari.setHargaKapal(asuransiMikroBahariDto.getHargaKapal());

        String validateJenisPaket = validateJenisPaket(asuransiMikroBahariDto.getJenisPaket(), asuransiMikroBahariDto.getHargaKapal());
        asuransiMikroBahariDto.setJenisPaket(validateJenisPaket);

        return asuransiMikroBahariRepository.save(asuransiMikroBahari);
    }

    @Override
    public AsuransiMikroBahari update(AsuransiMikroBahariDto asuransiMikroBahariDto) {
        AsuransiMikroBahari asuransiMikroBahari = asuransiMikroBahariRepository.findById(asuransiMikroBahariDto.getId())
                .orElseThrow(() -> new RuntimeException("No Asuransi Mikro Bahari Found With Id : " + asuransiMikroBahariDto.getId()));

        if (!asuransiMikroBahariDto.getJangkaWaktuAwal().isEqual(LocalDate.now())) {
            throw new IllegalArgumentException("Jangka Waktu Awal hanya bisa diisi dengan hari ini.");
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
        asuransiMikroBahari.setJangkaWaktuAwal(asuransiMikroBahariDto.getJangkaWaktuAwal());
        asuransiMikroBahari.setNoIDKapal(asuransiMikroBahariDto.getNoIDKapal());

        MasterLookup masterLookupJenisKapal = masterLookupService.cekMasterLookup(asuransiMikroBahariDto.getJenisKapal(), "jenis_kapal")
                .orElseThrow(() -> new RuntimeException("Tidak ada Jenis Kapal dengan key: " + asuransiMikroBahariDto.getJenisKapal() + "di grup Jenis Kapal"));
        asuransiMikroBahari.setJenisKapal(masterLookupJenisKapal.getLookupKey());

        MasterLookup masterLookupKonstruksiKapal = masterLookupService.cekMasterLookup(asuransiMikroBahariDto.getKonstruksiKapal(), "MARINE_HULL_CONSTRUCTION")
                .orElseThrow(() -> new RuntimeException("Tidak ada Konstruksi Kapal dengan key: " + asuransiMikroBahariDto.getKonstruksiKapal() + "di grup Marine Hull Construction"));
        asuransiMikroBahari.setKonstruksiKapal(masterLookupKonstruksiKapal.getLookupKey());

        MasterLookup masterLookupPengunaanKapal = masterLookupService.cekMasterLookup(asuransiMikroBahariDto.getPenggunaanKapal(), "penggunaan_kapal")
                .orElseThrow(() -> new RuntimeException("Tidak ada Pengunaan Kapal dengan key: " + asuransiMikroBahariDto.getPenggunaanKapal() + "di grup Penggunaan Kapal"));
        asuransiMikroBahari.setPenggunaanKapal(masterLookupKonstruksiKapal.getLookupKey());

        if (asuransiMikroBahariDto.getHargaKapal().compareTo(new BigDecimal("100000000")) < 0 ||
                asuransiMikroBahariDto.getHargaKapal().compareTo(new BigDecimal("500000000")) > 0) {
            throw new IllegalArgumentException("Harga Kapal harus antara 100 juta dan 500 juta.");
        }

        asuransiMikroBahari.setHargaKapal(asuransiMikroBahariDto.getHargaKapal());

        String validateJenisPaket = validateJenisPaket(asuransiMikroBahariDto.getJenisPaket(), asuransiMikroBahariDto.getHargaKapal());
        asuransiMikroBahariDto.setJenisPaket(validateJenisPaket);

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
        if (jenisPaket.equalsIgnoreCase("Silver")) {
            if (hargaKapal.compareTo(new BigDecimal("150000000")) > 0) {
                throw new IllegalArgumentException("Silver hanya untuk harga kapal hingga 150 juta.");
            }
            return "Silver";
        } else if (jenisPaket.equalsIgnoreCase("Gold")) {
            if (hargaKapal.compareTo(new BigDecimal("300000000")) > 0) {
                throw new IllegalArgumentException("Gold hanya untuk harga kapal hingga 300 juta.");
            }
            return "Gold";
        } else if (jenisPaket.equalsIgnoreCase("Platinum")) {
            if (hargaKapal.compareTo(new BigDecimal("500000000")) > 0) {
                throw new IllegalArgumentException("Platinum hanya untuk harga kapal hingga 500 juta.");
            }
            return "Platinum";
        } else {
            throw new IllegalArgumentException("Jenis Paket tidak valid. Pilih antara Silver, Gold, atau Platinum.");
        }
    }
}
