package org.askrindo.service.impl;

import org.askrindo.domain.Role;
import org.askrindo.dto.RoleDto;
import org.askrindo.repository.RoleRepository;
import org.askrindo.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public Role create(RoleDto roleDto) {
        if (roleRepository.findByRoleName(roleDto.getRoleName()).isPresent()) {
            throw new IllegalArgumentException("Role dengan nama " + roleDto.getRoleName() + " sudah ada.");
        }
        Role role = new Role();
        role.setRoleName(roleDto.getRoleName());
        role.setKeterangan(roleDto.getKeterangan());
        role.setActive(roleDto.isActive());
        role.setCreatedBy("system");
        role.setCreatedDate(LocalDateTime.now());
        return roleRepository.save(role);
    }

    @Override
    public Role update(RoleDto roleDto) {
        Role role = roleRepository.findByRoleName(roleDto.getRoleName())
                .orElseThrow(() -> new EntityNotFoundException("Role dengan nama " + roleDto.getRoleName() + " tidak ditemukan."));
        role.setKeterangan(roleDto.getKeterangan());
        role.setModifiedBy("system");
        role.setModifiedDate(LocalDateTime.now());
        return roleRepository.save(role);
    }

    @Override
    public Role delete(String roleName) {
        Role role = roleRepository.findByRoleName(roleName).orElseThrow(() -> new EntityNotFoundException("Role Name not found"));
        role.setActive(false);

        return roleRepository.save(role);
    }

    @Override
    public List<Role> listRole() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> findByRole(String roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    @Override
    public Page<Role> findAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return roleRepository.findAll(pageable);
    }
}
