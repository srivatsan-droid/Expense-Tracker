package com.expense_service.authService.Dto.response;

import com.expense_service.authService.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private UUID id;
    private String username;
    private String email;
    private Role role;
    //Success Message in API
    private String message;
    private LocalDateTime createdAt;
}
