package br.com.watchwave.repository;

import br.com.watchwave.model.Categoria;
import br.com.watchwave.model.Video;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface VideoRepository extends JpaRepository<Video, UUID> {
    List<Video> findByTitulo (String titulo);
    List<Video> findByDataPublicacao (LocalDate dataPublicacao);
    List<Video> findByCategoria (Categoria categoria);
    List<Video> findAllByOrderByDataPublicacaoAsc();
}
