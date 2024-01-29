package br.com.watchwave.config;

import br.com.watchwave.model.Categoria;
import br.com.watchwave.model.Video;
import br.com.watchwave.repository.CategoriaRepository;
import br.com.watchwave.repository.VideoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.UUID;

@Component
public class DummyData implements CommandLineRunner {

    private final VideoRepository videoRepository;

    private final CategoriaRepository categoriaRepository;

    DummyData(VideoRepository videoRepository, CategoriaRepository categoriaRepository) {
        this.videoRepository = videoRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        var categorias = gerarCategorias();

        categoriaRepository.deleteAll()
                .thenMany(categorias.map(categoria -> Categoria.builder()
                        .id(categoria.getId())
                        .nome(categoria.getNome())
                        .build()))
                .flatMap(categoriaRepository::save);

        var videos = gerarVideos();

        videoRepository.deleteAll()
                .thenMany(videos.map(video -> Video.builder()
                        .id(video.getId())
                        .titulo(video.getTitulo())
                        .descricao(video.getDescricao())
                        .url(video.getUrl())
                        .dataPublicacao(video.getDataPublicacao())
                        .categorias(video.getCategorias())
                        .build()))
                .flatMap(videoRepository::save);
    }

    private Flux<Categoria> gerarCategorias() {
        var categoriaAventura = Categoria.builder()
                .id(UUID.fromString("72ac1bf5-acc3-4475-bb01-589738d100dd"))
                .nome("Aventura").build();

        var categoriaComedia = Categoria.builder()
                .id(UUID.fromString("2cad1d9c-53c6-4860-b6c9-16bdb21ea933"))
                .nome("Comedia").build();

        var categoriaTerror = Categoria.builder()
                .id(UUID.fromString("85b34370-80b9-4b49-99be-204510a20718"))
                .nome("Terror").build();

        var categoriaAcao = Categoria.builder()
                .id(UUID.fromString("2c7b6cae-034b-4da4-adb2-28f8899e900d"))
                .nome("Acao").build();

        var categoriaSuspense = Categoria.builder()
                .id(UUID.fromString("d7cbf058-0203-42b1-8542-b432e1c3a5d7"))
                .nome("Suspense").build();

        var categoriaFiccaoCientifica = Categoria.builder()
                .id(UUID.fromString("ea15668e-006a-4c0b-b25d-df48c5652e21"))
                .nome("Ficcao Cientifica").build();

        var categoriaFantasia = Categoria.builder()
                .id(UUID.fromString("947e5e8b-9528-4628-ac6d-eb5c1c4c88d8"))
                .nome("Fantasia").build();

        return Flux.just(categoriaAventura, categoriaComedia, categoriaTerror,
                categoriaAcao, categoriaSuspense, categoriaFiccaoCientifica, categoriaFantasia);

    }

    private Flux<Video> gerarVideos() {
        var videoGuardioesGalaxiaVol3 = Video.builder()
                .id(UUID.fromString("76b6c4df-5439-4935-a5c5-45561008c780"))
                .titulo("Guardioes da Galaxia Volume 3")
                .descricao("Peter Quill deve reunir sua equipe para defender o universo e proteger um dos seus. Se a missão não for totalmente bem-sucedida, isso pode levar ao fim dos Guardiões.")
                .url("https://www.youtube.com/watch?v=d1yNc9skssk")
                .dataPublicacao(LocalDate.parse("2023-05-04"))
                .categorias(Arrays.asList(categoriaRepository
                                .findById(UUID.fromString("2c7b6cae-034b-4da4-adb2-28f8899e900d"))
                                .block(),
                        categoriaRepository
                                .findById(UUID.fromString("72ac1bf5-acc3-4475-bb01-589738d100dd"))
                                .block()))
                .build();

        var videoDunaParte2 = Video.builder()
                .id(UUID.fromString("11d171cd-8bdf-4e5c-bbe1-2d0e112cc943"))
                .titulo("Duna: Parte 2")
                .descricao("Paul Atreides se une a Chani e aos Fremen enquanto busca vingança contra os conspiradores que destruíram sua família. Enfrentando uma escolha entre o amor de sua vida e o destino do universo, ele deve evitar um futuro terrível que só ele pode prever.")
                .url("https://www.youtube.com/watch?v=ncwsW3qxQlo")
                .dataPublicacao(LocalDate.parse("2024-03-14"))
                .categorias(Arrays.asList(categoriaRepository
                                .findById(UUID.fromString("ea15668e-006a-4c0b-b25d-df48c5652e21"))
                                .block(),
                        categoriaRepository
                                .findById(UUID.fromString("72ac1bf5-acc3-4475-bb01-589738d100dd"))
                                .block()))
                .build();

        var videoHomemAranhaAranhaverso = Video.builder()
                .id(UUID.fromString("2a943085-d85f-4d22-afec-f35408e5e49a"))
                .titulo("Homem-Aranha: Através do AranhaVerso")
                .descricao("Depois de se reunir com Gwen Stacy, Homem-Aranha é jogado no multiverso, onde ele encontra uma equipe encarregada de proteger sua própria existência.")
                .url("https://www.youtube.com/watch?v=LZBlXkDvhh4")
                .dataPublicacao(LocalDate.parse("2023-06-01"))
                .categorias(Arrays.asList(categoriaRepository
                                .findById(UUID.fromString("2c7b6cae-034b-4da4-adb2-28f8899e900d"))
                                .block(),
                        categoriaRepository
                                .findById(UUID.fromString("2cad1d9c-53c6-4860-b6c9-16bdb21ea933"))
                                .block()))
                .build();

        return Flux.just(videoGuardioesGalaxiaVol3, videoDunaParte2, videoHomemAranhaAranhaverso);
    }
}
