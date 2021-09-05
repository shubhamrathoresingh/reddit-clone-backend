package com.redditclone.webapp.controller;

import com.redditclone.webapp.dto.AuthenticationResponse;
import com.redditclone.webapp.dto.LoginRequest;
import com.redditclone.webapp.dto.RefreshTokenRequest;
import com.redditclone.webapp.dto.RegisterRequest;
import com.redditclone.webapp.service.AuthService;
import com.redditclone.webapp.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    private final RefreshTokenService refreshTokenService;

    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody RegisterRequest registerRequest) {

        authService.signup(registerRequest);
        return new ResponseEntity<>("User registration successful", HttpStatus.OK);
    }

    @GetMapping("accountVerification/{token}")
    public ResponseEntity<String> verifyAccount(@PathVariable String token) {

        authService.verifyAccount(token);
        return new ResponseEntity<>("Account activation successful", HttpStatus.OK);
    }

    @PostMapping("/login")
    public AuthenticationResponse login(LoginRequest loginRequest) {
        
        return authService.login(loginRequest);
    }

    @PostMapping("/refresh/token")
    public AuthenticationResponse refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {

        return authService.refreshToken(refreshTokenRequest);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {

        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
        return ResponseEntity.status(HttpStatus.OK).body("Refresh token deleted successfully!!");
    }

}
