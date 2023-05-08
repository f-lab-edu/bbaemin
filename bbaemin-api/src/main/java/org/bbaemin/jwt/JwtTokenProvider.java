package org.bbaemin.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;

@Slf4j
@Component
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;

    private final String secret;
    private final long tokenValidityInMilliseconds;
    private final long refreshTokenValidityInMilliseconds;
    private static final String TOKEN_PREFIX = "Bearer ";

    public JwtTokenProvider(@Value("${spring.jwt.secret}") String secret,
                            @Value("${spring.jwt.token-validity-in-seconds}") long tokenValidityInMilliseconds,
                            UserDetailsService userDetailsService) {
        this.secret = secret;
        this.tokenValidityInMilliseconds = tokenValidityInMilliseconds * 1000;
        this.refreshTokenValidityInMilliseconds = tokenValidityInMilliseconds * 60 * 24 * 2;
        this.userDetailsService = userDetailsService;
    }

    private Key getSigningKey(String secretKey) {
        return hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * <pre>
     * 1. MethodName : generateToken
     * 2. ClassName  : JwtTokenProvider.java
     * 3. Comment    : JWT 토큰 발급
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 03. 01.
     * </pre>
     */
    public String generateToken(String userEmail) {
        Claims claims = Jwts.claims().setSubject(userEmail);
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.tokenValidityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .signWith(getSigningKey(secret), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * <pre>
     * 1. MethodName : doGenerateRefreshToken
     * 2. ClassName  : JwtTokenProvider.java
     * 3. Comment    : JWT 리프레시 토큰 발급
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 03. 01.
     * </pre>
     */
    public String generateRefreshToken(String userEmail) {
        Claims claims = Jwts.claims().setSubject(userEmail);
        long now = (new Date()).getTime();
        Date validity = new Date(now + this.refreshTokenValidityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(validity)
                .signWith(getSigningKey(secret), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * <pre>
     * 1. MethodName : getAuthentication
     * 2. ClassName  : JwtTokenProvider.java
     * 3. Comment    : JWT 토큰으로 인증정보 조회
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 03. 01.
     * </pre>
     */
    public Authentication getAuthentication(String token) {
        String subject = Jwts.parserBuilder()
                .setSigningKey(getSigningKey(secret))
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();

        UserDetails userDetails = userDetailsService.loadUserByUsername(subject);
        return new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
    }

    /**
     * <pre>
     * 1. MethodName : validateToken
     * 2. ClassName  : JwtTokenProvider.java
     * 3. Comment    : JWT 토큰 검증
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 03. 01.
     * </pre>
     */
    public Boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(getSigningKey(secret)).build().parseClaimsJws(token);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    /**
     * <pre>
     * 1. MethodName : resolveToken
     * 2. ClassName  : JwtTokenProvider.java
     * 3. Comment    : request header에서 token 값 가져오기
     * 4. 작성자      : CHO
     * 5. 작성일      : 2023. 03. 01.
     * </pre>
     */
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("Authorization");
    }

    /**
     * <pre>
     * 1. MethodName : setHeaderAccessToken
     * 2. ClassName  : JwtUtil.java
     * 3. Comment    : Header 토큰 정보 세팅
     * 4. 작성자      : CHO
     * 5. 작성일      : 2021. 07. 07.
     * </pre>
     */
    public void setHeaderAccessToken(HttpServletResponse response, String accessToken) {
        response.setHeader("Authorization", TOKEN_PREFIX + accessToken);
    }

    public String getSecretKey() {
        return this.secret;
    }

    public long getTokenValiditySeconds() {
        return this.tokenValidityInMilliseconds;
    }
}
