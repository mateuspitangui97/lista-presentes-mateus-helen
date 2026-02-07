package br.com.listapresentes.api.service;

import br.com.listapresentes.api.dto.ReservaDTO;
import br.com.listapresentes.api.dto.ReservaFormDTO;
import br.com.listapresentes.api.entity.Item;
import br.com.listapresentes.api.entity.Reserva;
import br.com.listapresentes.api.entity.StatusItem;
import br.com.listapresentes.api.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ItemService itemService;

    public List<ReservaDTO> listarTodas() {
        return reservaRepository.findAllByOrderByDataReservaDesc()
                .stream()
                .map(ReservaDTO::fromEntity)
                .toList();
    }

    public ReservaDTO buscarPorItemId(Long itemId) {
        return reservaRepository.findByItemId(itemId)
                .map(ReservaDTO::fromEntity)
                .orElse(null);
    }

    @Transactional
    public ReservaDTO reservar(ReservaFormDTO form) {
        Item item = itemService.buscarEntidadePorId(form.getItemId());

        if (item.getStatus() != StatusItem.DISPONIVEL) {
            throw new IllegalStateException("Este item não está disponível");
        }

        Reserva reserva = Reserva.builder()
                .item(item)
                .nomeConvidado(form.getNomeConvidado())
                .emailConvidado(form.getEmailConvidado())
                .mensagem(form.getMensagem())
                .build();

        reservaRepository.save(reserva);
        itemService.marcarComoAguardando(item.getId());

        return ReservaDTO.fromEntity(reserva);
    }

    @Transactional
    public void confirmarPagamento(Long reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada"));

        itemService.marcarComoReservado(reserva.getItem().getId());
    }

    @Transactional
    public void cancelarReserva(Long reservaId) {
        Reserva reserva = reservaRepository.findById(reservaId)
                .orElseThrow(() -> new IllegalArgumentException("Reserva não encontrada"));

        itemService.marcarComoDisponivel(reserva.getItem().getId());
        reservaRepository.delete(reserva);
    }
}
