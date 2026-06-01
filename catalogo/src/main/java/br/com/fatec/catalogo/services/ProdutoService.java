package br.com.fatec.catalogo.services;

import br.com.fatec.catalogo.models.AuditoriaModel;
import br.com.fatec.catalogo.models.ProdutoModel;
import br.com.fatec.catalogo.repositories.AuditoriaRepository;
import br.com.fatec.catalogo.repositories.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repository;

    @Autowired
    private AuditoriaRepository auditoriaRepository;

    public List<ProdutoModel> listarTodos(String termoBusca){
        if (termoBusca != null && !termoBusca.isBlank()){
            return repository.findByNomeContainingIgnoreCaseOrCategoriaNomeContainingIgnoreCase(termoBusca, termoBusca);
        }
        return repository.findAllByOrderByUltimaAtualizacaoDesc();
    }

    public ProdutoModel buscarPorId(long id){
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Produto não encontrado: " + id));
    }

    @Transactional
    public void salvar(ProdutoModel produto){
        boolean verificaNome = repository.existsByNome(produto.getNome());
        if (verificaNome && produto.getIdProduto() == 0){
            throw new IllegalArgumentException("Já existe um produto cadastrado com este nome.");
        }

        if (produto.getQuantidade() == null || produto.getQuantidade() <= 0){
            throw new IllegalArgumentException("A quantidade é obrigatória e deve ser um número positivo.");
        }

        boolean ehAtualizacao = produto.getIdProduto() != 0;
        String operacao = ehAtualizacao ? "UPDATE" : "CREATE";

        repository.save(produto);

        registrarLog(operacao, produto.getIdProduto(), produto.getNome());
    }

    @Transactional
    public void excluir(long id){
        ProdutoModel produto = buscarPorId(id);

        repository.deleteById(id);

        registrarLog("DELETE", produto.getIdProduto(), produto.getNome());
    }

    private void registrarLog(String operacao, long produtoId, String produtoNome) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String usuarioLogado = (auth != null && auth.isAuthenticated()) ? auth.getName() : "Sistema/Anônimo";

        AuditoriaModel log = new AuditoriaModel();
        log.setOperacao(operacao);
        log.setProdutoId(produtoId);
        log.setProdutoNome(produtoNome);
        log.setDataHora(LocalDateTime.now());
        log.setUsuarioResponsavel(usuarioLogado);

        auditoriaRepository.save(log);
    }
}

