package com.seplagpb.apiferiasseplagpb.service.impl;
import com.seplagpb.apiferiasseplagpb.dto.UsuarioDto;
import com.seplagpb.apiferiasseplagpb.infra.exceptions.BusinessException;
import com.seplagpb.apiferiasseplagpb.model.Usuario;
import com.seplagpb.apiferiasseplagpb.repository.UsuarioRepository;
import com.seplagpb.apiferiasseplagpb.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UsuarioDto salvar(UsuarioDto usuarioDto) {

        Usuario usuarioJaExiste = usuarioRepository.findByLogin(usuarioDto.login());

        if (usuarioJaExiste != null) {
            throw new BusinessException("Usuário já existe!");
        }

        var passwordHash = passwordEncoder.encode(usuarioDto.senha());

        Usuario entity = new Usuario(usuarioDto.nome(), usuarioDto.login(), passwordHash, usuarioDto.role());

        Usuario novoUsuario = usuarioRepository.save(entity);

        return new UsuarioDto(novoUsuario.getNome(), novoUsuario.getLogin(), novoUsuario.getSenha(), novoUsuario.getRole());
    }
}
