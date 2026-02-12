package com.example.user_order_management.service.impl;


import com.example.user_order_management.entity.User;
import com.example.user_order_management.entity.UserStatus;
import com.example.user_order_management.exception.UserNotFoundException;
import com.example.user_order_management.repository.UserRepository;
import com.example.user_order_management.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public User createUser(User user) {

        userRepository.findByEmail(user.getEmail())
                .ifPresent(u -> {
                    throw new RuntimeException("Email already exists!");
                });


        // Default status
        user.setStatus(UserStatus.ACTIVE);

        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User updateUser(Long id, User user) {

        User existingUser = userRepository.findById(id).orElse(null);

        if (existingUser == null) {
            return null;
        }

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setRole(user.getRole());

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long id) {

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        userRepository.delete(existingUser);
    }


}
