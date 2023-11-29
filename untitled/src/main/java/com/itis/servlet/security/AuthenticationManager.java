package com.itis.servlet.security;

public interface AuthenticationManager {
    boolean authenticate(String email, String password);
}
