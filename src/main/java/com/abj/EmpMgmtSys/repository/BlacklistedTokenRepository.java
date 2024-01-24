package com.abj.EmpMgmtSys.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.abj.EmpMgmtSys.model.BlacklistedToken;

public interface BlacklistedTokenRepository extends JpaRepository<BlacklistedToken, String> {
}