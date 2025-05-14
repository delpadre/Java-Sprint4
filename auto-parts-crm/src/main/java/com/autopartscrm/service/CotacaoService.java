package com.autopartscrm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autopartscrm.model.Cotacao;
import com.autopartscrm.model.StatusCotacao;
import com.autopartscrm.repository.CotacaoRepository;

@Service
public class CotacaoService {

    @Autowired
    private CotacaoRepository cotacaoRepository;

    public Cotacao salvar(Cotacao cotacao) {
        cotacao.setStatus(StatusCotacao.ABERTA.asString());
        return cotacaoRepository.save(cotacao);
    }

    public Cotacao responder(Long id, Double precoProposto, String resposta) {
        Cotacao cotacao = this.buscarCotacaoPorId(id);

        cotacao.setValor(precoProposto);
        cotacao.setDescricao(resposta);
        cotacao.setStatus(StatusCotacao.CONCLUIDA.asString());
        return cotacaoRepository.save(cotacao);
    }

    public Cotacao aprovar(Long id) {
        Cotacao cotacao = this.buscarCotacaoPorId(id);

        cotacao.setStatus(StatusCotacao.APROVADA.asString());
        return cotacaoRepository.save(cotacao);
    }
    
    private Cotacao buscarCotacaoPorId(Long id) {
    	return cotacaoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cotação não encontrada"));
    }

    public List<Cotacao> listarTodos() {
        return cotacaoRepository.findAll();
    }

    public long contarCotacoes() {
        return cotacaoRepository.count();
    }
}