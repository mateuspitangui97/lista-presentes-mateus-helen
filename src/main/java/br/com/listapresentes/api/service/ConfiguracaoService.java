package br.com.listapresentes.api.service;

import br.com.listapresentes.api.dto.ConfiguracaoDTO;
import br.com.listapresentes.api.entity.ConfiguracaoLista;
import br.com.listapresentes.api.repository.ConfiguracaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ConfiguracaoService {

    private final ConfiguracaoRepository configuracaoRepository;

    public ConfiguracaoDTO buscarConfiguracao() {
        return configuracaoRepository.findFirstByAtivoTrue()
                .map(ConfiguracaoDTO::fromEntity)
                .orElse(criarConfiguracaoPadrao());
    }

    private ConfiguracaoDTO criarConfiguracaoPadrao() {
        return ConfiguracaoDTO.builder()
                .nomePessoa1("Nome 1")
                .nomePessoa2("Nome 2")
                .tituloPagina("Lista de Presentes")
                .mensagemBoasVindas("Bem-vindos a nossa lista de presentes!")
                .corPrimaria("#667eea")
                .corSecundaria("#764ba2")
                .mensagemAgradecimento("Muito obrigado pelo presente!")
                .build();
    }

    @Transactional
    public ConfiguracaoDTO salvar(ConfiguracaoDTO dto) {
        ConfiguracaoLista config;

        if (dto.getId() != null) {
            config = configuracaoRepository.findById(dto.getId())
                    .orElse(new ConfiguracaoLista());
        } else {
            config = configuracaoRepository.findFirstByAtivoTrue()
                    .orElse(new ConfiguracaoLista());
        }

        config.setNomePessoa1(dto.getNomePessoa1());
        config.setNomePessoa2(dto.getNomePessoa2());
        config.setTituloPagina(dto.getTituloPagina());
        config.setMensagemBoasVindas(dto.getMensagemBoasVindas());
        config.setFotoCasalUrl(dto.getFotoCasalUrl());
        config.setFotoBannerUrl(dto.getFotoBannerUrl());
        config.setDataEvento(dto.getDataEvento());
        config.setLocalEvento(dto.getLocalEvento());
        config.setCorPrimaria(dto.getCorPrimaria() != null ? dto.getCorPrimaria() : "#667eea");
        config.setCorSecundaria(dto.getCorSecundaria() != null ? dto.getCorSecundaria() : "#764ba2");
        config.setMensagemAgradecimento(dto.getMensagemAgradecimento());
        config.setPixChave(dto.getPixChave());
        config.setPixTipo(dto.getPixTipo());
        config.setPixNomeTitular(dto.getPixNomeTitular());
        config.setPixBanco(dto.getPixBanco());
        config.setPixQrcodeUrl(dto.getPixQrcodeUrl());
        config.setAtivo(true);

        return ConfiguracaoDTO.fromEntity(configuracaoRepository.save(config));
    }
}
