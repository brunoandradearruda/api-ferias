package com.seplagpb.apiferiasseplagpb.view;

import com.seplagpb.apiferiasseplagpb.dto.RegistrarFeriasRequest;
import com.seplagpb.apiferiasseplagpb.model.Funcionario;
import com.seplagpb.apiferiasseplagpb.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class RegistroFeriasViewController {

    private final FuncionarioService funcionarioService;


    @Autowired
    public RegistroFeriasViewController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @GetMapping("/registroFerias")
    public String mostrarFormularioRegistroFerias(Model model) {
        List<Funcionario> funcionarios = funcionarioService.findAll(); // Assumindo que você tem um serviço para buscar funcionários
        RegistrarFeriasRequest registroFeriasRequest = new RegistrarFeriasRequest();
        model.addAttribute("funcionarios", funcionarios);
        model.addAttribute("registroFeriasRequest", registroFeriasRequest);
        return "ferias/registroFerias";
    }


    @PostMapping("/registroFerias")
    public String registrarFerias(@ModelAttribute RegistrarFeriasRequest registroFeriasRequest, RedirectAttributes redirectAttributes) {
        try {
            // Assumindo que o método registrarPeriodoFerias retorna o funcionário atualizado
            Funcionario funcionarioAtualizado = funcionarioService.registrarPeriodoFerias(registroFeriasRequest.getFuncionarioId(), registroFeriasRequest.getInicioFerias(), registroFeriasRequest.getDias());
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Férias registradas com sucesso para " + funcionarioAtualizado.getNome() + "!");
        } catch (Exception e) {
            // Captura exceções genéricas para simplificar, mas considere tratar exceções específicas
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro ao registrar férias: " + e.getMessage());
        }
        return "redirect:/registroFerias"; // Redireciona para evitar reenvio do formulário
    }

}