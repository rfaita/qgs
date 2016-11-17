package com.qgs.enums;

/**
 *
 * @author rafael
 */
public enum TipoCustomEnum {

    A_VISTA("À vista"), PARCELADO("Parcelado");

    private final String tipoCusto;

    public String getTipoCusto() {
        return tipoCusto;
    }

    private TipoCustomEnum(String tipoCusto) {
        this.tipoCusto = tipoCusto;
    }

}
