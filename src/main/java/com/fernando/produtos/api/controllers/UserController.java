package com.fernando.produtos.api.controllers;

import com.fernando.produtos.api.dtos.requests.CreateUserRequest;
import com.fernando.produtos.api.dtos.requests.LoginUserRequests;
import com.fernando.produtos.api.dtos.requests.RecoveryJwtTokenRequest;
import com.fernando.produtos.api.services.interfaces.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@Tag(name = "Usuários", description = "API para gerenciamento dos usuários")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    @Operation(summary = "Autenticacao do usuário", description = "Realiza a autenticacao do usuario")
    @ApiResponse(responseCode = "200", description = "Token JWT gerado com sucesso", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = RecoveryJwtTokenRequest.class)
    ))
    @ApiResponse(responseCode = "401", description = "Credenciais inválidas", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    @ApiResponse(responseCode = "400", description = "Requisição mal formatada", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    public ResponseEntity<RecoveryJwtTokenRequest> authenticateUser(@RequestBody @Valid      LoginUserRequests loginUserDto) {
        RecoveryJwtTokenRequest token = userService.authenticateUser(loginUserDto);
        return new ResponseEntity<>(token, HttpStatus.OK);
    }

    @PostMapping("/sign-up")
    @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = Void.class)
    ))
    @ApiResponse(responseCode = "400", description = "Dados de cadastro inválidos", content = @Content(
            mediaType = "application/json",
            schema = @Schema(implementation = ErrorResponse.class)
    ))
    public ResponseEntity<Void> createUser(@RequestBody @Valid CreateUserRequest createUserDto) {
        userService.createUser(createUserDto);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}