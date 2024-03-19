package com.seplagpb.apiferiasseplagpb.view;
import com.seplagpb.apiferiasseplagpb.service.FuncionarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ListarFuncionarioController {

    private final FuncionarioService funcionarioService;

    public ListarFuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping("/funcionarios/listar")
    public String listarFuncionarios(Model model) {
        // Obtém a lista de funcionários do serviço
        model.addAttribute("funcionarios", funcionarioService.listarFuncionariosCadastrados());


        return "funcionario/listar";
    }
}
