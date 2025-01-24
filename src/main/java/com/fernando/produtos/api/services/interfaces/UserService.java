package com.fernando.produtos.api.services.interfaces;


import com.fernando.produtos.api.dtos.requests.CreateUserRequest;
import com.fernando.produtos.api.dtos.requests.LoginUserRequests;
import com.fernando.produtos.api.dtos.requests.RecoveryJwtTokenRequest;

public interface UserService {

    /**
     * Autentica um usuário com base nas credenciais fornecidas e retorna um token JWT.
     *
     * @param loginUserDto DTO contendo as credenciais do usuário (email e senha).
     * @return Um DTO contendo o token JWT gerado.
     */
    RecoveryJwtTokenRequest authenticateUser(LoginUserRequests loginUserDto);

    /**
     * Cria um novo usuário com base nos dados fornecidos.
     *
     * @param createUserDto DTO contendo os dados do usuário a ser criado (email, senha e role).
     */
    void createUser(CreateUserRequest createUserDto);
}