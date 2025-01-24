package com.fernando.produtos.api.services;

import com.fernando.produtos.api.configurations.SecurityConfiguration;
import com.fernando.produtos.api.dtos.requests.CreateUserRequest;
import com.fernando.produtos.api.dtos.requests.LoginUserRequests;
import com.fernando.produtos.api.dtos.requests.RecoveryJwtTokenRequest;
import com.fernando.produtos.api.models.Role;
import com.fernando.produtos.api.models.User;
import com.fernando.produtos.api.models.UserDetailsImpl;
import com.fernando.produtos.api.repositories.UserRepository;
import com.fernando.produtos.api.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

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

    // Método responsável por autenticar um usuário e retornar um token JWT
    public RecoveryJwtTokenRequest authenticateUser(LoginUserRequests loginUserDto) {
        // Cria um objeto de autenticação com o email e a senha do usuário
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(loginUserDto.email(), loginUserDto.password());

        // Autentica o usuário com as credenciais fornecidas
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        // Obtém o objeto UserDetails do usuário autenticado
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        // Gera um token JWT para o usuário autenticado
        return new RecoveryJwtTokenRequest(jwtTokenService.generateToken(userDetails));
    }

    // Método responsável por criar um usuário
    public void createUser(CreateUserRequest createUserDto) {

        // Cria um novo usuário com os dados fornecidos
        User newUser = User.builder()
                .email(createUserDto.email())
                // Codifica a senha do usuário com o algoritmo bcrypt
                .password(securityConfiguration.passwordEncoder().encode(createUserDto.password()))
                // Atribui ao usuário uma permissão específica
                .roles(List.of(Role.builder().name(createUserDto.role()).build()))
                .build();

        // Salva o novo usuário no banco de dados
        userRepository.save(newUser);
    }
}