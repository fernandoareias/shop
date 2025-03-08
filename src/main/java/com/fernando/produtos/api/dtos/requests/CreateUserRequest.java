package com.fernando.produtos.api.dtos.requests;

import com.fernando.produtos.api.models.RoleName;

public record CreateUserRequest(

        String email,
        String password
) {
}