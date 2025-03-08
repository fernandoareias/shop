package com.fernando.produtos.api.services;

import com.fernando.produtos.api.configurations.SecurityConfiguration;
import com.fernando.produtos.api.dtos.requests.CreateUserRequest;
import com.fernando.produtos.api.dtos.requests.LoginUserRequests;
import com.fernando.produtos.api.dtos.requests.RecoveryJwtTokenRequest;
import com.fernando.produtos.api.models.Role;
import com.fernando.produtos.api.models.RoleName;
import com.fernando.produtos.api.models.User;
import com.fernando.produtos.api.models.UserDetailsImpl;
import com.fernando.produtos.api.repositories.UserRepository;
import com.fernando.produtos.api.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenServiceImpl jwtTokenService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SecurityConfiguration securityConfiguration;


    public RecoveryJwtTokenRequest authenticateUser(LoginUserRequests loginUserDto) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        return new RecoveryJwtTokenRequest(jwtTokenService.generateToken(userDetails));
    }

    @Transactional
    public void createUser(CreateUserRequest createUserDto) {

        User newUser = User.builder()
                .email(createUserDto.email())
                .password(securityConfiguration.passwordEncoder().encode(createUserDto.password()))
                .roles(List.of(Role.builder().name(RoleName.ROLE_CUSTOMER).build()))
                .build();

        userRepository.save(newUser);
    }
}