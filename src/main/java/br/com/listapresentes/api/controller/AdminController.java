package br.com.listapresentes.api.controller;

import br.com.listapresentes.api.dto.ConfiguracaoDTO;
import br.com.listapresentes.api.dto.ItemDTO;
import br.com.listapresentes.api.dto.ItemFormDTO;
import br.com.listapresentes.api.entity.Item;
import br.com.listapresentes.api.service.ConfiguracaoService;
import br.com.listapresentes.api.service.ItemService;
import br.com.listapresentes.api.service.ReservaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final ItemService itemService;
    private final ReservaService reservaService;
    private final ConfiguracaoService configuracaoService;

    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("itens", itemService.listarTodos());
        model.addAttribute("config", configuracaoService.buscarConfiguracao());
        return "admin/dashboard";
    }

    @GetMapping("/item/novo")
    public String novoItem(Model model) {
        model.addAttribute("itemForm", new ItemFormDTO());
        model.addAttribute("titulo", "Novo Item");
        return "admin/item-form";
    }

    @GetMapping("/item/editar/{id}")
    public String editarItem(@PathVariable Long id, Model model) {
        try {
            ItemDTO item = itemService.buscarPorId(id);
            model.addAttribute("itemForm", ItemFormDTO.fromEntity(
                    Item.builder()
                            .id(item.getId())
                            .nome(item.getNome())
                            .descricao(item.getDescricao())
                            .valor(item.getValor())
                            .imagemUrl(item.getImagemUrl())
                            .build()
            ));
            model.addAttribute("titulo", "Editar Item");
            return "admin/item-form";
        } catch (IllegalArgumentException e) {
            return "redirect:/admin";
        }
    }

    @PostMapping("/item/salvar")
    public String salvarItem(@Valid @ModelAttribute("itemForm") ItemFormDTO form,
                             BindingResult result,
                             RedirectAttributes redirectAttributes,
                             Model model) {
        if (result.hasErrors()) {
            model.addAttribute("titulo", form.getId() == null ? "Novo Item" : "Editar Item");
            return "admin/item-form";
        }

        itemService.salvar(form);
        redirectAttributes.addFlashAttribute("sucesso", "Item salvo com sucesso!");
        return "redirect:/admin";
    }

    @PostMapping("/item/excluir/{id}")
    public String excluirItem(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            itemService.excluir(id);
            redirectAttributes.addFlashAttribute("sucesso", "Item excluído com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao excluir item. Verifique se não há reservas.");
        }
        return "redirect:/admin";
    }

    @GetMapping("/reservas")
    public String reservas(Model model) {
        model.addAttribute("reservas", reservaService.listarTodas());
        model.addAttribute("config", configuracaoService.buscarConfiguracao());
        return "admin/reservas";
    }

    @PostMapping("/reserva/confirmar/{id}")
    public String confirmarPagamento(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            reservaService.confirmarPagamento(id);
            redirectAttributes.addFlashAttribute("sucesso", "Pagamento confirmado! Presente marcado como reservado.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao confirmar pagamento.");
        }
        return "redirect:/admin/reservas";
    }

    @PostMapping("/reserva/cancelar/{id}")
    public String cancelarReserva(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            reservaService.cancelarReserva(id);
            redirectAttributes.addFlashAttribute("sucesso", "Reserva cancelada. Item disponível novamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("erro", "Erro ao cancelar reserva.");
        }
        return "redirect:/admin/reservas";
    }

    @GetMapping("/configuracoes")
    public String configuracoes(Model model) {
        model.addAttribute("configForm", configuracaoService.buscarConfiguracao());
        return "admin/configuracoes";
    }

    @PostMapping("/configuracoes/salvar")
    public String salvarConfiguracoes(@Valid @ModelAttribute("configForm") ConfiguracaoDTO form,
                                       BindingResult result,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {
        if (result.hasErrors()) {
            return "admin/configuracoes";
        }

        configuracaoService.salvar(form);
        redirectAttributes.addFlashAttribute("sucesso", "Configurações salvas com sucesso!");
        return "redirect:/admin/configuracoes";
    }
}
