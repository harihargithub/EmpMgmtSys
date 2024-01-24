package com.abj.EmpMgmtSys.controller;

import com.abj.EmpMgmtSys.model.AuthenticationRequest;
import com.abj.EmpMgmtSys.model.AuthenticationResponse;
import com.abj.EmpMgmtSys.service.DomainUserDetailsService;
import com.abj.EmpMgmtSys.util.JwtBlacklist;
import com.abj.EmpMgmtSys.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@RestController
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private DomainUserDetailsService userDetailsService;

    @Autowired
    private JwtBlacklist jwtBlacklist;

    @Autowired
    private JwtUtil JwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
            HttpSession session) throws Exception {
        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        session.setAttribute("user", userDetails);

        // Generate a JWT for the authenticated user
        String token = JwtUtil.generateToken(userDetails);

        // Return the JWT in the response
        return ResponseEntity.ok(new AuthenticationResponse(token));
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader("Authorization") String bearerToken, HttpServletRequest request) {
        System.out.println("Logout method called");
        System.out.println("Request: " + request.toString());
        System.out.println("Logging out: " + bearerToken); // Log the bearer token
        String token = bearerToken.substring(7); // Remove the "Bearer " prefix
        jwtBlacklist.add(token);
        boolean isBlacklisted = jwtBlacklist.contains(token); // Check if the token was added to the blacklist
        System.out.println("Is token blacklisted: " + isBlacklisted); // Log the result
        return ResponseEntity.ok("Logged out");
    }

    @GetMapping("/login")
    public ResponseEntity<?> checkAuthentication(Principal principal) {
        if (principal != null) {
            return ResponseEntity.ok("Already logged in");
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Not logged in");
        }
    }

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }
}