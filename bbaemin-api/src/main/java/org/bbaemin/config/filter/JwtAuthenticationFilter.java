package org.bbaemin.config.filter;

import io.jsonwebtoken.ExpiredJwtException;
import java.io.IOException;
import java.util.NoSuchElementException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bbaemin.api.user.repository.UserRepository;
import org.bbaemin.api.user.vo.User;
import org.bbaemin.jwt.JwtTokenProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {
            String accessToken = jwtTokenProvider.resolveToken(request);
            // 유효한 토큰인지 검사
            if (accessToken != null) {
                if (jwtTokenProvider.validateToken(accessToken)) {
                    this.setAuthentication(accessToken);
                } else {
                    Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
                    User getUser = userRepository.findByEmail(authentication.getName())
                            .orElseThrow(() -> new NoSuchElementException("email : " + authentication.getName()));
                    if (getUser.getRefreshToken() != null) {
                        if (jwtTokenProvider.validateToken(getUser.getRefreshToken())) {
                            // DB에 저장된 refreshToken이 유효할 경우 새로운 accessToken, refreshToken 발급
                            String newAccessToken = jwtTokenProvider.generateToken(getUser.getEmail());
                            String refreshToken = jwtTokenProvider.generateRefreshToken(getUser.getEmail());
                            getUser.setRefreshToken(refreshToken);
                            jwtTokenProvider.setHeaderAccessToken(response, newAccessToken);
                            this.setAuthentication(newAccessToken);
                        }
                    }
                }
            }
        } catch (ExpiredJwtException e) {
            log.info("Security exception for user {} - {}", e.getClaims().getSubject(), e.getMessage());
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

        filterChain.doFilter(request, response);
    }

    public void setAuthentication(String token) {
        Authentication authentication = jwtTokenProvider.getAuthentication(token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
