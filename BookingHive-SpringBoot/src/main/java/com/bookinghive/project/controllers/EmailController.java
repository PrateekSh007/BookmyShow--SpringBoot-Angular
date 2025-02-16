package com.bookinghive.project.controllers;

import com.bookinghive.project.Utility.JwtUtil;
import com.bookinghive.project.model.EmailModel;
import com.bookinghive.project.service.impl.EmailService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<String> sendEmail(HttpServletRequest request, @RequestBody EmailModel emailModel) {
        try {

            String token = null;
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("token".equals(cookie.getName())) {
                        token = cookie.getValue();
                        break;
                    }
                }
            }

            if (token == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Missing authentication token.");
            }


            String email = jwtUtil.extractEmail(token);


            emailService.sendEmail(email, emailModel.getSub(), emailModel.getBody());
            return ResponseEntity.status(HttpStatus.OK).body("Email sent successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to send email: " + e.getMessage());
        }
    }
}
