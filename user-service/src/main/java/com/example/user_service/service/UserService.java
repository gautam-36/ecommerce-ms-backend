package com.example.user_service.service;

import com.example.user_service.domain.User;
import com.example.user_service.dtos.requestDTO.UserRequestDTO;

public interface UserService {

    public User registerUser(UserRequestDTO requestDTO);
    public Object login(String username, String password);

}
