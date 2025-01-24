package com.fernando.produtos.api.dtos.requests;


import com.fernando.produtos.api.models.Role;

import java.util.List;

public record RecoveryUserRequest(

        Long id,
        String email,
        List<Role> roles

) {
}