package com.seplagpb.apiferiasseplagpb.view;
import com.seplagpb.apiferiasseplagpb.model.Departamento;
import com.seplagpb.apiferiasseplagpb.model.Funcionario;
import com.seplagpb.apiferiasseplagpb.service.DepartamentoService;
import com.seplagpb.apiferiasseplagpb.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class CadastroFuncionarioController {

    private final FuncionarioService funcionarioService;
    private final DepartamentoService departamentoService;


    @Autowired
    public CadastroFuncionarioController(FuncionarioService funcionarioService, DepartamentoService departamentoService) {
        this.funcionarioService = funcionarioService;
        this.departamentoService = departamentoService;
    }

    @GetMapping("/cadastro")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        List<Departamento> departamentos = departamentoService.listarTodos();
        model.addAttribute("departamentos", departamentos);
        return "funcionario/cadastro";

    }


    @PostMapping("/cadastro")
    public String cadastrarFuncionario(@ModelAttribute Funcionario funcionario) {

        funcionarioService.salvarFuncionario(funcionario);

        return "funcionario/cadastro";
    }
}