package com.seplagpb.apiferiasseplagpb.controller;
import com.seplagpb.apiferiasseplagpb.model.SolicitacaoFerias;
import com.seplagpb.apiferiasseplagpb.service.SolicitacaoFeriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/solicitacoes-ferias")
public class SolicitacaoFeriasController {

    private final SolicitacaoFeriasService solicitacaoFeriasService;

    @Autowired
    public SolicitacaoFeriasController(SolicitacaoFeriasService solicitacaoFeriasService) {
        this.solicitacaoFeriasService = solicitacaoFeriasService;
    }

    @PostMapping("/{funcionarioId}")
    public ResponseEntity<SolicitacaoFerias> criarSolicitacao(@PathVariable Long funcionarioId, @RequestBody SolicitacaoFerias solicitacao) {
        SolicitacaoFerias novaSolicitacao = solicitacaoFeriasService.criarSolicitacaoFerias(funcionarioId, solicitacao);
        return new ResponseEntity<>(novaSolicitacao, HttpStatus.CREATED);
    }

    @GetMapping("/{funcionarioId}/dias-pendentes")
    public ResponseEntity<?> calcularDiasFeriasPendentes(@PathVariable Long funcionarioId) {
        int dias = solicitacaoFeriasService.calcularDiasFeriasPendentes(funcionarioId);
        return ResponseEntity.ok(Map.of("diasPendentes", dias));
    }

}
