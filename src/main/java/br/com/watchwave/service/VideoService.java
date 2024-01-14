package br.com.watchwave.service;

import br.com.watchwave.model.Categoria;
import br.com.watchwave.model.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface VideoService {

    public Video cadastrarVideo (Video video);

    public Video atualizarVideo (Video video);

    public Page<Video> buscarVideosPorTitulo (String titulo, Pageable paginacao);

    public Page<Video> buscarVideosPorDataDaPublicacao (LocalDate dataPublicacao, Pageable paginacao);

    public Page<Video> buscarVideosPorCategoria (Categoria categoria, Pageable paginacao);

    public Page<Video> listarTodosVideosOrdenadosPorDataPublicacao (Pageable paginacao);

    public boolean excluirVideo (Video video);
}
