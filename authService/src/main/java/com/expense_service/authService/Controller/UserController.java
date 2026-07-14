package com.expense_service.authService.Controller;

import com.expense_service.authService.Dto.request.UserRequest;
import com.expense_service.authService.Dto.response.UserResponse;
import com.expense_service.authService.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userservice;

    // POST: Create User
    @PostMapping("/register")
    public ResponseEntity<UserResponse> createUser(@RequestBody UserRequest request) {
        UserResponse response = userservice.createUser(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    // GET: Get All Users
    @GetMapping("/getUsers")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> response = userservice.getAllUsers();
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    // GET: Get Users By ID
    @GetMapping("/{id}")
    public ResponseEntity<List<UserResponse>> getUserById(@PathVariable UUID id) {
        List<UserResponse> user = userservice.  getUserById(id);
        return ResponseEntity.ok(user);
    }

    // PUT: Update User By ID
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable UUID id, @RequestBody UserRequest request) {
        UserResponse updatedUser = userservice.updateUsersById(request, id);
        return ResponseEntity.ok(updatedUser);
    }

    //DELETE: Delete Users By Id
    @DeleteMapping("/{id}")  // Fixed typo: was "/{id"
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userservice.deleteUserById(id);
        return ResponseEntity.noContent().build();  // 204 No Content
    }
}
