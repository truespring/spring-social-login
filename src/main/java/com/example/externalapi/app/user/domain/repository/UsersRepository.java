package com.example.externalapi.app.user.domain.repository;

import com.example.externalapi.app.user.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUserEmail(String userEmail);
}
