package com.seplagpb.apiferiasseplagpb.service;
import com.seplagpb.apiferiasseplagpb.dto.AuthDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AutenticacaoService extends UserDetailsService {
    public String obterToken(AuthDto authDto);
    public String validaTokenJwt(String token);
}
