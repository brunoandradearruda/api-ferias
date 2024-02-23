package com.seplagpb.apiferiasseplagpb.view;
import com.seplagpb.apiferiasseplagpb.model.Funcionario;
import com.seplagpb.apiferiasseplagpb.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CadastroFuncionarioController {

    private final FuncionarioService funcionarioService;

    @Autowired
    public CadastroFuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping("/cadastro")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        return "funcionario/cadastro";
    }

    @PostMapping("/cadastro")
    public String cadastrarFuncionario(@ModelAttribute Funcionario funcionario) {
        // Chame o serviço para salvar o funcionário
        funcionarioService.salvarFuncionario(funcionario);
        // Redirecione para a página de listagem de funcionários ou outra página desejada
        return "funcionario/cadastro";
    }
}