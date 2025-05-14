package com.autopartscrm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autopartscrm.model.Pedido;
import com.autopartscrm.model.StatusPedido;
import com.autopartscrm.repository.PedidoRepository;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido criar(Pedido pedido) {
        pedido.setStatus(StatusPedido.PENDENTE.asString());
        return pedidoRepository.save(pedido);
    }

    public Pedido atualizarStatus(Long id, StatusPedido status) {
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        pedido.setStatus(status.asString());
        if (status == StatusPedido.ENTREGUE) {
           // pedido.setDataEntrega(LocalDateTime.now());
        }
        return pedidoRepository.save(pedido);
    }

    public long contarPedidos() {
        return pedidoRepository.count();
    }
}