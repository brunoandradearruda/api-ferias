package com.seplagpb.apiferiasseplagpb.controller;

import com.seplagpb.apiferiasseplagpb.model.Departamento;
import com.seplagpb.apiferiasseplagpb.service.DepartamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String salvarDepartamento(@RequestBody Departamento departamento) {
        departamentoService.salvarDepartamento(departamento);
        return "redirect:/departamentos/cadastrar";
    }



    @GetMapping("/excluir/{id}")
    public String excluirDepartamento(@PathVariable Long id) {
        departamentoService.excluirDepartamento(id);
        return "redirect:/departamentos/listar";
    }
}
