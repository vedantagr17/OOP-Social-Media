package com.example.socialmedia.service;

import com.example.socialmedia.entity.UserEntity;
import com.example.socialmedia.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class UserService {

    @Autowired
    private UserRepo userRepository;

    public ResponseEntity<Object> login(String email, String password) {
        UserEntity user = userRepository.findByEmail(email);
        if (user == null) {
            return buildErrorResponse(HttpStatus.NOT_FOUND, "User does not exist");  // User not found (incorrect email)
        } else if (!user.getPassword().equals(password)) {
            return buildErrorResponse(HttpStatus.UNAUTHORIZED, "Username/Password Incorrect");  // Wrong password
        }
        return ResponseEntity.ok("Login Successful");
    }

    public ResponseEntity<Object> signup(String email, String name, String password) {
        if (userRepository.findByEmail(email) != null) {
            return buildErrorResponse(HttpStatus.FORBIDDEN, "Forbidden, Account already exists");
        }

        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setName(name);
        user.setPassword(password);
        userRepository.save(user);
        return ResponseEntity.ok("Account Creation Successful");
    }

    private ResponseEntity<Object> buildErrorResponse(HttpStatus status, String errorMessage) {
        Map<String, String> errorBody = new HashMap<>();
        errorBody.put("Error", errorMessage);
        return ResponseEntity.status(status).body(errorBody);
    }

    public ResponseEntity<Object> getUserDetails(Long userID) {
        UserEntity user = userRepository.findById(Math.toIntExact(userID)).orElse(null);

        if (user == null) {
            return buildErrorResponse(HttpStatus.NOT_FOUND, "User does not exist");
        }

        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("userID", String.valueOf(user.getUserID()));
        responseBody.put("email", user.getEmail());
        responseBody.put("name", user.getName());

        return ResponseEntity.ok(responseBody);
    }

    public ResponseEntity<Object> getAllUsers() {
        List<UserEntity> allUsers = new ArrayList<>();
        userRepository.findAll().forEach(allUsers::add);

        List<Object> xyz = new ArrayList<>();
        for (UserEntity user : allUsers) {
            Map<String, Object> userDetails = new HashMap<>();

            userDetails.put("name", user.getName());
            userDetails.put("userID", user.getUserID());
            userDetails.put("email", user.getEmail());

            xyz.add(userDetails);
        }

        return ResponseEntity.ok(xyz);
    }


}

