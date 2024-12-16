package org.askrindo.service.impl;

import org.askrindo.domain.AsuransiMikroRumahku;
import org.askrindo.domain.MasterLookup;
import org.askrindo.dto.AsuransiMikroRumahkuDto;
import org.askrindo.repository.AsuransiMikroRumahkuRepository;
import org.askrindo.service.AsuransiMikroRumahkuService;
import org.askrindo.service.MasterLookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class AsuransiMikroRumahkuServiceImpl implements AsuransiMikroRumahkuService {

    @Autowired
    private AsuransiMikroRumahkuRepository asuransiMikroRumahkuRepository;

    @Autowired
    private MasterLookupService masterLookupService;

    @Override
    public AsuransiMikroRumahku save(AsuransiMikroRumahkuDto asuransiMikroRumahkuDto) {

        LocalDate today = LocalDate.now();
        LocalDate minDate = today.minusDays(3);
        LocalDate maxDate = today.plusDays(3);

        if (asuransiMikroRumahkuDto.getJangkaWaktuAwal().isBefore(minDate) ||
                asuransiMikroRumahkuDto.getJangkaWaktuAwal().isAfter(maxDate)) {
            throw new IllegalArgumentException("Jangka Waktu Awal harus antara H-3 dan H+3 dari hari ini.");
        }

        // Validasi Jangka Waktu Akhir
        LocalDate maxEndDate = asuransiMikroRumahkuDto.getJangkaWaktuAwal().plusYears(1);
        if (asuransiMikroRumahkuDto.getJangkaWaktuAkhir().isAfter(maxEndDate)) {
            throw new IllegalArgumentException("Jangka Waktu Akhir tidak boleh lebih dari 1 tahun dari Jangka Waktu Awal.");
        }

        AsuransiMikroRumahku asuransiMikroRumahku = new AsuransiMikroRumahku();
        asuransiMikroRumahku.setNamaTertanggung(asuransiMikroRumahkuDto.getNamaTertanggung());
        asuransiMikroRumahku.setNomorKTP(asuransiMikroRumahkuDto.getNomorKTP());
        asuransiMikroRumahku.setEmail(asuransiMikroRumahkuDto.getEmail());
        asuransiMikroRumahku.setNomorTelepon(asuransiMikroRumahkuDto.getNomorTelepon());
        asuransiMikroRumahku.setJangkaWaktuAwal(asuransiMikroRumahkuDto.getJangkaWaktuAwal());
        asuransiMikroRumahku.setJangkaWaktuAwal(asuransiMikroRumahkuDto.getJangkaWaktuAwal());

        MasterLookup masterLookupInformasiKepemilikan = masterLookupService.cekMasterLookup(asuransiMikroRumahkuDto.getInformasiKepemilikan(), "asmik_info_kepemilikan")
                .orElseThrow(() -> new RuntimeException("Tidak ada Informasi Kepemilikan dengan key: " + asuransiMikroRumahkuDto.getInformasiKepemilikan() + "di grup Asmik Info Kepemilikan"));
        asuransiMikroRumahku.setInformasiKepemilikan(masterLookupInformasiKepemilikan.getLabel());

        asuransiMikroRumahku.setAlamat(asuransiMikroRumahkuDto.getAlamat());
        asuransiMikroRumahku.setNamaAhliWaris(asuransiMikroRumahkuDto.getNamaAhliWaris());
        asuransiMikroRumahku.setTanggalLahir(asuransiMikroRumahkuDto.getTanggalLahir());
        asuransiMikroRumahku.setNomorTeleponAhliWaris(asuransiMikroRumahkuDto.getNomorTeleponAhliWaris());

        MasterLookup masterLookupHubungan = masterLookupService.cekMasterLookup(asuransiMikroRumahku.getHubungan(), "ahli_waris")
                .orElseThrow(() -> new RuntimeException("Tidak ada Hubungan dengan key: " + asuransiMikroRumahkuDto.getHubungan() + "di grup Ahli Waris"));
        asuransiMikroRumahku.setHubungan(masterLookupHubungan.getLabel());

        asuransiMikroRumahku.setJenisPaket(asuransiMikroRumahkuDto.getJenisPaket());
        return asuransiMikroRumahkuRepository.save(asuransiMikroRumahku);
    }

    @Override
    public AsuransiMikroRumahku update(AsuransiMikroRumahkuDto asuransiMikroRumahkuDto) {
        AsuransiMikroRumahku asuransiMikroRumahku = asuransiMikroRumahkuRepository.findById(asuransiMikroRumahkuDto.getId())
                .orElseThrow(() -> new RuntimeException("No Asuransi Mikro Rumahku Id Found With Id : " + asuransiMikroRumahkuDto.getId()));

        LocalDate today = LocalDate.now();
        LocalDate minDate = today.minusDays(3);
        LocalDate maxDate = today.plusDays(3);

        if (asuransiMikroRumahkuDto.getJangkaWaktuAwal().isBefore(minDate) ||
                asuransiMikroRumahkuDto.getJangkaWaktuAwal().isAfter(maxDate)) {
            throw new IllegalArgumentException("Jangka Waktu Awal harus antara H-3 dan H+3 dari hari ini.");
        }

        // Validasi Jangka Waktu Akhir
        LocalDate maxEndDate = asuransiMikroRumahkuDto.getJangkaWaktuAwal().plusYears(1);
        if (asuransiMikroRumahkuDto.getJangkaWaktuAkhir().isAfter(maxEndDate)) {
            throw new IllegalArgumentException("Jangka Waktu Akhir tidak boleh lebih dari 1 tahun dari Jangka Waktu Awal.");
        }

        asuransiMikroRumahku.setNamaTertanggung(asuransiMikroRumahkuDto.getNamaTertanggung());
        asuransiMikroRumahku.setNomorKTP(asuransiMikroRumahkuDto.getNomorKTP());
        asuransiMikroRumahku.setEmail(asuransiMikroRumahkuDto.getEmail());
        asuransiMikroRumahku.setNomorTelepon(asuransiMikroRumahkuDto.getNomorTelepon());
        asuransiMikroRumahku.setJangkaWaktuAwal(asuransiMikroRumahkuDto.getJangkaWaktuAwal());
        asuransiMikroRumahku.setJangkaWaktuAwal(asuransiMikroRumahkuDto.getJangkaWaktuAwal());

        MasterLookup masterLookupInformasiKepemilikan = masterLookupService.cekMasterLookup(asuransiMikroRumahkuDto.getInformasiKepemilikan(), "asmik_info_kepemilikan")
                .orElseThrow(() -> new RuntimeException("Tidak ada Informasi Kepemilikan dengan key: " + asuransiMikroRumahkuDto.getInformasiKepemilikan() + "di grup Asmik Info Kepemilikan"));
        asuransiMikroRumahku.setInformasiKepemilikan(masterLookupInformasiKepemilikan.getLabel());

        asuransiMikroRumahku.setAlamat(asuransiMikroRumahkuDto.getAlamat());
        asuransiMikroRumahku.setNamaAhliWaris(asuransiMikroRumahkuDto.getNamaAhliWaris());
        asuransiMikroRumahku.setTanggalLahir(asuransiMikroRumahkuDto.getTanggalLahir());
        asuransiMikroRumahku.setNomorTeleponAhliWaris(asuransiMikroRumahkuDto.getNomorTeleponAhliWaris());

        MasterLookup masterLookupHubungan = masterLookupService.cekMasterLookup(asuransiMikroRumahku.getHubungan(), "ahli_waris")
                .orElseThrow(() -> new RuntimeException("Tidak ada Hubungan dengan key: " + asuransiMikroRumahkuDto.getHubungan() + "di grup Ahli Waris"));
        asuransiMikroRumahku.setHubungan(masterLookupHubungan.getLabel());

        asuransiMikroRumahku.setJenisPaket(asuransiMikroRumahkuDto.getJenisPaket());
        return asuransiMikroRumahkuRepository.save(asuransiMikroRumahku);
    }

    @Override
    public void delete(String id) {
        AsuransiMikroRumahku asuransiMikroRumahku = asuransiMikroRumahkuRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Asuransi Mikro Rumahku Found With Id : " + id));
        asuransiMikroRumahkuRepository.delete(asuransiMikroRumahku);
    }

    @Override
    public List<AsuransiMikroRumahku> listAsuransiMikroRumahku() {
        return asuransiMikroRumahkuRepository.findAll();
    }

    @Override
    public Optional<AsuransiMikroRumahku> findByAsuransiMikroRumahku(String id) {
        return asuransiMikroRumahkuRepository.findById(id);
    }

    @Override
    public Page<AsuransiMikroRumahku> findAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return asuransiMikroRumahkuRepository.findAllByOrderByNamaTertanggungAsc(pageable);
    }
}