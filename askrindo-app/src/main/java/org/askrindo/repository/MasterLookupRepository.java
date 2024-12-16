package org.askrindo.repository;

import org.askrindo.domain.MasterLookup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MasterLookupRepository extends JpaRepository<MasterLookup, String> {

    Optional<MasterLookup> findByLabelAndLookupGroup(String label, String lookupGroup);

    Optional<MasterLookup> findByLookupKey(String lookupKey);

}
