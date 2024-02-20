package com.eduardondarocha.mindsimapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MensagemEmail {
    private String assunto;
    private String texto;
    private String remetente;
    private List<String> destinatarios;
    private String anexo;
}
