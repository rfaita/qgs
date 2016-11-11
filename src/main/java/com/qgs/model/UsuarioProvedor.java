package com.qgs.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author rafael
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "UsuarioProvedor.findByUsuario", query = "SELECT p FROM UsuarioProvedor p WHERE upper(p.usuario) = :usuario"),
    @NamedQuery(name = "UsuarioProvedor.findByProvedor",
            query = "SELECT p FROM UsuarioProvedor p "
            + "\n JOIN FETCH p.provedor"
            + "\n WHERE p.provedor.id = :idProvedor")
})
public class UsuarioProvedor implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = true)
    private String usuario;
    private String email;
    private String senha;
    private String nome;
    private String sobrenome;
    private String sexo;
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String avatar;
    @ManyToOne
    @JoinColumn(name = "idprovedor")
    private Empresa provedor;
    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) {
        this.sobrenome = sobrenome;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Empresa getProvedor() {
        return provedor;
    }

    public void setProvedor(Empresa provedor) {
        this.provedor = provedor;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
