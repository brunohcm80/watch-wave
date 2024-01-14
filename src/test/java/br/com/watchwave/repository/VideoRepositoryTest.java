package br.com.watchwave.repository;

import br.com.watchwave.model.Categoria;
import br.com.watchwave.model.Video;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VideoRepositoryTest {

    @Mock
    private VideoRepository videoRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception{
        openMocks.close();
    }

    @Test
    void devePermitirCriarVideo(){
        // Arrange
        var video = gerarVideo();

        when(videoRepository.save(any(Video.class))).thenReturn(video);

        // Act
        var videoCriado = videoRepository.save(video);

        // Assert
        assertThat(videoCriado)
                .isNotNull()
                .isEqualTo(video);

        verify(videoRepository, times(1)).save(any(Video.class));

    }

    @Test
    void devePermitirListarVideoComOrdenacaoPorDataDePublicacao(){
        // Arrange
        var videos = gerarListaVideos();

        when(videoRepository.findAllByOrderByDataPublicacaoAsc()).thenReturn(videos);

        // Act
        var videosPesquisados = videoRepository.findAllByOrderByDataPublicacaoAsc();

        // Assert
        assertThat(videosPesquisados)
                .size()
                .isEqualTo(videos.size());

        assertThat(videosPesquisados.get(0).getId()).isEqualTo(videos.get(0).getId());
        assertThat(videosPesquisados.get(0).getDescricao()).isEqualTo(videos.get(0).getDescricao());

        assertThat(videosPesquisados.get(1).getId()).isEqualTo(videos.get(1).getId());
        assertThat(videosPesquisados.get(1).getDescricao()).isEqualTo(videos.get(1).getDescricao());

        verify(videoRepository, times(1)).findAllByOrderByDataPublicacaoAsc();
    }

    @Test
    void devePermitirListarVideoComFiltroPorTitulo(){
        // Arrange
        var videos = gerarListaVideos();

        when(videoRepository.findByTitulo(any(String.class))).thenReturn(videos);

        // Act
        var videoPesquisado = videoRepository.findByTitulo("Duna: Parte 2");

        // Assert
        assertThat(videoPesquisado)
                .isNotNull()
                .isEqualTo(videos);

        assertThat(videoPesquisado.get(0).getId()).isEqualTo(videos.get(0).getId());
        assertThat(videoPesquisado.get(0).getDataPublicacao()).isEqualTo(videos.get(0).getDataPublicacao());

        assertThat(videoPesquisado.get(1).getId()).isEqualTo(videos.get(1).getId());
        assertThat(videoPesquisado.get(1).getDataPublicacao()).isEqualTo(videos.get(1).getDataPublicacao());

        verify(videoRepository, times(1)).findByTitulo(any(String.class));
    }

    @Test
    void devePermitirListarVideoComFiltroPorDataPublicacao(){
        // Arrange
        var videos = gerarListaVideos();

        when(videoRepository.findByDataPublicacao(any(LocalDate.class))).thenReturn(videos);

        // Act
        var videosListados = videoRepository.findByDataPublicacao(LocalDate.now());

        // Assert
        assertThat(videosListados)
                .isNotNull()
                .isEqualTo(videos);

        assertThat(videosListados.get(0).getId()).isEqualTo(videos.get(0).getId());
        assertThat(videosListados.get(0).getDataPublicacao()).isEqualTo(videos.get(0).getDataPublicacao());

        assertThat(videosListados.get(1).getId()).isEqualTo(videos.get(1).getId());
        assertThat(videosListados.get(1).getDataPublicacao()).isEqualTo(videos.get(1).getDataPublicacao());

        verify(videoRepository, times(1)).findByDataPublicacao(any(LocalDate.class));
    }

    @Test
    void devePermitirListarVideoComFiltroPorCategoria(){
        // Arrange
        var videos = gerarListaVideos();
        var categoria = Categoria.builder()
                .id(UUID.randomUUID())
                .nome("Aventura")
                .build();

        when(videoRepository.findByCategorias(any(Categoria.class))).thenReturn(videos);

        // Act
        var videosListados = videoRepository.findByCategorias(categoria);

        // Assert
        assertThat(videosListados)
                .isNotNull()
                .isEqualTo(videos);

        assertThat(videosListados.get(0).getId()).isEqualTo(videos.get(0).getId());
        assertThat(videosListados.get(0).getDataPublicacao()).isEqualTo(videos.get(0).getDataPublicacao());

        assertThat(videosListados.get(1).getId()).isEqualTo(videos.get(1).getId());
        assertThat(videosListados.get(1).getDataPublicacao()).isEqualTo(videos.get(1).getDataPublicacao());

        verify(videoRepository, times(1)).findByCategorias(any(Categoria.class));

    }

    @Test
    void devePermitirExcluirVideo(){
        // Arrange
        var id = UUID.randomUUID();
        doNothing().when(videoRepository).deleteById(any(UUID.class));

        // Act
        videoRepository.deleteById(id);

        // Assert
        verify(videoRepository, times(1)).deleteById(any(UUID.class));

    }

    private Video gerarVideo(){
        return Video.builder()
                .id(UUID.randomUUID())
                .titulo("Wonka")
                .descricao("A história se concentra em um jovem Willy Wonka e como ele conheceu os Oompa-Loompas.")
                .url("https://www.youtube.com/watch?v=5a-qYjXNOtw")
                .dataPublicacao(LocalDate.parse("2023-12-07"))
                .build();
    }

    private List<Video> gerarListaVideos(){
        var primeiroVideo =
                    Video.builder()
                            .id(UUID.randomUUID())
                            .titulo("Guardioes da Galaxia Volume 3")
                            .descricao("Peter Quill deve reunir sua equipe para defender o universo e proteger um dos seus. Se a missão não for totalmente bem-sucedida, isso pode levar ao fim dos Guardiões.")
                            .url("https://www.youtube.com/watch?v=d1yNc9skssk")
                            .dataPublicacao(LocalDate.parse("2023-05-04"))
                            .build();

        var segundoVideo =
                Video.builder()
                        .id(UUID.randomUUID())
                        .titulo("Homem-Aranha: Através do Aranhaverso")
                        .descricao("Depois de se reunir com Gwen Stacy, Homem-Aranha é jogado no multiverso, onde ele encontra uma equipe encarregada de proteger sua própria existência.")
                        .url("https://www.youtube.com/watch?v=LZBlXkDvhh4")
                        .dataPublicacao(LocalDate.parse("2023-04-04"))
                        .build();

        var terceiroVideo =
                Video.builder()
                        .id(UUID.randomUUID())
                        .titulo("Duna: Parte 2")
                        .descricao("Paul Atreides se une a Chani e aos Fremen enquanto busca vingança contra os conspiradores que destruíram sua família. Enfrentando uma escolha entre o amor de sua vida e o destino do universo, ele deve evitar um futuro terrível que só ele pode prever.")
                        .url("https://www.youtube.com/watch?v=ncwsW3qxQlo")
                        .dataPublicacao(LocalDate.parse("2024-03-14"))
                        .build();

        var videos = new ArrayList<Video>();
        videos.add(primeiroVideo);
        videos.add(segundoVideo);
        videos.add(terceiroVideo);

        return videos;
    }
}
