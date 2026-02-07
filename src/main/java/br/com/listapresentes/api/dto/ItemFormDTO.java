package br.com.listapresentes.api.dto;

import br.com.listapresentes.api.entity.Item;
import br.com.listapresentes.api.entity.StatusItem;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemFormDTO {

    private Long id;

    @NotBlank(message = "Nome é obrigatório")
    private String nome;

    private String descricao;

    @NotNull(message = "Valor é obrigatório")
    @DecimalMin(value = "0.01", message = "Valor deve ser maior que zero")
    private BigDecimal valor;

    private String imagemUrl;

    public Item toEntity() {
        return Item.builder()
                .id(this.id)
                .nome(this.nome)
                .descricao(this.descricao)
                .valor(this.valor)
                .imagemUrl(this.imagemUrl)
                .status(StatusItem.DISPONIVEL)
                .build();
    }

    public static ItemFormDTO fromEntity(Item item) {
        return ItemFormDTO.builder()
                .id(item.getId())
                .nome(item.getNome())
                .descricao(item.getDescricao())
                .valor(item.getValor())
                .imagemUrl(item.getImagemUrl())
                .build();
    }
}
