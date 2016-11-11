package com.qgs.model.cadastro;

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
public class Material extends BaseBean<Integer> {

    @Id
    @SequenceGenerator(name = "seqmaterial", sequenceName = "seqmaterial", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqmaterial")
    private Integer id;
    @NotNull(message = "Material é obrigatório.", groups = SaveGroup.class)
    @Size(min = 1, max = 50, message = "O material deve estar preenchido e possuir no máximo 50 caractéres.", groups = SaveGroup.class)
    private String material;
    @NotNull(message = "Descrição do material é obrigatório.", groups = EPI.SaveGroup.class)
    @Size(min = 1, max = 4000, message = "A descrição do material deve estar preenchido e possuir no máximo 4000 caractéres.", groups = SaveGroup.class)
    private String descricao;
    private Boolean ativo;
    @ManyToOne
    @JoinColumn(name = "idtipomaterial")
    @NotNull(message = "Tipo do material é obrigatório.", groups = SaveGroup.class)
    private TipoMaterial tipoMaterial;
    private Double valorUnitario;
    private Integer dimensoes;
    @ManyToOne
    @JoinColumn(name = "idempresa")
    private Empresa empresa;

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
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

    public TipoMaterial getTipoMaterial() {
        return tipoMaterial;
    }

    public void setTipoMaterial(TipoMaterial tipoMaterial) {
        this.tipoMaterial = tipoMaterial;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public Integer getDimensoes() {
        return dimensoes;
    }

    public void setDimensoes(Integer dimensoes) {
        this.dimensoes = dimensoes;
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
