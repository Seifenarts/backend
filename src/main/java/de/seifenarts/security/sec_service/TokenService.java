package de.seifenarts.security.sec_service;

import de.seifenarts.domain.entity.Role;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

@Service
public class TokenService {
    private SecretKey accessKey;
    private SecretKey refreshKey;

    public TokenService(
            @Value("${key.access}") String accessPhrase,
            @Value("${key.refresh}") String refreshPhrase) {
        this.accessKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(accessPhrase));
        this.refreshKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(refreshPhrase));
    }

}
