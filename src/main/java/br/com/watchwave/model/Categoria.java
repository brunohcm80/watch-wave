package br.com.watchwave.model;

import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Categoria {
    private UUID id;
    private String nome;
}
