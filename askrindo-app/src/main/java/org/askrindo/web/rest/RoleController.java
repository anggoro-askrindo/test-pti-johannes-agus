package org.askrindo.web.rest;

import org.askrindo.domain.Role;
import org.askrindo.domain.User;
import org.askrindo.dto.RoleDto;
import org.askrindo.dto.UserDto;
import org.askrindo.service.RoleService;
import org.askrindo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/role/")
@PreAuthorize("hasRole('ADMIN')")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("{id}")
    public RoleDto findByRole(@PathVariable(name = "roleName") String roleName) {
        return toDto(roleService.findByRole(roleName).orElseThrow(() -> new RuntimeException("User Not Found With Id : " + roleName)));
    }

    @GetMapping("")
    public List<RoleDto> listRole() {
        return roleService.listRole().stream().map(this::toDto).collect(Collectors.toList());
    }

    @PostMapping("")
    public RoleDto saveDocument(@RequestBody @Valid RoleDto roleDto) {
        return toDto(roleService.create(roleDto));
    }

    @PatchMapping("")
    public RoleDto updateDocument(@RequestBody @Valid RoleDto roleDto) {
        return toDto(roleService.update(roleDto));
    }

    @DeleteMapping("{id}")
    public void deleteDocument(@PathVariable(name = "id") String id) {
        roleService.delete(id);
    }

    @GetMapping("/paged")
    public Page<RoleDto> paged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return roleService.findAll(page, size).map(this::toDto);
    }

    private RoleDto toDto(Role role) {
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleName(role.getRoleName());
        roleDto.setKeterangan(role.getKeterangan());
        roleDto.setActive(role.isActive());

        return roleDto;
    }

}
