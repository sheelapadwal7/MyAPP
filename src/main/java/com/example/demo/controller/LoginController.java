package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.demo.model.Login;
import com.example.demo.response.ErrorResponse;
import com.example.demo.response.SuccessResponse;
import com.example.demo.service.LoginService;


import java.util.List;

@RestController
@RequestMapping("/api/login")
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/add")
    public ResponseEntity<?> addLogin(@RequestBody Login login) {
        List<String> errors = loginService.validate(login);
        if (!errors.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(false, errors.toString()));
        }
        loginService.addLogin(login);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessResponse(true, "Login added successfully"));
    }
    
    
    @PostMapping("/check")
    public ResponseEntity<Object> checkUser(@RequestBody Login login) {
        boolean userExists = loginService.checkUserExists(login.getUsername(), login.getPassword());
        if (userExists) {
            return ResponseEntity.ok(new SuccessResponse(true, "User exists. Login successful."));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(false, "User does not exist."));
        }
    }
}
