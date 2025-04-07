package com.example.user_service.service;

import com.example.user_service.dtos.requestDTO.LogInUserDTO;
import com.example.user_service.dtos.responseDTO.LoginResponseDTO;

public interface AuthenticationService {

    public LoginResponseDTO login(LogInUserDTO loginUserDTO) throws Exception;
}
