package de.seifenarts.security.sec_service;

import de.seifenarts.domain.entity.User;
import de.seifenarts.security.exceptions.BadCredentialsException;
import de.seifenarts.security.exceptions.InvalidTokenException;
import de.seifenarts.security.exceptions.UserNotFoundException;
import de.seifenarts.security.sec_dto.TokenResponseDTO;
import de.seifenarts.service.interfaces.UserService;
import io.jsonwebtoken.Claims;
import jakarta.security.auth.message.AuthException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    private final UserService userService;
    private final TokenService tokenService;
    private final Map<String, String> refreshStorage;
    private final BCryptPasswordEncoder passwordEncoder;

    public AuthService(UserService userService, TokenService tokenService, Map<String, String> refreshStorage, BCryptPasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.refreshStorage = new HashMap<>();
        this.passwordEncoder = passwordEncoder;
    }

    public TokenResponseDTO login(User inboundUser) {
        String username = inboundUser.getUsername();
        UserDetails foundUser;
        try {
            foundUser = userService.loadUserByUsername(username);
        } catch (Exception e) {
            throw new UserNotFoundException("User " + username + " not found");
        }

        if (!passwordEncoder.matches(inboundUser.getPassword(), foundUser.getPassword())) {
            throw new BadCredentialsException("Incorrect password");
        }

        String accessToken = tokenService.generateAccessToken(foundUser);
        String refreshToken = tokenService.generateRefreshToken(foundUser);

        refreshStorage.put(username, refreshToken);

        return new TokenResponseDTO(accessToken, refreshToken);
    }

    public TokenResponseDTO getNewAccessToken(String inboundRefreshToken) {
        Claims refreshClaims;
        try {
            refreshClaims = tokenService.getRefreshClaims(inboundRefreshToken);
        } catch (Exception e) {
            throw new InvalidTokenException("Refresh token is invalid or expired");
        }

        String username = refreshClaims.getSubject();
        String storedToken = refreshStorage.get(username);

        if (storedToken == null || !storedToken.equals(inboundRefreshToken)) {
            throw new InvalidTokenException("Refresh token rejected");
        }

        UserDetails foundUser = userService.loadUserByUsername(username);
        String newAccessToken = tokenService.generateAccessToken(foundUser);

        return new TokenResponseDTO(newAccessToken);
    }
}
