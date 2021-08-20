package com.example.externalapi.common.user.domain.repository;

import com.example.externalapi.common.user.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Optional<Users> findAllByUserEmail(String userEmail);
}
