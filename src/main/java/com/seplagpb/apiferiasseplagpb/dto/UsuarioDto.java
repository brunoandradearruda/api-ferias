package com.seplagpb.apiferiasseplagpb.dto;

import com.seplagpb.apiferiasseplagpb.enums.RoleEnum;

public record UsuarioDto(
        String nome,
        String login,
        String senha,
        RoleEnum role
) {
}
