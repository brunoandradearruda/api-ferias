package com.seplagpb.apiferiasseplagpb.controller;
import com.seplagpb.apiferiasseplagpb.dto.HistoricoFeriasDTO;
import com.seplagpb.apiferiasseplagpb.service.HistoricoFeriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/historico-ferias")
public class HistoricoFeriasController {

    private final HistoricoFeriasService historicoFeriasService;

    @Autowired
    public HistoricoFeriasController(HistoricoFeriasService historicoFeriasService) {
        this.historicoFeriasService = historicoFeriasService;
    }

    @GetMapping("/historicoFerias")
    public ResponseEntity<List<HistoricoFeriasDTO>> gerarRelatorioHistoricoFeriasGozadas() {
        List<HistoricoFeriasDTO> historicoFerias = historicoFeriasService.gerarRelatorioHistoricoFeriasGozadas();
        return ResponseEntity.ok(historicoFerias);
    }
}
