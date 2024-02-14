package com.eduardondarocha.mindsimapp.email.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.file.Path;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class MensagemEmail {
    private String assunto;
    private String texto;
    private String remetente;
    private List<String> destinatarios;
    private Path caminhoArquivo;
    private String nomeArquivo;
}
