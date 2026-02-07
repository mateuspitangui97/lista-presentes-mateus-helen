package br.com.listapresentes.api.dto;

import br.com.listapresentes.api.entity.Item;
import br.com.listapresentes.api.entity.StatusItem;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class ItemDTO {
    private Long id;
    private String nome;
    private String descricao;
    private BigDecimal valor;
    private String imagemUrl;
    private StatusItem status;

    public static ItemDTO fromEntity(Item item) {
        return ItemDTO.builder()
                .id(item.getId())
                .nome(item.getNome())
                .descricao(item.getDescricao())
                .valor(item.getValor())
                .imagemUrl(item.getImagemUrl())
                .status(item.getStatus())
                .build();
    }
}
