package br.com.fatec.catalogo.controllers;

import br.com.fatec.catalogo.repositories.AuditoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/auditoria")
public class AuditoriaController {

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    @GetMapping
    public String exibirPainel(Model model) {
        model.addAttribute("logs", auditoriaRepository.findAllByOrderByDataHoraDesc());
        return "painel-auditoria";
    }
}
