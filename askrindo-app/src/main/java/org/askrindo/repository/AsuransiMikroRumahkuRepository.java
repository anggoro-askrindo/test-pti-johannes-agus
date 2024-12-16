package org.askrindo.repository;

import org.askrindo.domain.AsuransiMikroRumahku;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsuransiMikroRumahkuRepository extends JpaRepository<AsuransiMikroRumahku, String> {

    Page<AsuransiMikroRumahku> findAllByOrderByNamaTertanggungAsc (Pageable pageable);

}
