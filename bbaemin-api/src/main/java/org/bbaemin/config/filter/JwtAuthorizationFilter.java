package org.bbaemin.config.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.bbaemin.jwt.JwtTokenProvider;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static io.jsonwebtoken.Jwts.builder;
import static io.jsonwebtoken.SignatureAlgorithm.HS256;
import static io.jsonwebtoken.security.Keys.hmacShaKeyFor;
import static java.lang.System.currentTimeMillis;
import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.security.core.context.SecurityContextHolder.clearContext;

@Slf4j
public class JwtAuthorizationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        super(authenticationManager);
        this.jwtTokenProvider = jwtTokenProvider;

        setFilterProcessesUrl("/api/v1/users/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("UsernameFilter");

        try {
            User credentialUser = new ObjectMapper().readValue(request.getInputStream(), User.class);

            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentialUser.getUsername(),
                            credentialUser.getPassword(),
                            credentialUser.getAuthorities()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        Claims claims = Jwts.claims();
        claims.put("username", ((User) authResult.getPrincipal()).getUsername());

        String token = builder()
                .setClaims(claims)
                .setIssuedAt(new Date(currentTimeMillis()))
                .setExpiration(new Date(jwtTokenProvider.getTokenValiditySeconds()))
                .signWith(hmacShaKeyFor(jwtTokenProvider.getSecretKey().getBytes(UTF_8)), HS256)
                .compact();

        response.addHeader("Authorization", "Bearer " + token);
        response.getWriter().write("Bearer " + token);
        response.getWriter().flush();
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        clearContext();
        getFailureHandler().onAuthenticationFailure(request, response, failed);
    }
}
