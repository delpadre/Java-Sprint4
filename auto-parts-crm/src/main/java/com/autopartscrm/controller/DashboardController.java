package com.autopartscrm.controller;

import com.autopartscrm.service.ClienteService;
import com.autopartscrm.service.CotacaoService;
import com.autopartscrm.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CotacaoService cotacaoService;

    @Autowired
    private PedidoService pedidoService;

    @GetMapping("/dashboard")
    public String dashboard(Model model, @AuthenticationPrincipal UserDetails userDetails) {
        model.addAttribute("username", userDetails.getUsername());
        model.addAttribute("totalClientes", clienteService.contarClientes());
        model.addAttribute("totalCotacoes", cotacaoService.contarCotacoes());
        model.addAttribute("totalPedidos", pedidoService.contarPedidos());
        return "dashboard";
    }
}