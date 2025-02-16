package com.bookinghive.project.service;

import com.bookinghive.project.entity.User;
import com.bookinghive.project.model.LoginModel;
import com.bookinghive.project.model.ResponseModel;
import com.bookinghive.project.model.UserAnalytics;
import com.bookinghive.project.model.UserModel;

import java.util.List;

public interface UserService {
    UserModel registerUser(UserModel userModel) throws Exception;
    UserModel getUserByEmail(String email) throws Exception;
    List<UserModel> getAllUsers();
    User getUserByEmailAndPassword(String email, String password);
    ResponseModel loginUser(LoginModel loginModel);
    User getUserById(Long userId);
    UserModel getUserByID(Long userId);
    UserAnalytics getUserAnalytics(Long userId) throws Exception;
}