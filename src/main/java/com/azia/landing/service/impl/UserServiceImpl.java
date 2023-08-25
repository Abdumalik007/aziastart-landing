package com.azia.landing.service.impl;

import com.azia.landing.dao.User;
import com.azia.landing.dto.custom.LoginRequest;
import com.azia.landing.dto.custom.LoginResponse;
import com.azia.landing.redis.UserSession;
import com.azia.landing.redis.UserSessionRedisRepository;
import com.azia.landing.repository.UserRepository;
import com.azia.landing.role.Role;
import com.azia.landing.security.jwt.JwtService;
import com.azia.landing.service.main.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.azia.landing.helper.ResponseEntityHelper.INTERNAL_ERROR;
import static com.azia.landing.helper.ResponseEntityHelper.OK_MESSAGE;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserSessionRedisRepository redisRepository;
    private final JwtService jwtService;


    @Override
    public ResponseEntity<?> login(LoginRequest loginRequest, HttpServletRequest request) {
        Optional<User> userOptional = userRepository.findUserByEmail(loginRequest.getEmail());
        if(userOptional.isEmpty())
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();

        if(!passwordEncoder.matches(loginRequest.getPassword(), userOptional.get().getPassword()))
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).build();

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        userOptional.get(),
                        null,
                        userOptional.get().getAuthorities()
                );

        authenticationToken.setDetails(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);


        String uuid = UUID.randomUUID().toString();

        UserSession userSession = UserSession.builder()
                .id(uuid).value(userOptional.get()).build();

        try {
            redisRepository.save(userSession);
        }catch (Exception e){
            logger.error("Error while logging in: ".concat(e.getMessage()));
            return INTERNAL_ERROR();
        }
        String jwtToken = jwtService.generateToken(uuid);
        LoginResponse response = new LoginResponse();
        Role role = userOptional.get().getRole();
        response.setRole(role.name());
        response.setToken(jwtToken);

        return ResponseEntity.ok(response);
    }


    @Override
    public ResponseEntity<?> logout(HttpServletRequest request) {
        try {
            String token = request.getHeader("Authorization").substring(7);
            String uuid = jwtService.extractSubject(token);
            redisRepository.deleteById(uuid);
            return OK_MESSAGE();
        }catch (Exception e){
            return INTERNAL_ERROR();
        }
    }


}


