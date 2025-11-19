package de.seifenarts.security.sec_dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class TokenResponseDTO {

    private String accessToken;
    private String refreshToken;

    public TokenResponseDTO(String accessToken) {
        this.accessToken = accessToken;
    }

    public TokenResponseDTO(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return String.format("TokenResponseDTO: access token - %s, refresh token - %s", accessToken, refreshToken);
    }
}
