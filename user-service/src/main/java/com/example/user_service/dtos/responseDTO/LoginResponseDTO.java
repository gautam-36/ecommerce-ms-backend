package com.example.user_service.dtos.responseDTO;


public class LoginResponseDTO {
    private String token;
    private String refreshToken ;
    private String emailId ;
    private String userName ;
    private long expiryTime;
    private Integer HttpStatus;
    private String message;


    public LoginResponseDTO(String token, String refreshToken, String emailId, String userName, long expiryTime, Integer httpStatus, String message) {
        this.token = token;
        this.refreshToken = refreshToken;
        this.emailId = emailId;
        this.userName = userName;
        this.expiryTime = expiryTime;
        HttpStatus = httpStatus;
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(long expiryTime) {
        this.expiryTime = expiryTime;
    }

    public Integer getHttpStatus() {
        return HttpStatus;
    }

    public void setHttpStatus(Integer httpStatus) {
        HttpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
