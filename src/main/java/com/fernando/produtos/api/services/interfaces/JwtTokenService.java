package com.fernando.produtos.api.services.interfaces;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.fernando.produtos.api.models.UserDetailsImpl;

public interface JwtTokenService {

    /**
     * Gera um token JWT para o usuário fornecido.
     *
     * @param user Detalhes do usuário para o qual o token será gerado.
     * @return Uma string representando o token JWT gerado.
     * @throws JWTCreationException Se ocorrer um erro ao gerar o token.
     */
    String generateToken(UserDetailsImpl user) throws JWTCreationException;

    /**
     * Obtém o assunto (subject) do token JWT, que geralmente é o nome de usuário.
     *
     * @param token O token JWT a ser verificado.
     * @return O assunto (subject) do token.
     * @throws JWTVerificationException Se o token for inválido ou expirado.
     */
    String getSubjectFromToken(String token) throws JWTVerificationException;
}