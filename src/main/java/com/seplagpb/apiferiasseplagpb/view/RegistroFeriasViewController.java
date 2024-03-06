package com.seplagpb.apiferiasseplagpb.view;

import com.seplagpb.apiferiasseplagpb.dto.RegistrarFeriasRequest;
import com.seplagpb.apiferiasseplagpb.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegistroFeriasViewController {

    private final FuncionarioService funcionarioService;

    @Autowired
    public RegistroFeriasViewController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    // Ajuste o caminho se necessário para evitar a ambiguidade com FuncionarioController
    @GetMapping("/funcionarios/{id}/registro-de-ferias")
    public String mostrarFormularioRegistroFerias(@PathVariable("id") Long id, Model model) {
        // Assegure-se de adicionar qualquer atributo necessário ao modelo aqui
        model.addAttribute("funcionarioId", id);
        // Pode ser necessário adicionar mais lógica aqui, como buscar informações do funcionário
        return "ferias/registroFerias"; // Certifique-se de que o caminho do template está correto
    }

    @PostMapping("/funcionarios/{id}/registro-de-ferias")
    public String registrarPeriodoFerias(@PathVariable("id") Long id, RegistrarFeriasRequest request, RedirectAttributes redirectAttributes) {
        // Implemente a lógica para registrar as férias do funcionário aqui
        // Por exemplo: funcionarioService.registrarFerias(id, request);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Férias registradas com sucesso!");
        return "redirect:/algum-caminho-para-confirmacao"; // Atualize com o caminho de redirecionamento desejado
    }
}
