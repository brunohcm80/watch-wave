package br.com.watchwave.service;

import br.com.watchwave.model.Categoria;
import br.com.watchwave.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

public interface VideoService {

    public Mono<Video> cadastrarVideo (Video video);

    public Mono<Video> atualizarVideo (UUID id, Video video);

    public Mono<Page<Video>> buscarVideosPorTitulo (String titulo, Pageable paginacao);

    public Mono<Page<Video>> buscarVideosPorDataDaPublicacao (LocalDate dataPublicacao, Pageable paginacao);

    public Mono<Page<Video>> buscarVideosPorCategoria (UUID idCategoria, Pageable paginacao);

    public Mono<Page<Video>> listarTodosVideosOrdenadosPorDataPublicacao (Pageable paginacao);

    public Mono<Boolean> excluirVideo (UUID id);
}
