package org.askrindo.service;

import org.askrindo.domain.User;
import org.askrindo.dto.UserDto;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(UserDto userDto);

    User update(UserDto userDto);

    User delete(String id);

    List<User> listUser();

    Optional<User> findByUser(String id);

    Page<User> findAll(int pageNumber, int pageSize);

}
