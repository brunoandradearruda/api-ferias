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
    private final DepartamentoService departamentoService; // Adiciona a referência ao DepartamentoService

    // Modifica o construtor para incluir DepartamentoService
    @Autowired
    public CadastroFuncionarioController(FuncionarioService funcionarioService, DepartamentoService departamentoService) {
        this.funcionarioService = funcionarioService;
        this.departamentoService = departamentoService; // Inicializa o departamentoService
    }

    @GetMapping("/cadastro")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("funcionario", new Funcionario()); // Adiciona um novo funcionário ao modelo
        List<Departamento> departamentos = departamentoService.listarTodos(); // Obtenha a lista de departamentos
        model.addAttribute("departamentos", departamentos); // Adiciona a lista de departamentos ao modelo
        return "funcionario/cadastro"; // Retorna o nome do template

    }


    @PostMapping("/cadastro")
    public String cadastrarFuncionario(@ModelAttribute Funcionario funcionario) {
        // Chame o serviço para salvar o funcionário
        funcionarioService.salvarFuncionario(funcionario);
        // Redirecione para a página de listagem de funcionários ou outra página desejada
        return "funcionario/cadastro";
    }
}