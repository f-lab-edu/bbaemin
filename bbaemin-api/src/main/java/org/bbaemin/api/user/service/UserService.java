package org.bbaemin.api.user.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bbaemin.api.user.repository.UserRepository;
import org.bbaemin.api.user.vo.User;
import org.bbaemin.jwt.JwtResponse;
import org.bbaemin.jwt.JwtTokenProvider;
import org.bbaemin.user.controller.request.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NoSuchElementException("userId : " + userId));
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException("userEmail : " + email));
    }

    @Transactional
    public User join(User user) {
        User saveUser = userRepository.save(user);
        String refreshToken = jwtTokenProvider.generateRefreshToken(saveUser.getEmail());
        saveUser.setRefreshToken(refreshToken);
        return saveUser;
    }

    @Transactional
    public User updateUserInfo(Long userId, String nickname, String image, String phoneNumber) {
        // TODO - CHECK : update할 컬럼을 명시적으로 나타내주는 게 좋은가? 변경사항이 많은 경우에는?
        // updateUserInfo(User user) vs updateUserInfo(Long userId, String nickname, String image, String phoneNumber)
        User user = getUser(userId);
        user.setNickname(nickname);
        user.setImage(image);
        user.setPhoneNumber(phoneNumber);
        return user;
    }

    @Transactional
    public void quit(Long userId) {
        User user = getUser(userId);
        user.setDeleted(true);
        user.setDeletedAt(LocalDateTime.now());
    }

    @Transactional
    public JwtResponse login(LoginRequest loginRequest) {
        if (passwordEncoder.matches(loginRequest.getPassword(), getUserByEmail(loginRequest.getUserEmail()).getPassword())) {
            Authentication authentication = authenticate(loginRequest.getUserEmail(), loginRequest.getPassword());
            if (authentication != null) {
                SecurityContextHolder.getContext().setAuthentication(authentication);
                User user = (User) authentication.getPrincipal();
                // accessToken 발급
                String accessToken = jwtTokenProvider.generateToken(user.getEmail());
                // refreshToken은 DB에 저장
                String refreshToken = jwtTokenProvider.generateRefreshToken(user.getEmail());
                user.setRefreshToken(refreshToken);
                return JwtResponse.builder().accessToken(accessToken).refreshToken(refreshToken).build();
            }
        }
        return null;
    }

    private Authentication authenticate(String userEmail, String password) {
        try {
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEmail, password));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("BadCredentialsException");
        } catch(DisabledException e) {
            throw new DisabledException("DisabledException");
        } catch(LockedException e) {
            throw new LockedException("LockedException");
        } catch(UsernameNotFoundException e) {
            throw new UsernameNotFoundException("UsernameNotFoundException");
        } catch(AuthenticationException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
