package br.com.watchwave.repository;

import br.com.watchwave.model.Video;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
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
                .isNotNull();
    }

    @Test
    void devePermitirListarVideoComOrdenacaoPorDataDePublicacao(){
        // Act
        var videos = videoRepository.findAllByOrderByDataPublicacaoAsc();

        // Assert
        assertThat(videos)
                .isNotNull()
                .isInstanceOf(Flux.class);
    }

    @Test
    void devePermitirListarVideoComFiltroPorTitulo(){
        // Arrange
        var titulo = "Guardioes da Galaxia Volume 3";

        // Act
        var videos = videoRepository.findByTitulo(titulo);

        // Assert
        assertThat(videos).isNotNull();
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

    }

    @Test
    void devePermitirListarVideoComFiltroPorCategorias(){

        // Arrange
        var categoriaPesquisada = categoriaRepository
                .findById(UUID.fromString("72ac1bf5-acc3-4475-bb01-589738d100dd"))
                .block();

        // Act
        var videos = videoRepository
                .findByCategorias(categoriaPesquisada);

        // Assert
        assertThat(videos)
                .isNotNull()
                .isInstanceOf(Flux.class);

    }

    @Test
    void devePermitirExcluirVideo(){
        // Arrange
        var idExclusao = UUID.fromString("76b6c4df-5439-4935-a5c5-45561008c780");

        // Act
        var resultado = videoRepository.deleteById(idExclusao);

        // Assert
        assertThat(resultado).isInstanceOf(Mono.class);
    }

    private Video gerarVideo() {
        var categoriaAventura = categoriaRepository
                .findById(UUID.fromString("72ac1bf5-acc3-4475-bb01-589738d100dd"))
                .block();
        var categoriaComedia = categoriaRepository
                .findById(UUID.fromString("2cad1d9c-53c6-4860-b6c9-16bdb21ea933"))
                .block();

        return Video.builder()
                .id(UUID.randomUUID())
                .titulo("Super Mario Bros. O Filme")
                .descricao("Mario e seu irmão Luigi são encanadores do Brooklyn, em Nova York. Um dia, eles vão parar no reino dos cogumelos, governado pela Princesa Peach. O local é ameaçado por Bowser, rei dos Koopas, que faz de tudo para conseguir reinar em todos os lugares.")
                .url("https://www.youtube.com/watch?v=Cb4WV4aXBpk")
                .dataPublicacao(LocalDate.parse("2023-04-05"))
                .categorias(Arrays.asList(categoriaAventura, categoriaComedia))
                .build();
    }
}
