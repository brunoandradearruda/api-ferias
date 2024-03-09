package com.seplagpb.apiferiasseplagpb.controller;

import com.seplagpb.apiferiasseplagpb.model.Departamento;
import com.seplagpb.apiferiasseplagpb.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

    private final DepartamentoService departamentoService;

    @Autowired
    public DepartamentoController(DepartamentoService departamentoService) {
        this.departamentoService = departamentoService;
    }

    @GetMapping("/listar")
    public String listarDepartamentos(Model model) {
        model.addAttribute("departamentos", departamentoService.listarDepartamentos());
        return "departamentos/listar";
    }

    @GetMapping("/cadastrar")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("departamento", new Departamento());
        return "departamentos/cadastro";
    }

    @PostMapping("/salvar")
    public String salvarDepartamento(@ModelAttribute("departamento") Departamento departamento, Model model) {
        departamento.setUnidadeTrabalho("SEPLAG"); // Define a unidade de trabalho como SEPLAG
        String nomeDepartamento = departamento.getNomeDepartamento().toUpperCase(); // Converter para maiúsculas
        List<Departamento> departamentos = departamentoService.listarDepartamentos();

        // Verificar se o departamento já está cadastrado
        boolean departamentoExistente = departamentos.stream()
                .anyMatch(dep -> dep.getNomeDepartamento().toUpperCase().equals(nomeDepartamento));

        if (departamentoExistente) {
            model.addAttribute("mensagem", "Departamento já cadastrado.");
            return "departamentos/cadastro";
        } else {
            departamento.setNomeDepartamento(nomeDepartamento); // Garantir que o nome seja maiúsculo
            departamentoService.salvarDepartamento(departamento);
            return "redirect:/departamentos/cadastrar";
        }
    }


    @GetMapping("/excluir/{id}")
    public String excluirDepartamento(@PathVariable Long id) {
        departamentoService.excluirDepartamento(id);
        return "redirect:/departamentos/listar";
    }

}
