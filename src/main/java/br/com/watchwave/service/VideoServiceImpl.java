package br.com.watchwave.service;

import br.com.watchwave.exception.VideoInvalidoException;
import br.com.watchwave.model.Video;
import br.com.watchwave.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService{

    @Autowired
    private final VideoRepository videoRepository;

    @Autowired
    private final CategoriaService categoriaService;

    @Override
    public Mono<Video> cadastrarVideo(Video video) {
        video.setId(UUID.randomUUID());
        return videoRepository.save(video);
    }

    @Override
    public Mono<Video> atualizarVideo(UUID id, Video video) {
        videoRepository.findById(id)
                .switchIfEmpty(Mono.error(new VideoInvalidoException("Video nao localizado")));
        return videoRepository.save(video);
    }

    @Override
    public Mono<Page<Video>> buscarVideosPorTitulo(String titulo, Pageable paginacao) {
        return videoRepository.findByTitulo(titulo)
                .collectList()
                .zipWith(videoRepository.findByTitulo(titulo).count())
                .map(p -> new PageImpl<>(p.getT1(), paginacao, p.getT2()));
    }

    @Override
    public Mono<Page<Video>> buscarVideosPorDataDaPublicacao(LocalDate dataPublicacao, Pageable paginacao) {
        return videoRepository.findByDataPublicacao(dataPublicacao)
                .collectList()
                .zipWith(videoRepository.findByDataPublicacao(dataPublicacao).count())
                .map(p -> new PageImpl<>(p.getT1(), paginacao, p.getT2()));
    }

    @Override
    public Mono<Page<Video>> buscarVideosPorCategoria(UUID idCategoria, Pageable paginacao) {

        var categoria = categoriaService.buscarCategoria(idCategoria)
                .switchIfEmpty(Mono.error(new VideoInvalidoException("Categoria nao localizada")));

        return videoRepository.findByCategorias(categoria.block())
                .collectList()
                .zipWith(videoRepository.findByCategorias(categoria.block()).count())
                .map(p -> new PageImpl<>(p.getT1(), paginacao, p.getT2()));
    }

    @Override
    public Mono<Page<Video>> listarTodosVideosOrdenadosPorDataPublicacao(Pageable paginacao) {
        return videoRepository.findAllByOrderByDataPublicacaoAsc()
                .collectList()
                .zipWith(videoRepository.findAllByOrderByDataPublicacaoAsc().count())
                .map(p -> new PageImpl<>(p.getT1(), paginacao, p.getT2()));
    }

    @Override
    public Mono<Boolean> excluirVideo(UUID id) {
        videoRepository.findById(id)
                .switchIfEmpty(Mono.error(new VideoInvalidoException("Video nao localizado")));
        videoRepository.deleteById(id);

        return Mono.just(true);
    }
}
