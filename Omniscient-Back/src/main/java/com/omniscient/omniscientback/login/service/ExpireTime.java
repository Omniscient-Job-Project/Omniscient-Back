package com.omniscient.omniscientback.login.service;

public class ExpireTime {
    public static final Integer ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60; // 1시간
    public static final Integer REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 30; // 30일
}
