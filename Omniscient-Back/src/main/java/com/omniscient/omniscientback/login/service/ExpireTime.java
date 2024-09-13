package com.omniscient.omniscientback.login.service;

public class ExpireTime {
    public static final long ACCESS_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 10; // 10시간
    public static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 30; // 30일
}
