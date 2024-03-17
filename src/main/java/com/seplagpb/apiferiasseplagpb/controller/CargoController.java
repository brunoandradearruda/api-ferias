package com.seplagpb.apiferiasseplagpb.controller;

import com.seplagpb.apiferiasseplagpb.model.Cargo;
import com.seplagpb.apiferiasseplagpb.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CargoController {

    private final CargoService cargoService;

    @Autowired
    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @GetMapping("/cargos/cadastrar")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("cargo", new Cargo());
        return "cargo/cadastro";
    }

    @PostMapping("/cargos/cadastrar")
    public String cadastrarCargo(Cargo cargo) {
        cargoService.salvarCargo(cargo);
        return "redirect:/cargos/cadastrar";
    }

    @GetMapping("/cargos/listar")
    public String listarCargos(Model model) {
        model.addAttribute("cargos", cargoService.listarCargos());
        return "cargo/listar";
    }
}
