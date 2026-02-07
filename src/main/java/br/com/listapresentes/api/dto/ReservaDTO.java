package br.com.listapresentes.api.dto;

import br.com.listapresentes.api.entity.Reserva;
import br.com.listapresentes.api.entity.StatusItem;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class ReservaDTO {
    private Long id;
    private Long itemId;
    private String nomeItem;
    private BigDecimal valorItem;
    private StatusItem statusItem;
    private String nomeConvidado;
    private String emailConvidado;
    private String mensagem;
    private LocalDateTime dataReserva;

    public static ReservaDTO fromEntity(Reserva reserva) {
        return ReservaDTO.builder()
                .id(reserva.getId())
                .itemId(reserva.getItem().getId())
                .nomeItem(reserva.getItem().getNome())
                .valorItem(reserva.getItem().getValor())
                .statusItem(reserva.getItem().getStatus())
                .nomeConvidado(reserva.getNomeConvidado())
                .emailConvidado(reserva.getEmailConvidado())
                .mensagem(reserva.getMensagem())
                .dataReserva(reserva.getDataReserva())
                .build();
    }
}
