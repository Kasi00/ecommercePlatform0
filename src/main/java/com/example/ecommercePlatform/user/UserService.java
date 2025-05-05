package com.example.ecommercePlatform.user;

import com.example.ecommercePlatform.user.Persistance.Role;
import com.example.ecommercePlatform.user.Persistance.User;
import com.example.ecommercePlatform.user.Persistance.UserRepository;
import com.example.ecommercePlatform.user.model.UserRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RoleService roleService;

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(()->new RuntimeException("User not found"));
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    public void createUser(UserRequest request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setRoles(request.getRoleIds().stream()
                .map(roleService::getRole).collect(Collectors.toSet()));
        userRepository.save(user);

    }

    public void updateUser(Long id, UserRequest request) {
        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        if (request.getUsername() != null) {
            user.setUsername(request.getUsername());
        }

        if (request.getPassword() != null) {
            user.setPassword(request.getPassword()); // Always encode passwords
        }

        if (request.getRoleIds() != null && !request.getRoleIds().isEmpty()) {
            Set<Role> updatedRoles = request.getRoleIds().stream()
                    .map(roleService::getRole)
                    .collect(Collectors.toSet());
            user.setRoles(updatedRoles);
        }

        userRepository.save(user);
    }


    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(()->new RuntimeException("User not found"));
        userRepository.delete(user);
    }



}
