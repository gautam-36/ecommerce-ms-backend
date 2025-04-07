package com.example.user_service.dtos.requestDTO;


import jakarta.validation.constraints.NotBlank;
import lombok.*;


public class LogInUserDTO {
    @NotBlank(message = "Please enter email id")
    private String email;
    @NotBlank(message = "Please enter password")
    private String password;

    public LogInUserDTO(){

    }

    public LogInUserDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
