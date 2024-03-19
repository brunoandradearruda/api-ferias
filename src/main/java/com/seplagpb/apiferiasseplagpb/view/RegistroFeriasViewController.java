package com.seplagpb.apiferiasseplagpb.view;
import org.springframework.web.multipart.MultipartFile;
import com.seplagpb.apiferiasseplagpb.dto.RegistrarFeriasRequest;
import com.seplagpb.apiferiasseplagpb.model.Funcionario;
import com.seplagpb.apiferiasseplagpb.service.FuncionarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
        List<Funcionario> funcionarios = funcionarioService.findAll();
        RegistrarFeriasRequest registroFeriasRequest = new RegistrarFeriasRequest();
        model.addAttribute("funcionarios", funcionarios);
        model.addAttribute("registroFeriasRequest", registroFeriasRequest);
        return "ferias/registroFerias";
    }

    @PostMapping("/registroFerias")
    public String registrarFerias(@ModelAttribute RegistrarFeriasRequest registroFeriasRequest, RedirectAttributes redirectAttributes) {
        try {

            if (funcionarioService.verificaFeriasRegistradas(registroFeriasRequest.getFuncionarioId(), registroFeriasRequest.getInicioFerias(), registroFeriasRequest.getDias())) {
                redirectAttributes.addFlashAttribute("mensagemErro", "FUNCIONÁRIO JÁ POSSUI FÉRIAS PARA O PERÍODO SOLICITADO OU JÁ ESTÁ EM FÉRIAS");
                return "redirect:/registroFerias";
            }


            Funcionario funcionarioAtualizado = funcionarioService.registrarPeriodoFerias(
                    registroFeriasRequest.getFuncionarioId(),
                    registroFeriasRequest.getInicioFerias(),
                    registroFeriasRequest.getDias()
            );
            redirectAttributes.addFlashAttribute("mensagemSucesso", "FÉRIAS REGISTRADA COM SUCESSO PARA " + funcionarioAtualizado.getNome() + "!");
        } catch (EntityNotFoundException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Funcionário não encontrado.");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Erro inesperado ao registrar férias: " + e.getMessage());
        }
        return "redirect:/registroFerias";
    }

}