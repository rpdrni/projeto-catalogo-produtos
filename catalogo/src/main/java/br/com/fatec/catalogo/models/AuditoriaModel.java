package br.com.fatec.catalogo.models;
import jakarta.persistence.*;


import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "TB_AUDITORIA")
public class AuditoriaModel {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long idAuditoria;

    @Column(nullable = false)
    private String operacao;

    @Column(nullable = false)
    private long produtoId;

    @Column(nullable = false)
    private String produtoNome;

    @Column(nullable = false)
    private LocalDateTime dataHora;

    @Column(nullable = false)
    private String usuarioResponsavel;


    public AuditoriaModel() {}

    public long getIdAuditoria() { return idAuditoria; }
    public void setIdAuditoria(long idAuditoria) { this.idAuditoria = idAuditoria; }

    public String getOperacao() { return operacao; }
    public void setOperacao(String operacao) { this.operacao = operacao; }

    public long getProdutoId() { return produtoId; }
    public void setProdutoId(long produtoId) { this.produtoId = produtoId; }

    public String getProdutoNome() { return produtoNome; }
    public void setProdutoNome(String produtoNome) { this.produtoNome = produtoNome; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public String getUsuarioResponsavel() { return usuarioResponsavel; }
    public void setUsuarioResponsavel(String usuarioResponsavel) { this.usuarioResponsavel = usuarioResponsavel; }
}
