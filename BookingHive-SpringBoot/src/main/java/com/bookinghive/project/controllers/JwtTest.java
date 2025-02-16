package com.bookinghive.project.controllers;

import com.bookinghive.project.Utility.JwtUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jwt")
public class JwtTest {
    @Autowired
    JwtUtil jwtUtil;

    @GetMapping("/{email}/{userId}")
    public ResponseEntity<?> jwt(@PathVariable String email,@PathVariable Long userId) {
        String token = jwtUtil.generateToken(email,userId);
        return ResponseEntity.ok(token);


    }

    @GetMapping("/authenticate")
    public ResponseEntity<?> authenticate(HttpServletRequest request) {

        String token = null;


        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        if (token == null) {
            return ResponseEntity.status(400).build();
        }


        String email = jwtUtil.extractEmail(token);
        Long userId = jwtUtil.extractUserId(token);
        if (jwtUtil.validateToken(token, email)) {
            return ResponseEntity.status(200).build();
        } else {
            return ResponseEntity.status(401).build();
        }
    }
}
