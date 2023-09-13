package com.mini_assignment.user_verification.repository;

import com.mini_assignment.user_verification.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
