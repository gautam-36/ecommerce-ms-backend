package com.example.user_service.service.impl;

import com.example.user_service.domain.User;
import com.example.user_service.dtos.requestDTO.LogInUserDTO;
import com.example.user_service.dtos.responseDTO.LoginResponseDTO;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.security.JwtService;
import com.example.user_service.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service

public class AuthenticationServiceImpl implements AuthenticationService {

    private final Logger log = Logger.getLogger(AuthenticationServiceImpl.class.getName());

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @Override
    public LoginResponseDTO login(LogInUserDTO loginUserDTO) throws Exception {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                    loginUserDTO.getEmail(),
                    loginUserDTO.getPassword())
            );
            Optional<User> user =  userRepository.findByEmail(loginUserDTO.getEmail());
            if(user.isPresent()){
                String token = jwtService.generateToken(user.get());
                long expiresIn = jwtService.extractExpirationInSeconds(token);
                String username = jwtService.extractUsername(token);
                String emailId = jwtService.extractEmail(token);
                log.info(":::::::::::::::: LOGIN SUCCESS:::::::::::::::");
                log.info("token: " + token);
                log.info("username: " + username);
                log.info("emailId: " + emailId);
                log.info("expiresIn: " + expiresIn);

                return new LoginResponseDTO(token,token,emailId,username,expiresIn,200,"Successfully Logged In");

            }else{
                log.info(":::::::::::::::: LOGIN FAILED:::::::::::::::");
                log.info("User not found");
                throw new Exception("User not Found");
            }
        }catch (Exception exception) {
            throw new Exception("Authentication failed: " + exception.getMessage());
        }
    }
}
