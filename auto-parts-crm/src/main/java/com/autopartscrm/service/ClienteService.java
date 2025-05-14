package com.autopartscrm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autopartscrm.model.Cliente;
import com.autopartscrm.repository.ClienteRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    public Cliente salvar(Cliente cliente) {
        return clienteRepository.save(cliente);
    }
    
    public Cliente obterClientePorId(Long id) {
        return this.clienteRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(String.format("Cliente com id %s não encontrado.", id)));
    }


    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    public long contarClientes() {
        return clienteRepository.count();
    }
    
    public void excluir(Cliente cliente) {
    	this.clienteRepository.delete(cliente);
    }
}