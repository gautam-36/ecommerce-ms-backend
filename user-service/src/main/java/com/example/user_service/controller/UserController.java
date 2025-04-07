package com.example.user_service.controller;

import com.example.user_service.domain.User;
import com.example.user_service.dtos.requestDTO.UserRequestDTO;
import com.example.user_service.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "User Controller ", description = "API endpoints for user management ")
public class UserController {

    @Autowired
    UserService userService;

    @Operation(summary = "API for creating a new user", description = "This API is used to create a new user")
    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody UserRequestDTO requestDTO) {
        try{
            return new ResponseEntity<>(userService.registerUser(requestDTO),HttpStatus.CREATED);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
}
