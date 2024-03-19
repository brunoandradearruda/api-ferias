package com.seplagpb.apiferiasseplagpb.view;

import com.seplagpb.apiferiasseplagpb.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class FuncionarioEmFeriasViewController {

    private final FuncionarioService funcionarioService;

    @Autowired
    public FuncionarioEmFeriasViewController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping("/funcionarios-em-ferias")
    public String mostrarFuncionariosEmFerias(Model model) {
        model.addAttribute("funcionariosEmFerias", funcionarioService.funcionariosEmFerias());
        return "ferias/funcionarioEmferias";
    }
}
