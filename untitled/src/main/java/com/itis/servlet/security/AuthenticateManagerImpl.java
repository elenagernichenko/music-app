package com.itis.servlet.security;


import com.itis.model.User;
import com.itis.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;

public class AuthenticateManagerImpl implements AuthenticationManager {
    private final UserRepository userRepository;

    public AuthenticateManagerImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean authenticate(String userName, String password) {
        User user = userRepository.findOneByUserName(userName).orElse(null);
        if (user == null) {
            return false;
        }
        return BCrypt.checkpw(password, user.getPassword());
    }
}
