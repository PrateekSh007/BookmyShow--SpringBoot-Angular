package com.bookinghive.project.service.impl;

import com.bookinghive.project.Utility.JwtUtil;
import com.bookinghive.project.entity.User;
import com.bookinghive.project.model.*;
import com.bookinghive.project.repository.UserRepository;
import com.bookinghive.project.service.BookingService;
import com.bookinghive.project.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BookingService bookingService;

    @Override
    public UserModel registerUser(UserModel userModel) throws Exception {

        if (userRepository.existsByEmail(userModel.getEmail())) {
            throw new Exception("User with this email already exists.");
        }
        int assignedRole = (userModel.getRole() != null) ? userModel.getRole() : 0;

        User user = User.builder()
                .name(userModel.getName())
                .age(userModel.getAge())
                .email(userModel.getEmail())
                .password(userModel.getPassword())
                .number(userModel.getNumber())
                .role(assignedRole)
                .build();


        User savedUser = userRepository.save(user);


        return UserModel.builder()
                .id(savedUser.getId())
                .name(savedUser.getName())
                .age(savedUser.getAge())
                .email(savedUser.getEmail())
                .password(savedUser.getPassword())
                .number(savedUser.getNumber())
                .role(savedUser.getRole())
                .build();
    }

    @Override
    public UserModel getUserByEmail(String email) throws Exception {
        Optional<User> userOpt = userRepository.findByEmail(email);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return UserModel.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .age(user.getAge())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .number(user.getNumber())
                    .role(user.getRole())
                    .build();
        } else {
            throw new Exception("User not found with email: " + email);
        }
    }

    @Override
    public List<UserModel> getAllUsers() {
        List<User> users = userRepository.findAll();
        List<UserModel> userModels = new ArrayList<>();

        for (User user : users) {
            UserModel userModel = UserModel.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .age(user.getAge())
                    .email(user.getEmail())
                    .password(user.getPassword())
                    .number(user.getNumber())
                    .role(user.getRole())
                    .build();
            userModels.add(userModel);
        }

        return userModels;
    }

    @Override
    public User getUserByEmailAndPassword(String email, String password) {
        return null;
    }

    @Override
    public ResponseModel loginUser(LoginModel loginModel) {
        Optional<User> userOpt = userRepository.findByEmail(loginModel.getEmail());
        if (userOpt.isEmpty() || !(Objects.equals(userOpt.get().getPassword(), loginModel.getPassword()))) {
            try {
                throw new Exception("Invalid email or password");
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        loginModel.setRole(userOpt.get().getRole());
        ResponseModel responseModel = new ResponseModel();
        responseModel.setRole(userOpt.get().getRole());
        responseModel.setToken(jwtUtil.generateToken(userOpt.get().getEmail(), userOpt.get().getId()));
//        responseModel.setUser_id(userOpt.get().getId());
        return responseModel;
    }

    @Override
    public User getUserById(Long userId) {
        Optional<User> user=userRepository.findById(userId);
        return user.orElse(null);
    }

    @Override
    public UserModel getUserByID(Long userId) {
        User user=getUserById(userId);
        UserModel userModel=new UserModel();
        userModel.setId(user.getId());
        userModel.setName(user.getName());
        userModel.setAge(user.getAge());
        userModel.setEmail(user.getEmail());
        userModel.setNumber(user.getNumber());
        userModel.setRole(user.getRole());
        return userModel;

    }

    @Override
    public UserAnalytics getUserAnalytics(Long userId) throws Exception {
        // Fetch the user entity
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new Exception("User not found"));

        // Use the booking service to fetch all bookings for the user.
        List<BookingResponse> bookings = bookingService.getAllBookingsByUserId(userId);

        // Build and return the analytics DTO
        return new UserAnalytics(user.getName(), user.getEmail(), user.getNumber(), bookings);
    }


}