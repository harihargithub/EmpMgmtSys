package com.abj.EmpMgmtSys.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abj.EmpMgmtSys.model.User;

public interface UserJpaRepository extends JpaRepository<User, Integer>{
  Optional<User> findByUsername(String username);
}
