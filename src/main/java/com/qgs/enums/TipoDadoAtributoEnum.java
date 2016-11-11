package com.qgs.enums;

/**
 *
 * @author rafael
 */
public enum TipoDadoAtributoEnum {

    TEXTO("T"), NUMERICO("I"), NORMALIZADO("N"), DATA("D");
    private String tipo;

    public String getTipo() {
        return tipo;
    }

    TipoDadoAtributoEnum(String tipo) {
        this.tipo = tipo;
    }
}
