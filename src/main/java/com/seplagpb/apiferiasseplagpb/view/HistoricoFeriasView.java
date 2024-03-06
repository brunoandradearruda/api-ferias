package com.seplagpb.apiferiasseplagpb.view;

import com.seplagpb.apiferiasseplagpb.dto.HistoricoFeriasDTO;
import com.seplagpb.apiferiasseplagpb.service.HistoricoFeriasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HistoricoFeriasView {

    private final HistoricoFeriasService historicoFeriasService;

    @Autowired
    public HistoricoFeriasView(HistoricoFeriasService historicoFeriasService) {
        this.historicoFeriasService = historicoFeriasService;
    }

    @GetMapping("/historicoFeriasModel")
    public String exibirHistoricoFerias(Model model) {
        List<HistoricoFeriasDTO> historicoFerias = historicoFeriasService.gerarRelatorioHistoricoFeriasGozadas();
        model.addAttribute("historicoFerias", historicoFerias);
        return "ferias/historicoFerias";
    }
}
