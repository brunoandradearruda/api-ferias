package com.seplagpb.apiferiasseplagpb.service;
import com.seplagpb.apiferiasseplagpb.model.Cargo;
import com.seplagpb.apiferiasseplagpb.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CargoService {

    private final CargoRepository cargoRepository;

    @Autowired
    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public void salvarCargo(Cargo cargo) {
        cargoRepository.save(cargo);
    }

    public List<Cargo> listarCargos() {
        return cargoRepository.findAll();
    }
}
