package com.seplagpb.apiferiasseplagpb.service;

import com.seplagpb.apiferiasseplagpb.model.Departamento;
import com.seplagpb.apiferiasseplagpb.repository.DepartamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class DepartamentoService {

    private final DepartamentoRepository departamentoRepository;

    @Autowired
    public DepartamentoService(DepartamentoRepository departamentoRepository) {
        this.departamentoRepository = departamentoRepository;
    }

    public List<Departamento> listarDepartamentos() {
        return departamentoRepository.findAll();
    }

    public Optional<Departamento> encontrarDepartamentoPorId(Long id) {
        return departamentoRepository.findById(id);
    }

    public Departamento salvarDepartamento(Departamento departamento) {
        return departamentoRepository.save(departamento);
    }

    public void excluirDepartamento(Long id) {
        departamentoRepository.deleteById(id);
    }
}
