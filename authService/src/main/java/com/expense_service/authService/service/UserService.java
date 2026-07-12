package com.expense_service.authService.service;

import com.expense_service.authService.Dto.request.UserRequest;
import com.expense_service.authService.Dto.response.UserResponse;
import com.expense_service.authService.Entity.Role;
import com.expense_service.authService.Entity.User;
import com.expense_service.authService.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepo;

    //Create User
    public UserResponse createUser(UserRequest request) {
        // 1. Check if user already exists
        if (userRepo.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        if (userRepo.findByUsername(request.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        // 2. Create User entity (not UserResponse)
        User createUser = User.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword()) // Will encode later
                .role(request.getRole() != null ? request.getRole() : Role.USER)
                .build();

        // 3. Save to database
        User saveUser = userRepo.save(createUser);

        // 4. Convert to DTO and return
        return UserResponse.builder()
                .id(saveUser.getId())
                .username(saveUser.getUsername())
                .email(saveUser.getEmail())
                .role(saveUser.getRole())
                .message("User registered successfully")
                .createdAt(saveUser.getCreatedAt())
                .build();
    }

    //Get All Users
    public List<UserResponse> getAllUsers() {
        List<User> getAllUsers = userRepo.findAll();

        return getAllUsers.stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .createdAt(user.getCreatedAt())
                        .build())
                .toList();
    }

    //Get User By Id
    public List<UserResponse> getUserById(UUID id) {
        Optional<User> getUserById = userRepo.findById(id);
        if(getUserById.isEmpty()) {
            throw new RuntimeException("User is Not Found");
        }
        return getUserById.stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .username(user.getUsername())
                        .email(user.getEmail())
                        .role(user.getRole())
                        .createdAt(user.getCreatedAt())
                        .build())
                .toList();
    }

    //Update User
    public UserResponse updateUsersById(UserRequest updatedRequest, UUID id) {
        // 1. Find existing user
        User existingUser = userRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // 2. Update only the fields provided
        existingUser.setUsername(updatedRequest.getUsername());
        existingUser.setEmail(updatedRequest.getEmail());
        existingUser.setPassword(updatedRequest.getPassword());
        if (updatedRequest.getRole() != null) {
            existingUser.setRole(updatedRequest.getRole());
        }

        // 3. Save to DB
        User updatedUser = userRepo.save(existingUser);

        // 4. Convert to DTO and return
        return UserResponse.builder()
                .id(updatedUser.getId())
                .username(updatedUser.getUsername())
                .email(updatedUser.getEmail())
                .role(updatedUser.getRole())
                .message("User updated successfully")
                .createdAt(updatedUser.getCreatedAt())
                .build();
    }

    //Delete User By Id
    public void deleteUserById(UUID id) {
        if (!userRepo.existsById(id)) {
            throw new RuntimeException("User not found");
        }
        userRepo.deleteById(id);
    }
}
