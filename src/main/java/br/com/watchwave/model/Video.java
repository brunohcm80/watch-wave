package br.com.watchwave.model;

import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Video {
    @Id
    private UUID id;
    @NotBlank
    private String titulo;
    @NotBlank
    private String descricao;
    @NotBlank
    private String url;
    @NotNull
    private LocalDate dataPublicacao;
    @NotNull
    private List<Categoria> categorias;
}
