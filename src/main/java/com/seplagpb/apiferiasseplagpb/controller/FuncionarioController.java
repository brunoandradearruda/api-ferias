package com.seplagpb.apiferiasseplagpb.controller;

import com.seplagpb.apiferiasseplagpb.dto.FuncionarioFeriasDTO;
import com.seplagpb.apiferiasseplagpb.dto.RegistrarFeriasRequest;
import com.seplagpb.apiferiasseplagpb.model.Funcionario;
import com.seplagpb.apiferiasseplagpb.service.FuncionarioService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/funcionarios")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    @Autowired
    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @PostMapping
    public ResponseEntity<Funcionario> salvarFuncionario(@RequestBody Funcionario funcionario) {
        Funcionario funcionarioSalvo = funcionarioService.salvarFuncionario(funcionario);
        return ResponseEntity.ok(funcionarioSalvo);
    }

    @GetMapping("/{id}/direito-ferias")
    public ResponseEntity<?> verificarDireitoFerias(@PathVariable Long id) {
        boolean temDireito = funcionarioService.temDireitoAFerias(id);
        return ResponseEntity.ok(Map.of("temDireitoAFerias", temDireito));
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletarFuncionario(@PathVariable Long id) {
        try {
            funcionarioService.deletarFuncionario(id);
            return ResponseEntity.ok().build(); // Retorna uma resposta 200 OK sem corpo
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build(); // Retorna uma resposta 404 Not Found se o funcionário não for encontrado
        }
    }

    @GetMapping("/ferias-pendentes")
    public ResponseEntity<List<FuncionarioFeriasDTO>> listarFuncionariosComFeriasPendentes() {
        List<FuncionarioFeriasDTO> funcionarios = funcionarioService.listarFuncionariosComFeriasPendentes();
        return ResponseEntity.ok(funcionarios);
    }



    @PostMapping("/{id}/registrar-ferias")
    public ResponseEntity<?> registrarPeriodoFerias(@PathVariable Long id, @RequestBody RegistrarFeriasRequest request) {
        try {
            Funcionario funcionario = funcionarioService.registrarPeriodoFerias(id, request.getInicioFerias(), request.getDias());
            return ResponseEntity.ok(funcionario);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


    }


}

