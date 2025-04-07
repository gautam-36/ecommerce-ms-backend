package com.example.user_service.controller;

import com.example.user_service.dtos.requestDTO.LogInUserDTO;
import com.example.user_service.dtos.responseDTO.LoginResponseDTO;
import com.example.user_service.service.AuthenticationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Auth Controller ", description = "API endpoints for authentication management ")
public class AuthenticationController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping
    private ResponseEntity<?>authenticate(@RequestBody LogInUserDTO request)  throws Exception {
       try{
           LoginResponseDTO responseDTO = authenticationService.login(request);
           return new ResponseEntity<>(responseDTO, HttpStatus.OK);
       }catch (Exception e){
           return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
       }
    }
}
