package br.com.watchwave.repository;

import br.com.watchwave.model.Categoria;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import java.util.UUID;

public interface CategoriaRepository extends ReactiveMongoRepository<Categoria, UUID> {
}
