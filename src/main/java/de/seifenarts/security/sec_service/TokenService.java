package de.seifenarts.security.sec_service;

import de.seifenarts.domain.entity.Role;

import de.seifenarts.security.AuthInfo;
import de.seifenarts.security.exceptions.InvalidTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.JwtException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

@Service
public class TokenService {
    private final SecretKey accessKey;
    private final SecretKey refreshKey;

    public TokenService(
            @Value("${key.access}") String accessPhrase,
            @Value("${key.refresh}") String refreshPhrase) {
        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessPhrase));
        this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshPhrase));
    }

    public String generateAccessToken(UserDetails user) {
        LocalDateTime currentDate = LocalDateTime.now();
        Instant expiration = currentDate.plusDays(1).atZone(ZoneId.systemDefault()).toInstant();
        Date expirstionDate = Date.from(expiration);

        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(expirstionDate)
                .signWith(accessKey)
                .claim("role", user.getAuthorities())
                .claim("name", user.getUsername())
                .compact();
    }

    public String generateRefreshToken(UserDetails user) {
        LocalDateTime currentDate = LocalDateTime.now();
        Instant expiration = currentDate.plusDays(7).atZone(ZoneId.systemDefault()).toInstant();
        Date expirstionDate = Date.from(expiration);

        return Jwts.builder()
                .subject(user.getUsername())
                .expiration(expirstionDate)
                .signWith(refreshKey)
                .compact();
    }

    public boolean validateAccessToken(String accessToken) {
        return validateToken(accessToken, accessKey);
    }

    public boolean validateRefreshToken(String refreshToken) {
        return validateToken(refreshToken, refreshKey);
    }

    private boolean validateToken(String token, SecretKey key) {
        try {
            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }

    }

    public Claims getAccessClaims(String accessToken) {
        return getClaims(accessToken, accessKey);
    }

    public Claims getRefreshClaims(String refreshToken) {
        return getClaims(refreshToken, refreshKey);
    }

    private Claims getClaims(String token, SecretKey key) {
        try {
            return Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (Exception e) {
            throw new InvalidTokenException("Token is invalid or has wrong structure");
        }
    }

    public AuthInfo mapClaimsToAuthInfo(Claims claims) {
        String username = claims.getSubject();

        Object roleClaim = claims.get("role");

        if (!(roleClaim instanceof List<?> rolesRaw)) {
            throw new InvalidTokenException("Invalid JWT role format");
        }

        if (!(rolesRaw.get(0) instanceof LinkedHashMap<?, ?> roleMapRaw)) {
            throw new InvalidTokenException("Invalid JWT role structure");
        }

        String authority = (String) roleMapRaw.get("authority");   // ROLE_ADMIN

        if (authority == null) {
            throw new InvalidTokenException("Missing authority in JWT");
        }

        Role role = Role.valueOf(authority.replace("ROLE_", ""));

        AuthInfo auth = new AuthInfo(username, role);
        auth.setAuthenticated(true);

        return auth;

    }

}
