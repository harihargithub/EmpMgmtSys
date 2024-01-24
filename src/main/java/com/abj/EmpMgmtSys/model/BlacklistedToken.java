package com.abj.EmpMgmtSys.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class BlacklistedToken {

    @Id
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}