package com.seplagpb.apiferiasseplagpb.view;

import com.seplagpb.apiferiasseplagpb.model.Departamento;
import com.seplagpb.apiferiasseplagpb.model.Funcionario;
import com.seplagpb.apiferiasseplagpb.service.DepartamentoService;
import com.seplagpb.apiferiasseplagpb.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class FuncionarioViewController {

    private final FuncionarioService funcionarioService;

    private final DepartamentoService departamentoService;

    @Autowired
    public FuncionarioViewController(FuncionarioService funcionarioService, DepartamentoService departamentoService) {
        this.funcionarioService = funcionarioService;
        this.departamentoService = departamentoService;
    }



    @GetMapping("/funcionarios/editar/{id}")
    public String mostrarFormularioDeEdicao(@PathVariable("id") Long id, Model model) {
        Optional<Funcionario> funcionario = funcionarioService.findById(id);
        if (funcionario.isPresent()) {
            model.addAttribute("funcionario", funcionario.get());
            return "funcionario/edita-funcionario";
        } else {
            throw new IllegalArgumentException("ID de funcionário inválido:" + id);
        }
    }


    @PostMapping("/funcionarios/atualizar/{id}")
    public String atualizarFuncionario(@PathVariable("id") Long id, Funcionario funcionario, RedirectAttributes redirectAttributes) {
        funcionarioService.salvar(funcionario);
        redirectAttributes.addFlashAttribute("successMessage", "Dados do funcionário atualizados com sucesso!");
        return "redirect:/funcionarios/listar"; // Ajuste o caminho de redirecionamento conforme necessário
    }


    @GetMapping("/edicao/{id}")
    public String exibirFormularioEdicao(@PathVariable Long id, Model model) {
        model.addAttribute("funcionario", new Funcionario()); // Adiciona um novo funcionário ao modelo
        List<Departamento> departamentos = departamentoService.listarTodos(); // Obtenha a lista de departamentos
        model.addAttribute("departamentos", departamentos); // Adiciona a lista de departamentos ao modelo
        return "funcionario/edita-funcionario"; // Retorna o nome do template de edição
    }




}
