package com.example.user_service.service.impl;

import com.example.user_service.domain.User;
import com.example.user_service.dtos.requestDTO.UserRequestDTO;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User registerUser(UserRequestDTO requestDTO) {
        User user = new User();
        user.setFirstName(requestDTO.getFirstName());
        user.setLastName(requestDTO.getLastName());
        user.setEmail(requestDTO.getEmail());
        user.setPassword(requestDTO.getPassword());
        user.setAddress(requestDTO.getAddress());
        return userRepository.save(user);
    }

    @Override
    public Object login(String username, String password) {
        return null;
    }
}
