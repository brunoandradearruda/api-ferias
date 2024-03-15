package com.seplagpb.apiferiasseplagpb.controller;
import com.seplagpb.apiferiasseplagpb.dto.UsuarioDto;
import com.seplagpb.apiferiasseplagpb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    private UsuarioDto salvar(@RequestBody UsuarioDto usuarioDto) {
        return  usuarioService.salvar(usuarioDto) ;
    }

    @GetMapping("/admin")
    private String getAdmin() {
        return "permissão de administrador";
    }

    @GetMapping("/user")
    private String getUser() {
        return "permissão de usuário";
    }
}
