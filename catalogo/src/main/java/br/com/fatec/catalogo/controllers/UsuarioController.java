package br.com.fatec.catalogo.controllers;

import br.com.fatec.catalogo.models.UsuarioModel;
import br.com.fatec.catalogo.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("/novo")
    public String form(Model model) {
        model.addAttribute("usuarioModel", new UsuarioModel());
        return "cadastro-usuario";
    }

    @PostMapping("/salvar")
    public String salvar(@Valid UsuarioModel usuario, BindingResult result) {
        if (result.hasErrors()) {
            return "cadastro-usuario";
        }
        usuarioService.salvar(usuario);
        return "redirect:/produtos";
    }
}
