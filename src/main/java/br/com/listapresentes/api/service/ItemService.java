package br.com.listapresentes.api.service;

import br.com.listapresentes.api.dto.ItemDTO;
import br.com.listapresentes.api.dto.ItemFormDTO;
import br.com.listapresentes.api.entity.Item;
import br.com.listapresentes.api.entity.StatusItem;
import br.com.listapresentes.api.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    public List<ItemDTO> listarDisponiveis() {
        return itemRepository.findByStatusOrderByNomeAsc(StatusItem.DISPONIVEL)
                .stream()
                .map(ItemDTO::fromEntity)
                .toList();
    }

    public List<ItemDTO> listarTodos() {
        return itemRepository.findAllByOrderByDataCriacaoDesc()
                .stream()
                .map(ItemDTO::fromEntity)
                .toList();
    }

    public ItemDTO buscarPorId(Long id) {
        return itemRepository.findById(id)
                .map(ItemDTO::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado"));
    }

    public Item buscarEntidadePorId(Long id) {
        return itemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Item não encontrado"));
    }

    @Transactional
    public ItemDTO salvar(ItemFormDTO form) {
        Item item;
        if (form.getId() != null) {
            item = buscarEntidadePorId(form.getId());
            item.setNome(form.getNome());
            item.setDescricao(form.getDescricao());
            item.setValor(form.getValor());
            item.setImagemUrl(form.getImagemUrl());
        } else {
            item = form.toEntity();
        }
        return ItemDTO.fromEntity(itemRepository.save(item));
    }

    @Transactional
    public void excluir(Long id) {
        itemRepository.deleteById(id);
    }

    @Transactional
    public void marcarComoAguardando(Long id) {
        Item item = buscarEntidadePorId(id);
        item.setStatus(StatusItem.AGUARDANDO_CONFIRMACAO);
        itemRepository.save(item);
    }

    @Transactional
    public void marcarComoReservado(Long id) {
        Item item = buscarEntidadePorId(id);
        item.setStatus(StatusItem.RESERVADO);
        itemRepository.save(item);
    }

    @Transactional
    public void marcarComoDisponivel(Long id) {
        Item item = buscarEntidadePorId(id);
        item.setStatus(StatusItem.DISPONIVEL);
        itemRepository.save(item);
    }
}
