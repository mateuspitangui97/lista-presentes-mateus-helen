package br.com.listapresentes.api.controller;

import br.com.listapresentes.api.dto.ConfiguracaoDTO;
import br.com.listapresentes.api.dto.ItemDTO;
import br.com.listapresentes.api.dto.ReservaFormDTO;
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
@RequiredArgsConstructor
public class PublicController {

    private final ItemService itemService;
    private final ReservaService reservaService;
    private final ConfiguracaoService configuracaoService;

    private void addConfiguracao(Model model) {
        ConfiguracaoDTO config = configuracaoService.buscarConfiguracao();
        model.addAttribute("config", config);
    }

    @GetMapping("/")
    public String home() {
        return "redirect:/lista";
    }

    @GetMapping("/lista")
    public String lista(Model model) {
        addConfiguracao(model);
        model.addAttribute("itens", itemService.listarDisponiveis());
        return "public/lista";
    }

    @GetMapping("/item/{id}")
    public String detalheItem(@PathVariable Long id, Model model) {
        try {
            addConfiguracao(model);
            ItemDTO item = itemService.buscarPorId(id);
            model.addAttribute("item", item);
            return "public/item-detalhe";
        } catch (IllegalArgumentException e) {
            return "redirect:/lista";
        }
    }

    @GetMapping("/checkout/{id}")
    public String checkout(@PathVariable Long id, Model model) {
        try {
            addConfiguracao(model);
            ItemDTO item = itemService.buscarPorId(id);
            model.addAttribute("item", item);
            ReservaFormDTO reservaForm = new ReservaFormDTO();
            reservaForm.setItemId(id);
            model.addAttribute("reservaForm", reservaForm);
            return "public/checkout";
        } catch (IllegalArgumentException e) {
            return "redirect:/lista";
        }
    }

    @PostMapping("/reservar")
    public String reservar(@Valid @ModelAttribute("reservaForm") ReservaFormDTO form,
                           BindingResult result,
                           RedirectAttributes redirectAttributes,
                           Model model) {
        if (result.hasErrors()) {
            addConfiguracao(model);
            ItemDTO item = itemService.buscarPorId(form.getItemId());
            model.addAttribute("item", item);
            return "public/checkout";
        }

        try {
            reservaService.reservar(form);
            return "redirect:/confirmacao/" + form.getItemId();
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("erro", e.getMessage());
            return "redirect:/lista";
        }
    }

    @GetMapping("/confirmacao/{id}")
    public String confirmacao(@PathVariable Long id, Model model) {
        try {
            addConfiguracao(model);
            ItemDTO item = itemService.buscarPorId(id);
            model.addAttribute("item", item);
            return "public/confirmacao";
        } catch (IllegalArgumentException e) {
            return "redirect:/lista";
        }
    }
}
