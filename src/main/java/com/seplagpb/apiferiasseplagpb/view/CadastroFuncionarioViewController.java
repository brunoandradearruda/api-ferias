package com.seplagpb.apiferiasseplagpb.view;
import com.seplagpb.apiferiasseplagpb.model.Funcionario;
import com.seplagpb.apiferiasseplagpb.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/funcionarios")
public class CadastroFuncionarioViewController {

    private final FuncionarioService funcionarioService;

    // Injetando o serviço no construtor
    @Autowired
    public CadastroFuncionarioViewController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping("/cadastro")
    public String exibirFormularioCadastro(Model model) {
        // Lógica para fornecer dados adicionais ao modelo, se necessário
        return "funcionario-cadastro"; // Este é o nome do seu arquivo HTML
    }

    @PostMapping("/cadastro")
    public String cadastrarFuncionario(@ModelAttribute Funcionario funcionario) {
        // Adicione a lógica para salvar o funcionário no banco de dados
        funcionarioService.salvarFuncionario(funcionario);

        // Redirecionamento para a página de sucesso após o cadastro
        return "redirect:/funcionarios/cadastro/sucesso";
    }

    @GetMapping("/cadastro/sucesso")
    public String mostrarSucessoCadastro() {
        return "cadastro-sucedido";
    }
}
