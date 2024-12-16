package org.askrindo.web.rest;

import org.askrindo.domain.AsuransiMikroBahari;
import org.askrindo.domain.MasterLookup;
import org.askrindo.dto.AsuransiMikroBahariDto;
import org.askrindo.service.AsuransiMikroBahariService;
import org.askrindo.service.MasterLookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/asuransi_mikro_bahari/")
public class AsuransiMikroBahariController {

    @Autowired
    private AsuransiMikroBahariService asuransiMikroBahariService;

    @Autowired
    private MasterLookupService masterLookupService;

    @GetMapping("{id}")
    public AsuransiMikroBahariDto findByAsuransiMikroBahari(@PathVariable(name = "id") String id) {
        return toDto(asuransiMikroBahariService.findByAsuransiMikroBahari(id).orElseThrow(() -> new RuntimeException("No Asuransi Mikro Bahari Found With Id : " + id)));
    }

    @GetMapping("")
    public List<AsuransiMikroBahariDto> listAsuransiMikroBahari() {
        return asuransiMikroBahariService.listAsuransiMikroBahari().stream().map(this::toDto).collect(Collectors.toList());
    }

    @PostMapping("")
    public AsuransiMikroBahariDto saveDocument(@RequestBody @Valid AsuransiMikroBahariDto asuransiMikroBahariDto) {
        return toDto(asuransiMikroBahariService.save(asuransiMikroBahariDto));
    }

    @PatchMapping("")
    public AsuransiMikroBahariDto updateDocument(@RequestBody @Valid AsuransiMikroBahariDto asuransiMikroBahariDto) {
        return toDto(asuransiMikroBahariService.update(asuransiMikroBahariDto));
    }

    @DeleteMapping("{id}")
    public void deleteDocument(@PathVariable(name = "id") String id) {
        asuransiMikroBahariService.delete(id);
    }

    @GetMapping("/paged")
    public Page<AsuransiMikroBahariDto> paged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return asuransiMikroBahariService.findAll(page, size).map(this::toDto);
    }

    private AsuransiMikroBahariDto toDto(AsuransiMikroBahari asuransiMikroBahari) {
        AsuransiMikroBahariDto asuransiMikroBahariDto = new AsuransiMikroBahariDto();
        asuransiMikroBahariDto.setId(asuransiMikroBahari.getId());
        asuransiMikroBahariDto.setNamaTertanggung(asuransiMikroBahari.getNamaTertanggung());
        asuransiMikroBahariDto.setNomorKTP(asuransiMikroBahari.getNomorKTP());
        asuransiMikroBahariDto.setEmail(asuransiMikroBahari.getEmail());
        asuransiMikroBahariDto.setNomorTelepon(asuransiMikroBahari.getNomorTelepon());
        asuransiMikroBahariDto.setJangkaWaktuAwal(asuransiMikroBahari.getJangkaWaktuAwal());
        asuransiMikroBahariDto.setJangkaWaktuAkhir(asuransiMikroBahari.getJangkaWaktuAkhir());
        asuransiMikroBahariDto.setNoIDKapal(asuransiMikroBahari.getNoIDKapal());
        MasterLookup masterLookup1 = masterLookupService.cekLookupKey(asuransiMikroBahari.getJenisKapal()).orElseThrow();
        asuransiMikroBahariDto.setJenisKapal(masterLookup1.getLabel());
        MasterLookup masterLookup2 = masterLookupService.cekLookupKey(asuransiMikroBahari.getKonstruksiKapal()).orElseThrow();
        asuransiMikroBahariDto.setKonstruksiKapal(masterLookup2.getLabel());
        MasterLookup masterLookup3 = masterLookupService.cekLookupKey(asuransiMikroBahari.getPenggunaanKapal()).orElseThrow();
        asuransiMikroBahariDto.setPenggunaanKapal(masterLookup3.getLabel());
        asuransiMikroBahariDto.setHargaKapal(asuransiMikroBahari.getHargaKapal());
        asuransiMikroBahariDto.setJenisPaket(asuransiMikroBahari.getJenisPaket());
        asuransiMikroBahariDto.setNomorSertifikat(asuransiMikroBahari.getNomorSertifikat());
        asuransiMikroBahariDto.setPremi(asuransiMikroBahari.getPremi());
        return asuransiMikroBahariDto;
    }

}
