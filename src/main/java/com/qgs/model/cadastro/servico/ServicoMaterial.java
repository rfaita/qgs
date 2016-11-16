package com.qgs.model.cadastro.servico;

import com.qgs.model.cadastro.Material;
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
public class ServicoMaterial implements Serializable {

    @Id
    @SequenceGenerator(name = "seqServicoMaterial", sequenceName = "seqServicoMaterial", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqServicoMaterial")
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "idservico")
    private Servico servico;
    @ManyToOne
    @JoinColumn(name = "idmaterial")
    private Material material;
    private Integer qtd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material material) {
        this.material = material;
    }

    public Integer getQtd() {
        return qtd;
    }

    public void setQtd(Integer qtd) {
        this.qtd = qtd;
    }
}
