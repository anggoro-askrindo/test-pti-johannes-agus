package org.askrindo.service;

import org.askrindo.domain.AsuransiMikroRumahku;
import org.askrindo.dto.AsuransiMikroRumahkuDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface AsuransiMikroRumahkuService {

    AsuransiMikroRumahku save(AsuransiMikroRumahkuDto asuransiMikroRumahkuDto);

    AsuransiMikroRumahku update(AsuransiMikroRumahkuDto asuransiMikroRumahkuDto);

    void delete(String id);

    List<AsuransiMikroRumahku> listAsuransiMikroRumahku();

    Optional<AsuransiMikroRumahku> findByAsuransiMikroRumahku(String id);

    Page<AsuransiMikroRumahku> findAll(int pageNumber, int pageSize);

}
