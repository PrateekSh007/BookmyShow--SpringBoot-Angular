package com.bookinghive.project.controllers;

import com.bookinghive.project.Utility.JwtUtil;
import com.bookinghive.project.model.LoginModel;
import com.bookinghive.project.model.ResponseModel;
import com.bookinghive.project.model.UserAnalytics;
import com.bookinghive.project.model.UserModel;
import com.bookinghive.project.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserModel userModel) {
        try {
            // Register the user
            UserModel registeredUser = userService.registerUser(userModel);

            return ResponseEntity.ok(registeredUser);
        } catch (Exception e) {

            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        try {
            UserModel user = userService.getUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }



    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginModel loginModel, HttpServletResponse response) {
        try {
            ResponseModel responseModel = userService.loginUser(loginModel);


            Cookie cookie = new Cookie("token", responseModel.getToken());
            cookie.setHttpOnly(true);
            cookie.setSecure(true);
            cookie.setPath("/");
            cookie.setMaxAge(6 * 60 * 60);


            response.addCookie(cookie);

            responseModel.setToken(null);

            return ResponseEntity.ok(responseModel);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserById(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getUserByID(userId));
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logoutUser(HttpServletResponse response) {

        Cookie cookie = new Cookie("token", "");
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setSecure(true);
        cookie.setMaxAge(0);

        response.addCookie(cookie);
        return ResponseEntity.ok("Logged out successfully");
    }


    @GetMapping("/analytics")
    public ResponseEntity<?> getUserAnalytics(HttpServletRequest request) {
        try {
            // Extract token from cookies (similar to your other endpoints)
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
                return ResponseEntity.status(401).body("Unauthorized: Missing Token");
            }


            String email = jwtUtil.extractEmail(token);
            Long userId = jwtUtil.extractUserId(token);

            if (!jwtUtil.validateToken(token, email)) {
                return ResponseEntity.status(401).body("Unauthorized: Invalid Token");
            }


            UserAnalytics analytics = userService.getUserAnalytics(userId);

            return ResponseEntity.ok(analytics);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error retrieving analytics: " + e.getMessage());
        }
    }


}