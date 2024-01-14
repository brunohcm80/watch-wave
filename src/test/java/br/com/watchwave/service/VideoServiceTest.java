package br.com.watchwave.service;

import br.com.watchwave.exception.VideoInvalidoException;
import br.com.watchwave.model.Categoria;
import br.com.watchwave.model.Video;
import br.com.watchwave.repository.VideoRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class VideoServiceTest {

    private VideoService videoService;

    @Mock
    private VideoRepository videoRepository;

    AutoCloseable openMocks;

    @BeforeEach
    void setup(){
        openMocks = MockitoAnnotations.openMocks(this);
        videoService = new VideoServiceImpl(videoRepository);
    }

    @AfterEach
    void tearDown() throws Exception{
        openMocks.close();
    }

    @Test
    void devePermitirCriarVideo (){
        // Arrange
        var video = gerarVideo();
        when(videoRepository.save(any(Video.class)))
                .thenAnswer(i -> (i.getArgument(0)));

        // Act
        var videoCadastrado = videoService.cadastrarVideo(video);

        // Assert
        assertThat(videoCadastrado)
                .isNotNull()
                .isInstanceOf(Video.class);

        assertThat(videoCadastrado.getId()).isEqualTo(video.getId());
        assertThat(videoCadastrado.getCategorias().get(0).getNome())
                .isEqualTo(video.getCategorias().get(0).getNome());
    }

    @Test
    void devePermitirAtualizarVideo (){
        // Arrange
        var video = gerarVideo();
        video.setId(UUID.fromString("11d171cd-8bdf-4e5c-bbe1-2d0e112cc943"));

        when(videoRepository.findById(any(UUID.class))).thenReturn(Optional.of(video));
        when(videoRepository.save(any(Video.class))).thenAnswer(i -> (i.getArgument(0)));

        // Act
        var videoAtualizado = videoService.atualizarVideo(video);

        // Assert
        assertThat(video.getId()).isEqualTo(videoAtualizado.getId());
        assertThat(video.getTitulo()).isEqualTo(videoAtualizado.getTitulo());
        assertThat(video.getCategorias().get(0).getId())
                .isEqualTo(videoAtualizado.getCategorias().get(0).getId());
        assertThat(video.getCategorias().get(0).getNome())
                .isEqualTo(videoAtualizado.getCategorias().get(0).getNome());

        verify(videoRepository, times(1)).findById(any(UUID.class));
        verify(videoRepository, times(1)).save(any(Video.class));
    }

    @Test
    void deveGerarExcecao_QuandoAtualizarVideo_IdInexistente(){
        // Arrange
        var id = UUID.fromString("e35018de-7b1b-4b60-ae2a-a46fdd1c2d55");
        var video = gerarVideo();
        video.setId(id);

        when(videoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Act
        // Assert
        assertThatThrownBy(() -> videoService.atualizarVideo(video))
                .isInstanceOf(VideoInvalidoException.class)
                .hasMessage("Video nao localizado");

        verify(videoRepository, times(1)).findById(any(UUID.class));

    }

    @Test
    void devePermitirListagemVideoOrdenadaPorDataDePublicacao(){
        // Arrange
        var primeiroVideoLista = gerarVideo();
        primeiroVideoLista.setId(UUID.fromString("45a430a9-144f-4dbd-b473-599785238119"));
        primeiroVideoLista.setDataPublicacao(LocalDate.parse("2023-10-01"));

        var segundoVideoLista = gerarVideo();
        segundoVideoLista.setId(UUID.fromString("0fe964ec-4eab-4572-b556-8a7bceb61673"));
        segundoVideoLista.setDataPublicacao(LocalDate.parse("2023-11-01"));

        var terceiroVideoLista = gerarVideo();
        terceiroVideoLista.setId(UUID.fromString("6b9364e8-8131-44cc-adfe-b2a1c6775602"));
        terceiroVideoLista.setDataPublicacao(LocalDate.parse("2023-12-01"));

        var listaOrdenadaVideos = Arrays.asList(primeiroVideoLista, segundoVideoLista, terceiroVideoLista);

        when(videoRepository.findAllByOrderByDataPublicacaoAsc()).thenReturn(listaOrdenadaVideos);

        // Act
        var listaVideos = videoService.listarTodosVideosOrdenadosPorDataPublicacao(Pageable.unpaged());

        // Assert
        assertThat(listaVideos.getSize()).isEqualTo(listaOrdenadaVideos.size());
        assertThat(listaVideos.toList().get(0).getId())
                .isEqualTo(listaOrdenadaVideos.get(0).getId());
        assertThat(listaVideos.toList().get(0).getDataPublicacao())
                .isEqualTo(listaOrdenadaVideos.get(0).getDataPublicacao());

        assertThat(listaVideos).isInstanceOf(PageImpl.class);
        verify(videoRepository, times(1)).findAllByOrderByDataPublicacaoAsc();
    }

    @Test
    void devePermitirListagemVideoComFiltroPorTitulo(){
        // Arrange
        var video = gerarVideo();
        video.setId(UUID.fromString("2d6cd3fe-a01e-41c0-8b02-6a502f1cad96"));

        var listaVideo = Arrays.asList(video);

        when(videoRepository.findByTitulo(any(String.class))).thenReturn(listaVideo);

        // Act
        var videoPesquisado = videoService
                .buscarVideosPorTitulo("Aquaman 2: O Reino Perdido", Pageable.unpaged());

        // Assert
        assertThat(videoPesquisado).isInstanceOf(Page.class);
        assertThat(videoPesquisado.toList().get(0).getTitulo()).isEqualTo(video.getTitulo());
        assertThat(videoPesquisado.toList().get(0).getId()).isEqualTo(video.getId());

        verify(videoRepository, times(1)).findByTitulo(any(String.class));
    }

    @Test
    void devePermitirListagemVideoComFiltroPorDataDaPublicacao() {
        // Arrange
        var primeiroVideo = gerarVideo();
        primeiroVideo.setDataPublicacao(LocalDate.parse("2023-09-02"));
        primeiroVideo.setId(UUID.fromString("e731bda6-bab8-44aa-baaf-e4ae842ec0f9"));

        var segundoVideo = gerarVideo();
        segundoVideo.setDataPublicacao(LocalDate.parse("2023-09-02"));
        segundoVideo.setId(UUID.fromString("9580a540-f512-45c4-8b70-4f86b3035987"));

        var listaVideos = Arrays.asList(primeiroVideo, segundoVideo);

        when(videoRepository.findByDataPublicacao(any(LocalDate.class))).thenReturn(listaVideos);

        // Act
        var videosPesquisados = videoService.buscarVideosPorDataDaPublicacao(LocalDate
                .parse("2023-09-02"), Pageable.unpaged());

        // Assert
        assertThat(videosPesquisados.toList().get(0).getId()).isEqualTo(listaVideos.get(0).getId());
        assertThat(videosPesquisados.toList().get(0).getDataPublicacao())
                .isEqualTo(listaVideos.get(0).getDataPublicacao());

        assertThat(videosPesquisados.toList().get(1).getId()).isEqualTo(listaVideos.get(1).getId());
        assertThat(videosPesquisados.toList().get(1).getDataPublicacao())
                .isEqualTo(listaVideos.get(1).getDataPublicacao());

        verify(videoRepository, times(1)).findByDataPublicacao(any(LocalDate.class));
    }

    @Test
    void devePermitirListagemVideoComFiltroPorCategoria()
    {
        // Arrange
        var primeiroVideo = gerarVideo();
        primeiroVideo.setId(UUID.fromString("1d210cf0-f414-40a0-94a9-fa434d530e41"));

        var segundoVideo = gerarVideo();
        segundoVideo.setId(UUID.fromString("5c5c17e9-923c-4c95-a0a7-0d2dae73c913"));

        var listaVideos = Arrays.asList(primeiroVideo, segundoVideo);

        when(videoRepository.findByCategorias(any(Categoria.class))).thenReturn(listaVideos);

        // Act
        var videosPesquisados = videoService.buscarVideosPorCategoria(
                gerarCategorias().get(0), Pageable.unpaged());

        // Assert
        assertThat(videosPesquisados.toList().get(0).getId()).isEqualTo(listaVideos.get(0).getId());
        assertThat(videosPesquisados.toList().get(0).getCategorias().get(0))
                .isEqualTo(listaVideos.get(0).getCategorias().get(0));

        assertThat(videosPesquisados.toList().get(1).getId()).isEqualTo(listaVideos.get(1).getId());
        assertThat(videosPesquisados.toList().get(1).getCategorias().get(0))
                .isEqualTo(listaVideos.get(1).getCategorias().get(0));

        verify(videoRepository, times(1)).findByCategorias(any(Categoria.class));
    }

    @Test
    void deveGerarExcecao_QuandoExcluirVideo_IdInexistente() {
        // Arrange
        var video = gerarVideo();
        video.setId(UUID.fromString("8d759d21-513f-4790-8d54-21977e5d076e"));

        when(videoRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        // Act
        // Assert
        assertThatThrownBy(() -> videoService.excluirVideo(video))
                .isInstanceOf(VideoInvalidoException.class)
                .hasMessage("Video nao localizado");
        verify(videoRepository, times(1)).findById(any(UUID.class));

    }

    @Test
    void devePermitirExcluirVideo() {
        // Arrange
        var video = gerarVideo();
        video.setId(UUID.fromString("c45a51f0-7e35-4e47-8822-3476cf574ad1"));

        when(videoRepository.findById(any(UUID.class))).thenReturn(Optional.of(video));
        doNothing().when(videoRepository).deleteById(any(UUID.class));

        // Act
        var resultado = videoService.excluirVideo(video);

        // Assert
        assertThat(resultado).isTrue();
        verify(videoRepository, times(1)).deleteById(any(UUID.class));
    }

    private Video gerarVideo() {
        return Video.builder()
                .titulo("Aquaman 2: O Reino Perdido")
                .descricao("Um antigo poder é libertado e o herói Aquaman precisa fazer um perigoso acordo com um aliado improvável para proteger Atlântida e o mundo de uma devastação irreversível.")
                .url("https://www.youtube.com/watch?v=g_i8yVKpXuk")
                .dataPublicacao(LocalDate.parse("2023-12-20"))
                .categorias(gerarCategorias())
                .build();
    }

    private List<Categoria> gerarCategorias(){
        return Arrays.asList(
                Categoria.builder()
                .id(UUID.fromString("947e5e8b-9528-4628-ac6d-eb5c1c4c88d8"))
                .nome("Fantasia")
                .build(),

                Categoria.builder()
                        .id(UUID.fromString("2c7b6cae-034b-4da4-adb2-28f8899e900d"))
                        .nome("Acao")
                        .build());
    }
}
