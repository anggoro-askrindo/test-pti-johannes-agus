package org.askrindo.service.impl;

import org.askrindo.domain.MasterLookup;
import org.askrindo.repository.MasterLookupRepository;
import org.askrindo.service.MasterLookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MasterLookupServiceImpl implements MasterLookupService {

    @Autowired
    private MasterLookupRepository masterLookupRepository;

    @Override
    public Optional<MasterLookup> cekMasterLookup(String label, String group) {
        return masterLookupRepository.findByLookupKeyAndLookupGroup(label, group);
    }
}
