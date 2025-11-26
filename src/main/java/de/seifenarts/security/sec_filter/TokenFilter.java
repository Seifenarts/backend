package de.seifenarts.security.sec_filter;

import de.seifenarts.security.AuthInfo;
import de.seifenarts.security.exceptions.InvalidTokenException;
import de.seifenarts.security.sec_service.TokenService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@Component
public class TokenFilter extends GenericFilterBean {

    private final TokenService service;

    public TokenFilter(TokenService service) {
        this.service = service;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String uri = request.getRequestURI();

        if (uri.startsWith("/order") || uri.startsWith("/products")) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }


        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }


        String token = getTokenFromRequest(request);

        if (token != null) {
            try {
                if (service.validateAccessToken(token)) {
                    Claims claims = service.getAccessClaims(token);
                    AuthInfo authInfo = service.mapClaimsToAuthInfo(claims);
                    SecurityContextHolder.getContext().setAuthentication(authInfo);
                } else {
                    SecurityContextHolder.clearContext();
                }
            } catch (InvalidTokenException e) {
                SecurityContextHolder.clearContext();
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            return token.substring(7);
        }
        return null;
    }
}

