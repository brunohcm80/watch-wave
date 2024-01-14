package br.com.watchwave.service;

import br.com.watchwave.exception.VideoInvalidoException;
import br.com.watchwave.model.Categoria;
import br.com.watchwave.model.Video;
import br.com.watchwave.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VideoServiceImpl implements VideoService{

    private final VideoRepository videoRepository;

    @Override
    public Video cadastrarVideo(Video video) {
        video.setId(UUID.randomUUID());
        return videoRepository.save(video);
    }

    @Override
    public Video atualizarVideo(Video video) {
        videoRepository.findById(video.getId())
                .orElseThrow(() -> new VideoInvalidoException("Video nao localizado"));
        return videoRepository.save(video);
    }

    @Override
    public Page<Video> buscarVideosPorTitulo(String titulo, Pageable paginacao) {
        var listaVideo = videoRepository.findByTitulo(titulo);
        return new PageImpl<>(listaVideo, paginacao, listaVideo.size());
    }

    @Override
    public Page<Video> buscarVideosPorDataDaPublicacao(LocalDate dataPublicacao, Pageable paginacao) {
        var listaVideo = videoRepository.findByDataPublicacao(dataPublicacao);
        return new PageImpl<>(listaVideo, paginacao, listaVideo.size());
    }

    @Override
    public Page<Video> buscarVideosPorCategoria(Categoria categoria, Pageable paginacao) {
        var listaVideo = videoRepository.findByCategorias(categoria);
        return new PageImpl<>(listaVideo, paginacao, listaVideo.size());
    }

    @Override
    public Page<Video> listarTodosVideosOrdenadosPorDataPublicacao(Pageable paginacao) {
        var listaVideos = videoRepository.findAllByOrderByDataPublicacaoAsc();
        return new PageImpl<>(listaVideos, paginacao, listaVideos.size());
    }

    @Override
    public boolean excluirVideo(Video video) {
        videoRepository.findById(video.getId())
                .orElseThrow(() -> new VideoInvalidoException("Video nao localizado"));
        videoRepository.deleteById(video.getId());
        return true;
    }
}
