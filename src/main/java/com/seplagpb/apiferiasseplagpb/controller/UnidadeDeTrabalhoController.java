package com.seplagpb.apiferiasseplagpb.controller;

import com.seplagpb.apiferiasseplagpb.model.UnidadeDeTrabalho;
import com.seplagpb.apiferiasseplagpb.service.UnidadeDeTrabalhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/unidades")
public class UnidadeDeTrabalhoController {

    @Autowired
    private UnidadeDeTrabalhoService unidadeDeTrabalhoService;
    


}
