package org.askrindo.repository;

import org.askrindo.domain.AsuransiMikroBahari;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AsuransiMikroBahariRepository extends JpaRepository<AsuransiMikroBahari, String> {

    Page<AsuransiMikroBahari> findAllByOrderByNamaTertanggungAsc (Pageable pageable);

}
