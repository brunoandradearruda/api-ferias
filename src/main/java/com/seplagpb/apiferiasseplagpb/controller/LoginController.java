package com.seplagpb.apiferiasseplagpb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // Se necessário, adicione um método para "/home" ou ajuste conforme a lógica da sua aplicação
}