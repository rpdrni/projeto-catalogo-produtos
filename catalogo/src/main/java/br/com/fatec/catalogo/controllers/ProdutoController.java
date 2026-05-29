package br.com.fatec.catalogo.controllers;

import br.com.fatec.catalogo.models.CategoriaModel;
import br.com.fatec.catalogo.models.ProdutoModel;
import br.com.fatec.catalogo.repositories.CategoriaRepository;
import br.com.fatec.catalogo.repositories.ProdutoRepository;
import br.com.fatec.catalogo.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @ModelAttribute("categorias")
    public List<CategoriaModel> carregarCategorias() {
        return categoriaRepository.findAll();
    }

    @GetMapping
    public String listarProdutos(@RequestParam(name = "search", required = false) String search, Model model) {
        model.addAttribute("produtos", service.listarTodos(search));
        model.addAttribute("search", search);
        return "lista-produtos";
    }


    @GetMapping("/novo")
    public String exibirFormulario(Model model){
        model.addAttribute("produto", new ProdutoModel());
        model.addAttribute("categorias", categoriaRepository.findAll());
        return "cadastro-produto";
    }

    @PostMapping("/salvar")
    public String salvarProduto(@Valid @ModelAttribute("produto") ProdutoModel produto, BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            return "cadastro-produto";
        }

        try {
            service.salvar(produto);

            String horario = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
            String mensagem = "Ação realizada com sucesso às " + horario;
            attributes.addFlashAttribute("mensagemSucesso", mensagem);
        }
        catch (IllegalArgumentException e) {
            result.rejectValue("nome", "error.produto", e.getMessage());
            return  "cadastro-produto";
        }

        return "redirect:/produtos";
    }

    @GetMapping("/editar/{id}")
    public String exibirEdicao(@PathVariable long id, Model model){
        model.addAttribute("produto", service.buscarPorId(id));
        return "cadastro-produto";
    }

    @GetMapping("/excluir/{id}")
    public String excluirProduto(@PathVariable long id, RedirectAttributes attributes){
        service.excluir(id);

        String horario = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        attributes.addFlashAttribute("mensagemSucesso", "Produto excluído com sucesso às " + horario + "!");

        return "redirect:/produtos";
    }
}
