package com.qgs.model.cadastro.rubrica;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author rafael
 */
@Entity
public class Rubrica implements Serializable {

    @Id
    @SequenceGenerator(name = "seqrubrica", sequenceName = "seqrubrica", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqrubrica")
    private Integer id;
    private String codigo;
    private String rubrica;
    private String rubricaResumida;
    private Boolean ativo;
    @ManyToOne
    @JoinColumn(name = "idtiporubrica")
    private TipoRubrica tipoRubrica;

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRubrica() {
        return rubrica;
    }

    public void setRubrica(String rubrica) {
        this.rubrica = rubrica;
    }

    public String getRubricaResumida() {
        return rubricaResumida;
    }

    public void setRubricaResumida(String rubricaResumida) {
        this.rubricaResumida = rubricaResumida;
    }

    public TipoRubrica getTipoRubrica() {
        return tipoRubrica;
    }

    public void setTipoRubrica(TipoRubrica tipoRubrica) {
        this.tipoRubrica = tipoRubrica;
    }
}