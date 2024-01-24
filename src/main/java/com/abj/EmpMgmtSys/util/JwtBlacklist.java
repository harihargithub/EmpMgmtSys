package com.abj.EmpMgmtSys.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
// import org.springframework.stereotype.Component;

import com.abj.EmpMgmtSys.model.BlacklistedToken;
import com.abj.EmpMgmtSys.repository.BlacklistedTokenRepository;

@Component
public class JwtBlacklist {

    @Autowired
    private BlacklistedTokenRepository blacklistedTokenRepository;

    public void add(String token) {
        BlacklistedToken blacklistedToken = new BlacklistedToken();
        blacklistedToken.setToken(token);
        blacklistedTokenRepository.save(blacklistedToken);
    }

    public boolean contains(String token) {
        if (token == null) {
            return false;
        }
        return blacklistedTokenRepository.findById(token).isPresent();
    }
}