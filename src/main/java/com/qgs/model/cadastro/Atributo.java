package com.qgs.model.cadastro;

import com.qgs.model.BaseBean;
import com.qgs.model.Empresa;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

/**
 *
 * @author rafael
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Atributo.findAll", query = "SELECT p FROM Atributo p")
    ,
    @NamedQuery(name = "Atributo.findAllByParam", query = "SELECT o FROM Atributo o JOIN FETCH o.empresa WHERE o.empresa.id = :idEmpresa AND o.ativo = true")
})
public class Atributo extends BaseBean<Integer> {

    @Id
    @SequenceGenerator(name = "seqatributo", sequenceName = "seqatributo", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqatributo")
    private Integer id;
    @NotNull(message = "Atributo é obrigatório.", groups = SaveGroup.class)
    @Size(min = 1, max = 50, message = "O atributo deve estar preenchido e possuir no máximo 50 caractéres.", groups = SaveGroup.class)
    private String atributo;
    @NotNull(message = "Descrição do atributo é obrigatório.", groups = SaveGroup.class)
    @Size(min = 1, max = 4000, message = "A descrição do atributo deve estar preenchido e possuir no máximo 4000 caractéres.", groups = SaveGroup.class)
    private String descricao;
    private Boolean ativo;
    @ManyToOne
    @JoinColumn(name = "idtipoatributo")
    @NotNull(message = "Tipo do atributo é obrigatório.", groups = SaveGroup.class)
    private TipoAtributo tipoAtributo;
    private Integer nrMedicoes;
    @OneToMany(targetEntity = ValorAtributo.class, mappedBy = "atributo", orphanRemoval = true, cascade = CascadeType.ALL)
    private List<ValorAtributo> valoresAtributo;
    @ManyToOne
    @JoinColumn(name = "idempresa")
    private Empresa empresa;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAtributo() {
        return atributo;
    }

    public void setAtributo(String atributo) {
        this.atributo = atributo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public TipoAtributo getTipoAtributo() {
        return tipoAtributo;
    }

    public void setTipoAtributo(TipoAtributo tipoAtributo) {
        this.tipoAtributo = tipoAtributo;
    }

    public Integer getNrMedicoes() {
        return nrMedicoes;
    }

    public void setNrMedicoes(Integer nrMedicoes) {
        this.nrMedicoes = nrMedicoes;
    }

    public List<ValorAtributo> getValoresAtributo() {
        return valoresAtributo;
    }

    public void setValoresAtributo(List<ValorAtributo> valoresAtributo) {
        this.valoresAtributo = valoresAtributo;
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
