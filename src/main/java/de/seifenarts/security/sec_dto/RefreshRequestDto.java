package de.seifenarts.security.sec_dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EqualsAndHashCode
public class RefreshRequestDto {

    private String refreshToken;

    public RefreshRequestDto(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @Override
    public String toString() {
        return String.format("Refresh Request Dto: refresh  token - %s.", refreshToken);
    }
}
