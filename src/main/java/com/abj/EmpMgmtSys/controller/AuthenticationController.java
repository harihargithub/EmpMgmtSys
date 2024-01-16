package com.abj.EmpMgmtSys.controller;

import com.abj.EmpMgmtSys.model.AuthenticationRequest;
import com.abj.EmpMgmtSys.service.DomainUserDetailsService;
import com.abj.EmpMgmtSys.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.abj.EmpMgmtSys.util.JwtBlacklist;
import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtBlacklist jwtBlacklist;

    @Autowired
    private DomainUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
            throws Exception {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),
                            authenticationRequest.getPassword()));
        } catch (BadCredentialsException e) {
            throw new Exception("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(authenticationRequest.getUsername());

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(Collections.singletonMap("token", jwt));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token, HttpServletRequest request) {
        System.out.println("Logout endpoint hit with method: " + request.getMethod() + " and token: " + token);

        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        jwtBlacklist.add(token);
        return ResponseEntity.ok("Logout successful");
    }

    @GetMapping("/loginPage")
    public ResponseEntity<?> loginPage(@RequestParam(required = false) String logout) {
        if (logout != null) {
            return ResponseEntity.ok("Logout successful");
        } else {
            return ResponseEntity.ok("Login page");
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {
        return "redirect:/loginPage";
    }
}