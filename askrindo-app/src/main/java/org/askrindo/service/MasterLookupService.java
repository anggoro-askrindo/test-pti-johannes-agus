package org.askrindo.service;

import org.askrindo.domain.MasterLookup;

import java.util.Optional;

public interface MasterLookupService {

    Optional<MasterLookup> cekMasterLookup (String key, String group);

}
