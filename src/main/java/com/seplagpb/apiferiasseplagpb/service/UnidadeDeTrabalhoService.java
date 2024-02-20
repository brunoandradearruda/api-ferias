package com.seplagpb.apiferiasseplagpb.service;

import com.seplagpb.apiferiasseplagpb.model.UnidadeDeTrabalho;
import com.seplagpb.apiferiasseplagpb.repository.UnidadeDeTrabalhoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UnidadeDeTrabalhoService {

    private final UnidadeDeTrabalhoRepository unidadeDeTrabalhoRepository;

    @Autowired
    public UnidadeDeTrabalhoService(UnidadeDeTrabalhoRepository unidadeDeTrabalhoRepository) {
        this.unidadeDeTrabalhoRepository = unidadeDeTrabalhoRepository;
    }

    public List<UnidadeDeTrabalho> listarTodos() {
        return unidadeDeTrabalhoRepository.findAll();
    }

    public UnidadeDeTrabalho salvar(UnidadeDeTrabalho unidadeDeTrabalho) {
        // Aqui você pode adicionar qualquer lógica de negócio antes de salvar a unidade
        return unidadeDeTrabalhoRepository.save(unidadeDeTrabalho);
    }

    public Optional<UnidadeDeTrabalho> encontrarPorId(Long id) {
        return unidadeDeTrabalhoRepository.findById(id);
    }

    public void excluir(Long id) {
        unidadeDeTrabalhoRepository.deleteById(id);
    }

    // Método para atualizar uma unidade existente
    public UnidadeDeTrabalho salvarOuAtualizar(UnidadeDeTrabalho unidadeDeTrabalho) {
        if (unidadeDeTrabalho.getId() != null) {
            // A unidade já existe, atualize-a
            UnidadeDeTrabalho existente = unidadeDeTrabalhoRepository.findById(unidadeDeTrabalho.getId())
                    .orElseThrow(() -> new EntityNotFoundException("Unidade não encontrada: " + unidadeDeTrabalho.getId()));
            existente.setSetor(unidadeDeTrabalho.getSetor());
            existente.setCargo(unidadeDeTrabalho.getCargo());
            existente.setSimbolo(unidadeDeTrabalho.getSimbolo());
            return unidadeDeTrabalhoRepository.save(existente);
        } else {
            // Nova unidade, apenas salve-a
            return unidadeDeTrabalhoRepository.save(unidadeDeTrabalho);
        }
    }
}