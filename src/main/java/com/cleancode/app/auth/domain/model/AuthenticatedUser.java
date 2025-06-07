package com.cleancode.app.auth.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticatedUser {
    private String login;
    private String name;
    private String picture;
    private String email;
}