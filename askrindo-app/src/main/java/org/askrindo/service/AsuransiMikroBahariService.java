package org.askrindo.service;

import org.askrindo.domain.AsuransiMikroBahari;
import org.askrindo.dto.AsuransiMikroBahariDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface AsuransiMikroBahariService {

    AsuransiMikroBahari save(AsuransiMikroBahariDto asuransiMikroBahariDto);

    AsuransiMikroBahari update(AsuransiMikroBahariDto asuransiMikroBahariDto);

    void delete(String id);

    List<AsuransiMikroBahari> listAsuransiMikroBahari();

    Optional<AsuransiMikroBahari> findByAsuransiMikroBahari(String id);

    Page<AsuransiMikroBahari> findAll(int pageNumber, int pageSize);

}
