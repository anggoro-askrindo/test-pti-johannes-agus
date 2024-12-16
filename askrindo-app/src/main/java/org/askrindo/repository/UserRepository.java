package org.askrindo.repository;

import org.askrindo.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    Page<User> findAllByOrderByUserNameAsc (Pageable pageable);

    Optional<User> findByUserName(String userName);

}
