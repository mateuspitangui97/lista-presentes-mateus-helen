package br.com.listapresentes.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaFormDTO {

    @NotNull(message = "Item é obrigatório")
    private Long itemId;

    @NotBlank(message = "Nome é obrigatório")
    private String nomeConvidado;

    private String emailConvidado;

    private String mensagem;
}
