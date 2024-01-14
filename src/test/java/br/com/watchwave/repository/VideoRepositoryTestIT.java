package br.com.watchwave.repository;

import br.com.watchwave.model.Categoria;
import br.com.watchwave.model.Video;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class VideoRepositoryTestIT {

    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    void devePermitirCriarVideo (){
        // Arrange
        var video = gerarVideo();

        // Act
        var videoAtualizado = videoRepository.save(video);

        // Assert
        assertThat(videoAtualizado)
                .isInstanceOf(Video.class)
                .isNotNull();

        assertThat(videoAtualizado.getId()).isEqualTo(video.getId());
        assertThat(videoAtualizado.getDataPublicacao()).isEqualTo(video.getDataPublicacao());

        assertThat(videoAtualizado.getCategorias().size()).isEqualTo(video.getCategorias().size());

        assertThat(videoAtualizado.getCategorias().get(0).getId()).isEqualTo(video.getCategorias().get(0).getId());
        assertThat(videoAtualizado.getCategorias().get(0).getNome()).isEqualTo(video.getCategorias().get(0).getNome());

        assertThat(videoAtualizado.getCategorias().get(1).getId()).isEqualTo(video.getCategorias().get(1).getId());
        assertThat(videoAtualizado.getCategorias().get(1).getNome()).isEqualTo(video.getCategorias().get(1).getNome());

    }

    @Test
    void devePermitirListarVideoComOrdenacaoPorDataDePublicacao(){
        // Act
        var videos = videoRepository.findAllByOrderByDataPublicacaoAsc();

        // Assert
        assertThat(videos)
                .isNotNull()
                .isInstanceOf(List.class);

        assertThat(videos.get(0).getDataPublicacao())
                .isBeforeOrEqualTo(videos.get(1).getDataPublicacao());
        assertThat(videos.get(1).getDataPublicacao())
                .isBeforeOrEqualTo(videos.get(2).getDataPublicacao());
    }

    @Test
    void devePermitirListarVideoComFiltroPorTitulo(){
        // Arrange
        var titulo = "Guardioes da Galaxia Volume 3";

        // Act
        var videos = videoRepository.findByTitulo(titulo);

        // Assert
        assertThat(videos).isNotNull();
        assertThat(videos.get(0).getTitulo())
                .isEqualTo(titulo);
    }

    @Test
    void devePermitirListarVideoComFiltroPorDataPublicacao(){
        // Arrange
        var dataPesquisada = LocalDate.parse("2023-06-01");

        // Act
        var videos = videoRepository.findByDataPublicacao(dataPesquisada);

        // Assert
        assertThat(videos)
                .isNotNull();
        assertThat(videos.get(0).getDataPublicacao())
                .isEqualTo(dataPesquisada);
    }

    @Test
    void devePermitirListarVideoComFiltroPorCategorias(){

        // Arrange
        var categoriaPesquisada = buscarCategoria("Aventura").get();

        // Act
        var videos = videoRepository
                .findByCategorias(categoriaPesquisada);

        // Assert
        assertThat(videos)
                .isNotNull()
                .isInstanceOf(List.class);

        assertThat(videos.get(0).getCategorias())
                .contains(categoriaPesquisada);
        assertThat(videos.get(1).getCategorias())
                .contains(categoriaPesquisada);

    }

    @Test
    void devePermitirExcluirVideo(){
        // Arrange
        var videoExclusao = buscarVideoPorTitulo("Duna: Parte 2").get(0);

        // Act
        videoRepository.deleteById(videoExclusao.getId());

        // Assert
        var videoPesquisado = videoRepository.findById(videoExclusao.getId());
        assertThat(videoPesquisado).isEmpty();
    }

    private Video gerarVideo() {
        var categoriaAventura = buscarCategoria("Aventura").get();
        var categoriaComedia = buscarCategoria("Comedia").get();

        return Video.builder()
                .id(UUID.randomUUID())
                .titulo("Super Mario Bros. O Filme")
                .descricao("Mario e seu irmão Luigi são encanadores do Brooklyn, em Nova York. Um dia, eles vão parar no reino dos cogumelos, governado pela Princesa Peach. O local é ameaçado por Bowser, rei dos Koopas, que faz de tudo para conseguir reinar em todos os lugares.")
                .url("https://www.youtube.com/watch?v=Cb4WV4aXBpk")
                .dataPublicacao(LocalDate.parse("2023-04-05"))
                .categorias(Arrays.asList(categoriaAventura, categoriaComedia))
                .build();
    }

    private Optional<Categoria> buscarCategoria (String nome){
        return categoriaRepository.findByNome(nome);
    }

    private List<Video> buscarVideoPorTitulo (String titulo){
        return videoRepository.findByTitulo(titulo);
    }

}
