package de.seifenarts.security.sec_conroller;

import de.seifenarts.domain.entity.User;
import de.seifenarts.security.sec_dto.RefreshRequestDto;
import de.seifenarts.security.sec_dto.TokenResponseDTO;
import de.seifenarts.security.sec_service.AuthService;
import jakarta.security.auth.message.AuthException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService service;

    public AuthController(AuthService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public TokenResponseDTO login(@RequestBody User user) {
        return service.login(user);
    }

    @PostMapping("/refresh")
    public TokenResponseDTO getNewAccessToken(@RequestBody RefreshRequestDto refreshRequest) {
        return service.getNewAccessToken(refreshRequest.getRefreshToken());
    }
}
