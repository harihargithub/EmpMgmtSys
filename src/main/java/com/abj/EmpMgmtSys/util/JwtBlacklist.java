package com.abj.EmpMgmtSys.util;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Component
public class JwtBlacklist {

    private Set<String> blacklist = Collections.synchronizedSet(new HashSet<>());

    public void add(String token) {
        blacklist.add(token);
    }

    public boolean contains(String token) {
        return blacklist.contains(token);
    }
}