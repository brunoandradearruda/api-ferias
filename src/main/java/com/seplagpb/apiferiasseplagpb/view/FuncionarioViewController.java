package com.seplagpb.apiferiasseplagpb.view;

import com.seplagpb.apiferiasseplagpb.model.Funcionario;
import com.seplagpb.apiferiasseplagpb.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class FuncionarioViewController {

    private final FuncionarioService funcionarioService;

    @Autowired
    public FuncionarioViewController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping("/funcionarios/editar/{id}")
    public String mostrarFormularioDeEdicao(@PathVariable("id") Long id, Model model) {
        Funcionario funcionario = funcionarioService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("ID de funcionário inválido:" + id));
        model.addAttribute("funcionario", funcionario);
        return "funcionario/edita-funcionario"; // Ajuste o nome do template se necessário
    }

    @PostMapping("/funcionarios/atualizar/{id}")
    public String atualizarFuncionario(@PathVariable("id") Long id, Funcionario funcionario, RedirectAttributes redirectAttributes) {
        funcionarioService.salvar(funcionario);
        redirectAttributes.addFlashAttribute("successMessage", "Dados do funcionário atualizados com sucesso!");
        return "redirect:/funcionarios/listar"; // Ajuste o caminho de redirecionamento conforme necessário
    }


}
