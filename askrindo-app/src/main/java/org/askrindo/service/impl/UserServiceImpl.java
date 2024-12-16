package org.askrindo.service.impl;

import org.askrindo.domain.User;
import org.askrindo.dto.UserDto;
import org.askrindo.repository.UserRepository;
import org.askrindo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User create(UserDto userDto) {
        User user = new User();
        user.setUserName(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setActive(true);
        user.setCreatedBy("system");
        user.setCreatedDate(LocalDateTime.now());

        return userRepository.save(user);
    }

    @Override
    public User update(UserDto userDto) {
        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setFullName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setModifiedBy("system");
        user.setModifiedDate(LocalDateTime.now());

        return userRepository.save(user);
    }

    @Override
    public User delete(String id) {
        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setActive(false);

        return userRepository.save(user);
    }

    @Override
    public List<User> listUser() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findByUser(String id) {
        return userRepository.findById(id);
    }

    @Override
    public Page<User> findAll(int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        return userRepository.findAllByOrderByUserNameAsc(pageable);
    }
}
