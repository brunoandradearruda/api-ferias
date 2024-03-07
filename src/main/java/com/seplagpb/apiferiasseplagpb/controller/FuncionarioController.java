package com.seplagpb.apiferiasseplagpb.controller;
import com.seplagpb.apiferiasseplagpb.dto.FuncionarioFeriasDTO;
import com.seplagpb.apiferiasseplagpb.dto.RegistrarFeriasRequest;
import com.seplagpb.apiferiasseplagpb.model.Funcionario;
import com.seplagpb.apiferiasseplagpb.service.FuncionarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @Autowired
    public FuncionarioController(FuncionarioService funcionarioService) {

        this.funcionarioService = funcionarioService;
    }

    @PostMapping("/cadastro")
    public ResponseEntity<Funcionario> salvarFuncionario(@RequestBody Funcionario funcionario) {
        Funcionario funcionarioSalvo = funcionarioService.salvarFuncionario(funcionario);
        return ResponseEntity.ok(funcionarioSalvo);
    }


    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Funcionario> atualizarFuncionario(@PathVariable Long id, @RequestBody Funcionario funcionarioAtualizado) {
        try {
            Funcionario funcionario = funcionarioService.atualizarFuncionario(id, funcionarioAtualizado);
            return ResponseEntity.ok(funcionario);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}/direito-ferias")
    public ResponseEntity<?> verificarDireitoFerias(@PathVariable Long id) {
        boolean temDireito = funcionarioService.temDireitoAFerias(id);
        return ResponseEntity.ok(Map.of("temDireitoAFerias", temDireito));
    }

    @PostMapping("/excluir/{id}")
    public ResponseEntity<?> deletarFuncionario(@PathVariable Long id) {
        try {
            funcionarioService.deletarFuncionario(id);
            return ResponseEntity.ok("Excluído com sucesso"); // Retorna uma resposta 200 OK com a mensagem
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // Retorna uma resposta 404 Not Found se o funcionário não for encontrado
        }
    }



    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<?> excluirFuncionario(@PathVariable Long id) {
        try {
            funcionarioService.deletarFuncionario(id);
            return ResponseEntity.ok("Excluído com sucesso"); // Retorna uma resposta 200 OK com a mensagem
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // Retorna uma resposta 404 Not Found se o funcionário não for encontrado
        }
    }



    @GetMapping("/ferias-pendentes")
    public ResponseEntity<List<FuncionarioFeriasDTO>> listarFuncionariosComFeriasPendentes() {
        List<FuncionarioFeriasDTO> funcionarios = funcionarioService.listarFuncionariosComFeriasPendentes();
        return ResponseEntity.ok(funcionarios);
    }


    @PostMapping("/{id}/registroFerias")
    public ResponseEntity<?> registrarPeriodoFerias(@PathVariable Long id, @RequestBody RegistrarFeriasRequest request) {
        try {
            Funcionario funcionario = funcionarioService.registrarPeriodoFerias(id, request.getInicioFerias(), request.getDias());
            return ResponseEntity.ok(funcionario);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());

        }
    }




    @GetMapping("/em-ferias")
    public List<Funcionario> funcionariosEmFerias() {
        LocalDate dataAtual = LocalDate.now();

        return funcionarioService.listarFuncionariosCadastrados().stream()
                .filter(funcionario -> {
                    long diasAdicionais = funcionarioService.calcularDiasAdicionaisFerias(funcionario);

                    return funcionario.getInicioFerias() != null &&
                            funcionario.getFimFerias() != null &&
                            (dataAtual.isEqual(funcionario.getInicioFerias().plusDays(diasAdicionais)) ||
                                    dataAtual.isEqual(funcionario.getFimFerias().plusDays(diasAdicionais)) ||
                                    (dataAtual.isAfter(funcionario.getInicioFerias().plusDays(diasAdicionais)) &&
                                            dataAtual.isBefore(funcionario.getFimFerias().plusDays(diasAdicionais))));
                })
                .collect(Collectors.toList());
    }




    @GetMapping("/listar-funcionarios")
    public List<Funcionario> listarFuncionarios() {

        return funcionarioService.listarFuncionariosCadastrados();
    }


    @GetMapping("/funcionarios/{id}/registroFerias")
    public String mostrarFormularioRegistroFerias(@PathVariable Long id, Model model) {
        RegistrarFeriasRequest registroFeriasRequest = new RegistrarFeriasRequest(); // Instancia um novo objeto
        model.addAttribute("id", id);
        model.addAttribute("registroFeriasRequest", registroFeriasRequest); // Adiciona ao modelo
        return "ferias/registroFerias";
    }

    @PostMapping("/funcionarios/{id}/registroFerias")
    public String registrarFerias(@PathVariable Long id, @ModelAttribute RegistrarFeriasRequest registroFeriasRequest, Model model) {
        // Lógica para registrar as férias do funcionário
        return "ferias/registroFerias"; // Redirecione para uma página de confirmação
    }

    @GetMapping("/funcionarios/excluir/{id}") // Use @GetMapping se você não configurou o suporte ao método DELETE
    public String excluirFuncionario(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        funcionarioService.excluirPorId(id);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Funcionário excluído com sucesso.");
        return "redirect:/funcionarios/listar"; // Substitua "/funcionarios/listar" pelo endpoint correto
    }

}













