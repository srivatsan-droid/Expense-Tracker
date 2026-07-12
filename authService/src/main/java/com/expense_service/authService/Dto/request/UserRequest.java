package com.expense_service.authService.Dto.request;

import com.expense_service.authService.Entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequest {
    private String username;
    private String email;
    private String password;
    private Role role;  // Default to USER in Service layer
}
