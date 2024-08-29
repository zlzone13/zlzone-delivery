package com.sparta.zlzonedelivery.global.auth.jwt;

import com.sparta.zlzonedelivery.user.UserRole;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.crypto.SecretKey;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private static final String BEARER_PREFIX = "Bearer ";

    @Value("${jwt.expiration}")
    private long expiration;

    @Value("${jwt.secret.key}")
    private String key;

    private SecretKey secretKey;

    @PostConstruct
    private void init() {
        byte[] bytes = Base64.getDecoder().decode(key);
        secretKey = Keys.hmacShaKeyFor(bytes);
    }

    public String createToken(String username, UserRole role) {
        Date now = new Date();

        return BEARER_PREFIX +
                Jwts.builder()
                        .subject(username)
                        .claim("role", role)
                        .expiration(new Date(now.getTime() + expiration))
                        .issuedAt(now)
                        .signWith(secretKey)
                        .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.error(e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT 토큰 만료");
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 JWT");
        } catch (IllegalArgumentException e) {
            log.error("잘못된 JWT");
        }
        return false;
    }

    public String substringToken(String token) {
        if (StringUtils.hasText(token) && token.startsWith(BEARER_PREFIX)) {
            return token.substring(7);
        }
        throw new NullPointerException("");
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        return request.getHeader(AUTHORIZATION_HEADER);
    }

    public void addJwtToCookie(String token, HttpServletResponse response) {
        token = URLEncoder.encode(token, StandardCharsets.UTF_8).replace("\\+", "%20");

        Cookie cookie = new Cookie(AUTHORIZATION_HEADER, token);
        cookie.setPath("/");

        response.addCookie(cookie);
    }


}
