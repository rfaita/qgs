package com.qgs.model;

import com.qgs.enums.SexoEnum;
import com.qgs.model.endereco.Endereco;
import com.qgs.model.endereco.UF;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author rafael
 */
@Entity
public class Cliente implements Serializable {

    @Id
    @SequenceGenerator(name = "seqclient", sequenceName = "seqclient", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "seqclient")
    private Integer id;
    private String cliente;
    private String rg;
    @ManyToOne
    @JoinColumn(name = "idufexprg")
    private UF ufExpRg;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataExpRG;
    private String orgExpRG;
    private String cpf;
    private String foneFixo;
    private String foneMovel;
    private String email;
    @OneToMany(targetEntity = Endereco.class, mappedBy = "cliente")
    private List<Endereco> endereco;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dataNascimento;
    private SexoEnum sexo;

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Date getDataExpRG() {
        return dataExpRG;
    }

    public void setDataExpRG(Date dataExpRG) {
        this.dataExpRG = dataExpRG;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Endereco> getEndereco() {
        return endereco;
    }

    public void setEndereco(List<Endereco> endereco) {
        this.endereco = endereco;
    }

    public String getFoneFixo() {
        return foneFixo;
    }

    public void setFoneFixo(String foneFixo) {
        this.foneFixo = foneFixo;
    }

    public String getFoneMovel() {
        return foneMovel;
    }

    public void setFoneMovel(String foneMovel) {
        this.foneMovel = foneMovel;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrgExpRG() {
        return orgExpRG;
    }

    public void setOrgExpRG(String orgExpRG) {
        this.orgExpRG = orgExpRG;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public SexoEnum getSexo() {
        return sexo;
    }

    public void setSexo(SexoEnum sexo) {
        this.sexo = sexo;
    }

    public UF getUfExpRg() {
        return ufExpRg;
    }

    public void setUfExpRg(UF ufExpRg) {
        this.ufExpRg = ufExpRg;
    }
}
