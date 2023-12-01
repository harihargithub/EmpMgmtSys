package com.abj.EmpMgmtSys.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.abj.EmpMgmtSys.model.DomainUserDetails;
import com.abj.EmpMgmtSys.repository.UserJpaRepository;

@Service
public class DomainUserDetailsService implements UserDetailsService {

	@Autowired
	private UserJpaRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return this.userRepository.findByUsername(username).map(DomainUserDetails::new)
				.orElseThrow(() -> new UsernameNotFoundException("Invalid username"));

	}

}
