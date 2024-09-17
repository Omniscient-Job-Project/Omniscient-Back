package com.omniscient.omniscientback.login.model;

public class JwtTokenDTO {

    private String accessToken;
    private String refreshToken;
    private long accessTokenExpiry;
    private long refreshTokenExpiry;
    private String errorMessage;

    public JwtTokenDTO() {
    }

    public JwtTokenDTO(String accessToken, String refreshToken, long accessTokenExpiry, long refreshTokenExpiry, String errorMessage) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.accessTokenExpiry = accessTokenExpiry;
        this.refreshTokenExpiry = refreshTokenExpiry;
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public long getAccessTokenExpiry() {
        return accessTokenExpiry;
    }

    public void setAccessTokenExpiry(long accessTokenExpiry) {
        this.accessTokenExpiry = accessTokenExpiry;
    }

    public long getRefreshTokenExpiry() {
        return refreshTokenExpiry;
    }

    public void setRefreshTokenExpiry(long refreshTokenExpiry) {
        this.refreshTokenExpiry = refreshTokenExpiry;
    }

    @Override
    public String toString() {
        return "JwtTokenDTO{" +
                "accessToken='" + accessToken + '\'' +
                ", refreshToken='" + refreshToken + '\'' +
                ", accessTokenExpiry=" + accessTokenExpiry +
                ", refreshTokenExpiry=" + refreshTokenExpiry +
                ", errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
