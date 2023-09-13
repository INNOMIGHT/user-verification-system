package com.mini_assignment.user_verification.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mini_assignment.user_verification.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
