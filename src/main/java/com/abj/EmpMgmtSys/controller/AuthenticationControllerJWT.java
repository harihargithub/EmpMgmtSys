package com.abj.EmpMgmtSys.controller;

import com.abj.EmpMgmtSys.model.AuthenticationRequest;
import com.abj.EmpMgmtSys.service.DomainUserDetailsService;
import com.abj.EmpMgmtSys.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import com.abj.EmpMgmtSys.util.JwtBlacklist;
// import java.net.URI;
// import java.net.URISyntaxException;
// import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class AuthenticationControllerJWT {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtBlacklist jwtBlacklist;

    @Autowired
    private DomainUserDetailsService userDetailsService;

    @PostMapping("/loginJWT")
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

    @PostMapping("/logoutJWT")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);

            // Invalidate the token
            jwtBlacklist.add(token);

            // Log the token and the contents of the blacklist
            System.out.println("Logged out token: " + token);
            System.out.println("Blacklist: " + jwtBlacklist);

            return ResponseEntity.ok().build();
        }

        // If the token is not present or doesn't start with "Bearer ", return an error
        // response
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid token");
    }

    @GetMapping("/loginPageJWT")
    public ResponseEntity<?> loginPage(@RequestParam(required = false) String logout) {
        if (logout != null) {
            return ResponseEntity.ok(Collections.singletonMap("message", "Logout successful"));
        }

        // Handle the normal login page request...
        return ResponseEntity.ok(Collections.singletonMap("message", "Login page"));
    }

    @GetMapping("/loginJWT")
    public ResponseEntity<?> loginPage() {
        // Handle the login page request...
        return ResponseEntity.ok(Collections.singletonMap("message", "Login page"));
    }
}