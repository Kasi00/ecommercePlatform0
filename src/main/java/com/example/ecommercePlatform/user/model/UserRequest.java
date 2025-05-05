package com.example.ecommercePlatform.user.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UserRequest {

    @NotBlank
    @Size(min = 5, max = 50)
    private String username;

    @NotBlank
    @Size(min=6)
    private String password;

    @NotEmpty
    private Set<Long> roleIds;




}
