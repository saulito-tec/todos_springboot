package com.luv2code.springboot.todos.service;

import com.luv2code.springboot.todos.entity.Authority;
import com.luv2code.springboot.todos.entity.User;
import com.luv2code.springboot.todos.repository.UserRepository;
import com.luv2code.springboot.todos.response.UserResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated() || authentication.getPrincipal().equals("anonymousUser")){
            throw new AccessDeniedException("Authentication required");
        }

        User user = (User) authentication.getPrincipal();
        return new UserResponse(
                user.getId(),
                user.getFirstName() + " " + user.getLastname(),
                user.getEmail(),
                user.getAuthorities().stream().map(auth ->(Authority) auth).toList()
        );
    }
}
