package br.com.listapresentes.api.dto;

import br.com.listapresentes.api.entity.ConfiguracaoLista;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConfiguracaoDTO {

    private Long id;

    @NotBlank(message = "Nome da primeira pessoa é obrigatório")
    private String nomePessoa1;

    @NotBlank(message = "Nome da segunda pessoa é obrigatório")
    private String nomePessoa2;

    private String tituloPagina;

    private String mensagemBoasVindas;

    private String fotoCasalUrl;

    private String fotoBannerUrl;

    private LocalDate dataEvento;

    private String localEvento;

    private String corPrimaria;

    private String corSecundaria;

    private String mensagemAgradecimento;

    // Configuracoes PIX
    private String pixChave;
    private String pixTipo;
    private String pixNomeTitular;
    private String pixBanco;
    private String pixQrcodeUrl;

    public ConfiguracaoLista toEntity() {
        return ConfiguracaoLista.builder()
                .id(this.id)
                .nomePessoa1(this.nomePessoa1)
                .nomePessoa2(this.nomePessoa2)
                .tituloPagina(this.tituloPagina)
                .mensagemBoasVindas(this.mensagemBoasVindas)
                .fotoCasalUrl(this.fotoCasalUrl)
                .fotoBannerUrl(this.fotoBannerUrl)
                .dataEvento(this.dataEvento)
                .localEvento(this.localEvento)
                .corPrimaria(this.corPrimaria != null ? this.corPrimaria : "#667eea")
                .corSecundaria(this.corSecundaria != null ? this.corSecundaria : "#764ba2")
                .mensagemAgradecimento(this.mensagemAgradecimento)
                .pixChave(this.pixChave)
                .pixTipo(this.pixTipo)
                .pixNomeTitular(this.pixNomeTitular)
                .pixBanco(this.pixBanco)
                .pixQrcodeUrl(this.pixQrcodeUrl)
                .build();
    }

    public static ConfiguracaoDTO fromEntity(ConfiguracaoLista config) {
        return ConfiguracaoDTO.builder()
                .id(config.getId())
                .nomePessoa1(config.getNomePessoa1())
                .nomePessoa2(config.getNomePessoa2())
                .tituloPagina(config.getTituloPagina())
                .mensagemBoasVindas(config.getMensagemBoasVindas())
                .fotoCasalUrl(config.getFotoCasalUrl())
                .fotoBannerUrl(config.getFotoBannerUrl())
                .dataEvento(config.getDataEvento())
                .localEvento(config.getLocalEvento())
                .corPrimaria(config.getCorPrimaria())
                .corSecundaria(config.getCorSecundaria())
                .mensagemAgradecimento(config.getMensagemAgradecimento())
                .pixChave(config.getPixChave())
                .pixTipo(config.getPixTipo())
                .pixNomeTitular(config.getPixNomeTitular())
                .pixBanco(config.getPixBanco())
                .pixQrcodeUrl(config.getPixQrcodeUrl())
                .build();
    }

    public String getNomesCasal() {
        return nomePessoa1 + " & " + nomePessoa2;
    }
}
