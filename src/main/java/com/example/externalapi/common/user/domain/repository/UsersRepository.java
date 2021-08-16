package com.example.externalapi.common.user.domain.repository;

import com.example.externalapi.common.user.domain.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Long> {
}
