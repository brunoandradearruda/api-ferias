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

    public void editarDepartamento(Departamento departamento) {
        // Verifica se o departamento existe no banco de dados antes de editar
        if (departamentoRepository.existsById(departamento.getId())) {
            // Atualiza o departamento no banco de dados
            departamentoRepository.save(departamento);
        } else {
            throw new RuntimeException("Departamento não encontrado para edição.");
        }
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

    public List<Departamento> listarTodos() {
        return departamentoRepository.findAll();
    }


}


