package com.shrinitha.dochub.dto;

import lombok.Data;

import java.util.Set;

@Data
public class RegisterRequest {
    private String username;
    private String password;
    private Set<String> roles; // Ex: ["ADMIN"]
}
