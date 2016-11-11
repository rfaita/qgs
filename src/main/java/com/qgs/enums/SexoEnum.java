package com.qgs.enums;

/**
 *
 * @author rafael
 */
public enum SexoEnum {

    MASCULINO("M"), FEMININO("F");
    private String sexo;

    public String getSexo() {
        return sexo;
    }

    SexoEnum(String sexo) {
        this.sexo = sexo;
    }
}
