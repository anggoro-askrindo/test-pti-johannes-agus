package org.askrindo.service;

import org.askrindo.domain.Role;
import org.askrindo.domain.User;
import org.askrindo.dto.RoleDto;
import org.askrindo.dto.UserDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Role create(RoleDto roleDto);

    Role update(RoleDto roleDto);

    Role delete(String id);

    List<Role> listRole();

    Optional<Role> findByRole(String roleName);

    Page<Role> findAll(int pageNumber, int pageSize);

}
