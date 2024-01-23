package ua.trydmi.trellolo.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ua.trydmi.trellolo.exception.RefreshTokenExpiredException;
import ua.trydmi.trellolo.exception.UserNotFoundException;
import ua.trydmi.trellolo.model.RefreshToken;
import ua.trydmi.trellolo.repository.RefreshTokenRepository;
import ua.trydmi.trellolo.repository.UserRepository;
import ua.trydmi.trellolo.service.RefreshTokenService;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${jwt.refreshExpiration}")
    private long refreshExpiration;

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUser(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found")));
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshExpiration));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        log.info("IN RefreshTokenServiceImpl.createRefreshToken - refreshToken: {} successfully created", refreshToken);
        return refreshToken;
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            log.warn("IN RefreshTokenServiceImpl.verifyExpiration - refreshToken: {} expired", token);
            throw new RefreshTokenExpiredException("refresh token expired");
        }
        log.info("IN RefreshTokenServiceImpl.verifyExpiration - refreshToken: {} not expired", token);
        return token;
    }

    @Transactional
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUser(userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("user not found")));
        log.warn("IN RefreshTokenServiceImpl.deleteByUserId - refreshTokens for user with id {} successfully deleted", userId);
    }

}
