package com.ijse.springboot.dto;

import com.ijse.springboot.entity.User.Role;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private Role role;
}
