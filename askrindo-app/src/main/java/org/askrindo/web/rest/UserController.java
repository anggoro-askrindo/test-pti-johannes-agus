package org.askrindo.web.rest;

import lombok.RequiredArgsConstructor;
import org.askrindo.domain.AsuransiMikroRumahku;
import org.askrindo.domain.MasterLookup;
import org.askrindo.domain.User;
import org.askrindo.dto.AsuransiMikroRumahkuDto;
import org.askrindo.dto.UserDto;
import org.askrindo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users/")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("{id}")
    public UserDto findByUser(@PathVariable(name = "id") String id) {
        return toDto(userService.findByUser(id).orElseThrow(() -> new RuntimeException("User Not Found With Id : " + id)));
    }

    @GetMapping("")
    public List<UserDto> listUser() {
        return userService.listUser().stream().map(this::toDto).collect(Collectors.toList());
    }

    @PostMapping("")
    public UserDto saveDocument(@RequestBody @Valid UserDto userDto) {
        return toDto(userService.create(userDto));
    }

    @PatchMapping("")
    public UserDto updateDocument(@RequestBody @Valid UserDto userDto) {
        return toDto(userService.update(userDto));
    }

    @DeleteMapping("{id}")
    public void deleteDocument(@PathVariable(name = "id") String id) {
        userService.delete(id);
    }

    @GetMapping("/paged")
    public Page<UserDto> paged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return userService.findAll(page, size).map(this::toDto);
    }

    private UserDto toDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUserName());
        userDto.setFullName(user.getFullName());
        userDto.setEmail(user.getEmail());
        userDto.setActive(user.isActive());

        return userDto;
    }

}
