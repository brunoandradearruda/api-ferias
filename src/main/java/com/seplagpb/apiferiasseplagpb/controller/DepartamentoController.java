package com.seplagpb.apiferiasseplagpb.controller;

import com.seplagpb.apiferiasseplagpb.model.Departamento;
import com.seplagpb.apiferiasseplagpb.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
        departamento.setUnidadeTrabalho("SEPLAG");
        String nomeDepartamento = departamento.getNomeDepartamento().toUpperCase();
        List<Departamento> departamentos = departamentoService.listarDepartamentos();

        // Verificar se o departamento já está cadastrado
        boolean departamentoExistente = departamentos.stream()
                .anyMatch(dep -> dep.getNomeDepartamento().toUpperCase().equals(nomeDepartamento));

        if (departamentoExistente) {
            model.addAttribute("mensagem", "Departamento já cadastrado.");
            return "departamentos/cadastro";
        } else {
            departamento.setNomeDepartamento(nomeDepartamento);
            departamentoService.salvarDepartamento(departamento);
            return "redirect:/departamentos/cadastrar";
        }
    }

    @GetMapping("/excluir/{id}")
    public String excluirDepartamento(@PathVariable Long id) {
        departamentoService.excluirDepartamento(id);
        return "redirect:/departamentos/listar";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicao(@PathVariable Long id, Model model) {
        Optional<Departamento> optionalDepartamento = departamentoService.encontrarDepartamentoPorId(id);
        if (optionalDepartamento.isPresent()) {
            model.addAttribute("departamento", optionalDepartamento.get());
            return "departamentos/editar";
        } else {
            return "redirect:/departamentos/listar";
        }
    }

    @PostMapping("/editar")
    public String editarDepartamento(@ModelAttribute("departamento") Departamento departamento) {
        if (departamento.getId() == null) {
            // Adicione tratamento para lidar com o ID nulo
        }
        departamentoService.editarDepartamento(departamento);
        return "redirect:/departamentos/listar";
    }



    @PutMapping("/editar/{id}")
    public String editarDepartamento(@PathVariable("id") Long id, @ModelAttribute("departamento") Departamento departamento) {
        departamento.setId(id);
        departamentoService.editarDepartamento(departamento);
        return "redirect:/departamentos";
    }
}
