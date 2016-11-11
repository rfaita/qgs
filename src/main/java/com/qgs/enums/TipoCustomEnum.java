package com.qgs.enums;

/**
 *
 * @author rafael
 */
public enum TipoCustomEnum {

    A_VISTA("V"), PARCELADO("P");
    private String tipoCusto;

    public String getTipoCusto() {
        return tipoCusto;
    }

    private TipoCustomEnum(String tipoCusto) {
        this.tipoCusto = tipoCusto;
    }
}
