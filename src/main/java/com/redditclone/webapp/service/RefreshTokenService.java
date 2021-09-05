package com.redditclone.webapp.service;

import com.redditclone.webapp.exception.RedditWebAppException;
import com.redditclone.webapp.model.RefreshToken;
import com.redditclone.webapp.repository.RefreshTokenRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public RefreshToken generateRefreshToken() {

        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    @Transactional(readOnly = true)
    public void validateRefreshToken(String token) {

        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new RedditWebAppException("Invalid refresh token"));
    }

    @Transactional
    public void deleteRefreshToken(String token) {

        refreshTokenRepository.deleteByToken(token);
    }

}
