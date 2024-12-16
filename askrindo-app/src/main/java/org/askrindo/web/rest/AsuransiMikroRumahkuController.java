package org.askrindo.web.rest;

import org.askrindo.domain.AsuransiMikroRumahku;
import org.askrindo.domain.MasterLookup;
import org.askrindo.dto.AsuransiMikroRumahkuDto;
import org.askrindo.service.AsuransiMikroRumahkuService;
import org.askrindo.service.MasterLookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/asuransi_mikro_rumahku/")
public class AsuransiMikroRumahkuController {

    @Autowired
    private AsuransiMikroRumahkuService asuransiMikroRumahkuService;

    @Autowired
    private MasterLookupService masterLookupService;

    @GetMapping("{id}")
    public AsuransiMikroRumahkuDto findByAsuransiMikroRumahku(@PathVariable(name = "id") String id) {
        return toDto(asuransiMikroRumahkuService.findByAsuransiMikroRumahku(id).orElseThrow(() -> new RuntimeException("No Asuransi Mikro Rumahku Found With Id : " + id)));
    }

    @GetMapping("")
    public List<AsuransiMikroRumahkuDto> listAsuransiMikroRumahku() {
        return asuransiMikroRumahkuService.listAsuransiMikroRumahku().stream().map(this::toDto).collect(Collectors.toList());
    }

    @PostMapping("")
    public AsuransiMikroRumahkuDto saveDocument(@RequestBody @Valid AsuransiMikroRumahkuDto asuransiMikroRumahkuDto) {
        return toDto(asuransiMikroRumahkuService.save(asuransiMikroRumahkuDto));
    }

    @PatchMapping("")
    public AsuransiMikroRumahkuDto updateDocument(@RequestBody @Valid AsuransiMikroRumahkuDto asuransiMikroRumahkuDto) {
        return toDto(asuransiMikroRumahkuService.update(asuransiMikroRumahkuDto));
    }

    @DeleteMapping("{id}")
    public void deleteDocument(@PathVariable(name = "id") String id) {
        asuransiMikroRumahkuService.delete(id);
    }

    @GetMapping("/paged")
    public Page<AsuransiMikroRumahkuDto> paged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return asuransiMikroRumahkuService.findAll(page, size).map(this::toDto);
    }

    private AsuransiMikroRumahkuDto toDto(AsuransiMikroRumahku asuransiMikroRumahku) {
        AsuransiMikroRumahkuDto asuransiMikroRumahkuDto = new AsuransiMikroRumahkuDto();
        asuransiMikroRumahkuDto.setId(asuransiMikroRumahku.getId());
        asuransiMikroRumahkuDto.setNamaTertanggung(asuransiMikroRumahku.getNamaTertanggung());
        asuransiMikroRumahkuDto.setNomorKTP(asuransiMikroRumahku.getNomorKTP());
        asuransiMikroRumahkuDto.setEmail(asuransiMikroRumahku.getEmail());
        asuransiMikroRumahkuDto.setNomorTelepon(asuransiMikroRumahku.getNomorTelepon());
        asuransiMikroRumahkuDto.setJangkaWaktuAwal(asuransiMikroRumahku.getJangkaWaktuAwal());
        asuransiMikroRumahkuDto.setJangkaWaktuAkhir(asuransiMikroRumahku.getJangkaWaktuAkhir());
        MasterLookup masterLookup1 = masterLookupService.cekLookupKey(asuransiMikroRumahku.getInformasiKepemilikan()).orElseThrow();
        asuransiMikroRumahkuDto.setInformasiKepemilikan(masterLookup1.getLabel());
        asuransiMikroRumahkuDto.setAlamat(asuransiMikroRumahku.getAlamat());
        asuransiMikroRumahkuDto.setNamaAhliWaris(asuransiMikroRumahku.getNamaAhliWaris());
        asuransiMikroRumahkuDto.setTanggalLahir(asuransiMikroRumahku.getTanggalLahir());
        asuransiMikroRumahkuDto.setNomorTeleponAhliWaris(asuransiMikroRumahku.getNomorTeleponAhliWaris());
        MasterLookup masterLookup2 = masterLookupService.cekLookupKey(asuransiMikroRumahku.getHubungan()).orElseThrow();
        asuransiMikroRumahkuDto.setHubungan(masterLookup2.getLabel());
        asuransiMikroRumahkuDto.setJenisPaket(asuransiMikroRumahku.getJenisPaket());
        asuransiMikroRumahkuDto.setNomorSertifikat(asuransiMikroRumahku.getNomorSertifikat());
        asuransiMikroRumahkuDto.setPremi(asuransiMikroRumahku.getPremi());
        return asuransiMikroRumahkuDto;
    }

}
