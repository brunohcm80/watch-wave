package br.com.watchwave.controller;

import br.com.watchwave.model.Video;
import br.com.watchwave.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.UUID;

@Controller
@RestController("/videos")
public class VideoController {

    @Autowired
    private VideoService videoService;

    @PostMapping("/cadastrar")
    public Mono<ResponseEntity<?>> cadastrar (Video video) {
        return videoService.cadastrarVideo(video)
                .map(response -> ResponseEntity.status(HttpStatus.CREATED).body(response));
    }

    @PutMapping("/atualizar/{id}")
    public Mono<ResponseEntity<?>> atualizar (@PathVariable UUID id,
                                                       @RequestBody Video video) {
        return videoService.atualizarVideo(id, video)
                .map(response -> ResponseEntity.ok(response));
    }

    @GetMapping("/listar")
    public Mono<ResponseEntity<?>> listarTodos (@PageableDefault Pageable paginacao) {
        return videoService.listarTodosVideosOrdenadosPorDataPublicacao(paginacao)
                .map(response -> ResponseEntity.ok(response));
    }

    @GetMapping("/listar/titulo/{titulo}")
    public Mono<ResponseEntity<?>> listarPorTitulo (@PathVariable String titulo,
                                                    @PageableDefault Pageable paginacao) {
        return videoService.buscarVideosPorTitulo(titulo, paginacao)
                .map(response -> ResponseEntity.ok(response));
    }

    @GetMapping("/listar/data/{data}")
    public Mono<ResponseEntity<?>> listarPorDataPublicacao (@PathVariable LocalDate dataPublicacao,
                                                            @PageableDefault Pageable paginacao) {
        return videoService.buscarVideosPorDataDaPublicacao(dataPublicacao, paginacao)
                .map(response -> ResponseEntity.ok(response));
    }

    @GetMapping("/listar/categoria/{categoria}")
    public Mono<ResponseEntity<?>> listarPorCategoria (@PathVariable UUID idCategoria,
                                                       @PageableDefault Pageable paginacao) {
        return videoService.buscarVideosPorCategoria(idCategoria, paginacao)
                .map(response -> ResponseEntity.ok(response));
    }

    @DeleteMapping("/excluir/{id}")
    public Mono<ResponseEntity<?>> excluir (@PathVariable UUID id) {
        return videoService.excluirVideo(id)
                .map(response -> ResponseEntity.ok(response));
    }

}
