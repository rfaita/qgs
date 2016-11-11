package com.qgs.model.cadastro.servico;

import com.qgs.enums.TipoCustomEnum;
import com.qgs.model.cadastro.rubrica.Rubrica;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

/**
 *
 * @author rafael
 */
@Entity
public class ServicoCusto implements Serializable {

    @Id
    @SequenceGenerator(name = "seqservicocusto", sequenceName = "seqservicocusto", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqservicocusto")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "idservico")
    private Servico servico;
    @ManyToOne
    @JoinColumn(name = "idrubrica")
    private Rubrica rubrica;
    private Integer qtdParcela;
    private Double valor;
    private TipoCustomEnum tipoCustomEnum;

    public Rubrica getRubrica() {
        return rubrica;
    }

    public void setRubrica(Rubrica rubrica) {
        this.rubrica = rubrica;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQtdParcela() {
        return qtdParcela;
    }

    public void setQtdParcela(Integer qtdParcela) {
        this.qtdParcela = qtdParcela;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public TipoCustomEnum getTipoCustomEnum() {
        return tipoCustomEnum;
    }

    public void setTipoCustomEnum(TipoCustomEnum tipoCustomEnum) {
        this.tipoCustomEnum = tipoCustomEnum;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
}
