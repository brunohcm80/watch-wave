package br.com.watchwave.service;

import br.com.watchwave.model.Categoria;
import br.com.watchwave.repository.CategoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class CategoriaService {
    @Autowired
    CategoriaRepository categoriaRepository;
    public Mono<Categoria> buscarCategoria (UUID id){
        return categoriaRepository.findById(id);
    }
}
