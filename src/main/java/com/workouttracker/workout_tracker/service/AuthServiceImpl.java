package com.workouttracker.workout_tracker.service;

import com.workouttracker.workout_tracker.config.JwtService;
import com.workouttracker.workout_tracker.dto.*;
import com.workouttracker.workout_tracker.mapper.UserMapper;
import com.workouttracker.workout_tracker.model.Role;
import com.workouttracker.workout_tracker.model.User;
import com.workouttracker.workout_tracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserMapper userMapper;
    private final UserDetailsService userDetailsService;

    @Override
    public UserDTO register(RegisterRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException("Ce nom d’utilisateur est déjà pris");
        }
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.USER)
                .build();
        User saved = userRepository.save(user);
        return userMapper.toDto(saved);
    }

    @Override
    public AuthResponse authenticate(AuthRequest request) {
        // déclenche UsernameNotFoundException ou BadCredentialsException si échec
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getUsername());
        String token = jwtService.generateToken(userDetails);
        return new AuthResponse(token);
    }
}
