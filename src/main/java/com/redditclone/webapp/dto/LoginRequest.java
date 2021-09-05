package com.redditclone.webapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
//@Entity
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    private String username;

    private String password;
}
