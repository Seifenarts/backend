package de.seifenarts.security;

import de.seifenarts.domain.entity.Role;
import lombok.EqualsAndHashCode;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@EqualsAndHashCode
public class AuthInfo implements Authentication {

    private boolean authenticated;
    private String username;
    private Role role;

    public AuthInfo(String username, Role role) {
        this.username = username;
        this.role = role;
    }

    @Override
    public String toString() {
        return String.format("Auth info: authenticated - %b, username - %s, role - %s.", authenticated, username, role);
    }

    @Override
    public String getName() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> "ROLE_" + role.name());
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return username;
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
            this.authenticated = isAuthenticated;
    }
}
