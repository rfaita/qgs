package com.qgs.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

/**
 *
 * @author rafael
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Usuario.findByUsuario", query = "SELECT p FROM Usuario p WHERE upper(p.usuario) = :usuario")
    ,
    @NamedQuery(name = "Usuario.findByCelular", query = "SELECT p FROM Usuario p WHERE upper(p.celular) = :celular")
    ,
    @NamedQuery(name = "Usuario.findByCPF", query = "SELECT p FROM Usuario p WHERE upper(p.cpf) = :cpf")
})
public class Usuario implements Serializable {

    @Id
    @SequenceGenerator(name = "sequsuario", sequenceName = "sequsuario", initialValue = 1000, allocationSize = 100)
    @GeneratedValue(generator = "sequsuario")
    private Integer id;
    @NotNull(message = "Usuário é obrigatório.", groups = SaveGroup.class)
    @Size(min = 1, max = 50, message = "O usuário deve estar preenchido e possuir no máximo 50 caractéres.", groups = SaveGroup.class)
    @Column(unique = true)
    private String usuario;
    private String senha;
    @Transient
    private String senhaConf;
    @NotNull(message = "CPF é obrigatório.", groups = SaveGroup.class)
    private String cpf;
    @NotNull(message = "E-mail é obrigatório.", groups = SaveGroup.class)
    @Pattern(regexp = ".+@.+\\.[a-z]+", message = "Formato do email inválido.", groups = SaveGroup.class)
    private String email;
    @NotNull(message = "Celular é obrigatório.", groups = SaveGroup.class)
    @Size(min = 10, max = 11, message = "O celular deve estar preenchido e possuir 10 ou 11 digítos.", groups = SaveGroup.class)
    private String celular;
    private String role;
    private Integer pontos;

    public interface SaveGroup extends Default {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getSenhaConf() {
        return senhaConf;
    }

    public void setSenhaConf(String senhaConf) {
        this.senhaConf = senhaConf;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Integer getPontos() {
        return pontos;
    }

    public void setPontos(Integer pontos) {
        this.pontos = pontos;
    }

}
