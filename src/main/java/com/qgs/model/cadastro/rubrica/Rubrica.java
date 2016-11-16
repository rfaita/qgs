package com.qgs.model.cadastro.rubrica;

import com.qgs.model.BaseBean;
import com.qgs.model.Empresa;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

/**
 *
 * @author rafael
 */
@Entity
public class Rubrica extends BaseBean<Integer> {

    @Id
    @SequenceGenerator(name = "seqrubrica", sequenceName = "seqrubrica", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqrubrica")
    private Integer id;
    private String atalho;
    @NotNull(message = "Rubrica é obrigatório.", groups = SaveGroup.class)
    @Size(min = 1, max = 100, message = "A rubrica deve estar preenchido e possuir no máximo 100 caractéres.", groups = SaveGroup.class)
    private String rubrica;
    @Size(min = 1, max = 4000, message = "A descrição deve estar preenchido e possuir no máximo 4000 caractéres.", groups = SaveGroup.class)
    private String descricao;
    @NotNull(message = "Rubrica resumida é obrigatório.", groups = SaveGroup.class)
    @Size(min = 1, max = 50, message = "A rubrica resumida deve estar preenchido e possuir no máximo 50 caractéres.", groups = SaveGroup.class)
    private String rubricaResumida;
    private Boolean ativo;
    @ManyToOne
    @JoinColumn(name = "idtiporubrica")
    @NotNull(message = "O tipo da rubrica é obrigatório.", groups = SaveGroup.class)
    private TipoRubrica tipoRubrica;
    @ManyToOne
    @JoinColumn(name = "idEmpresa")
    private Empresa empresa;

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAtalho() {
        return atalho;
    }

    public void setAtalho(String atalho) {
        this.atalho = atalho;
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

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public TipoRubrica getTipoRubrica() {
        return tipoRubrica;
    }

    public void setTipoRubrica(TipoRubrica tipoRubrica) {
        this.tipoRubrica = tipoRubrica;
    }

    public Empresa getEmpresa() {
        return empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public interface SaveGroup extends Default {

    }

    public interface CancelGroup extends Default {

    }
}
