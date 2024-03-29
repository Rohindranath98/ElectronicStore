package com.lcwd.electronic.store.repositories;

import com.lcwd.electronic.store.entites.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {
    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndName(String email, String password);
    List<User> findByNameContaining(String keywords);
}

