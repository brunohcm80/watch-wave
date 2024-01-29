package br.com.watchwave.repository;

import br.com.watchwave.model.Categoria;
import br.com.watchwave.model.Video;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.UUID;

public interface VideoRepository extends ReactiveMongoRepository<Video, UUID> {
    Flux<Video> findByTitulo (String titulo);
    Flux<Video> findByDataPublicacao (LocalDate dataPublicacao);
    Flux<Video> findByCategorias(Categoria categoria);
    Flux<Video> findAllByOrderByDataPublicacaoAsc();
}


