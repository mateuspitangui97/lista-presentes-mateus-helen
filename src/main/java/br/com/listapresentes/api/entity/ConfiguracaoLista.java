package br.com.listapresentes.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "configuracao_lista")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracaoLista {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_pessoa1", nullable = false)
    private String nomePessoa1;

    @Column(name = "nome_pessoa2", nullable = false)
    private String nomePessoa2;

    @Column(name = "titulo_pagina")
    private String tituloPagina;

    @Column(name = "mensagem_boas_vindas", length = 2000)
    private String mensagemBoasVindas;

    @Column(name = "foto_casal_url")
    private String fotoCasalUrl;

    @Column(name = "foto_banner_url")
    private String fotoBannerUrl;

    @Column(name = "data_evento")
    private LocalDate dataEvento;

    @Column(name = "local_evento")
    private String localEvento;

    @Column(name = "cor_primaria")
    @Builder.Default
    private String corPrimaria = "#667eea";

    @Column(name = "cor_secundaria")
    @Builder.Default
    private String corSecundaria = "#764ba2";

    @Column(name = "mensagem_agradecimento", length = 2000)
    private String mensagemAgradecimento;

    // Configuracoes PIX
    @Column(name = "pix_chave")
    private String pixChave;

    @Column(name = "pix_tipo")
    private String pixTipo; // CPF, CNPJ, EMAIL, TELEFONE, ALEATORIA

    @Column(name = "pix_nome_titular")
    private String pixNomeTitular;

    @Column(name = "pix_banco")
    private String pixBanco;

    @Column(name = "pix_qrcode_url")
    private String pixQrcodeUrl; // URL da imagem do QR Code

    @Column(name = "ativo")
    @Builder.Default
    private Boolean ativo = (Boolean) true;
}
